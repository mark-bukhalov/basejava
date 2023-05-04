package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = Resume.EMPTY;
                break;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                addEmptySections(r);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume r;

        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(List.of(value.split("\\n"))));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Company> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Period> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        Period period = new Period(titles[j], descriptions[j]);
                                        period.setBeginDate(DateUtil.parse(startDates[j]));
                                        period.setEndDate(DateUtil.parse(endDates[j]));
                                        positions.add(period);
                                    }
                                }
                                orgs.add(new Company(name, urls[i], positions));
                            }
                        }
                        r.addSection(type, new CompanySection(orgs));
                        break;
                }
            }
        }

        List<String> error = new ArrayList<>();

        if (request.getParameter("fullName").equals("")) {
            error.add("Заполните ФИО");
        }

        if (error.isEmpty()) {
            if (isCreate) {
                storage.save(r);
            } else {
                storage.update(r);
            }
            response.sendRedirect("resume");
        } else {
            addEmptySections(r);
            request.setAttribute("status", "");
            request.setAttribute("resume", r);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        }
    }

    protected void addEmptySections(Resume r) {
        for (SectionType type : SectionType.values()) {
            AbstractSection section = r.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = TextSection.EMPTY;
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (section == null) {
                        section = ListSection.EMPTY;
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    CompanySection companySection = (CompanySection) section;
                    List<Company> emptyFirstComp = new ArrayList<>();
                    emptyFirstComp.add(Company.EMPTY);
                    if (companySection != null) {
                        for (Company company : companySection.getCompanies()) {
                            List<Period> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Period.EMPTY);
                            emptyFirstPositions.addAll(company.getPeriods());
                            emptyFirstComp.add(new Company(company.getName(), company.getUrl(), emptyFirstPositions));
                        }
                    }
                    section = new CompanySection(emptyFirstComp);
                    break;
            }
            r.addSection(type, section);
        }
    }
}
