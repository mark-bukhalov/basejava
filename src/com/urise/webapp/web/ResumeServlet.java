package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = Config.get().getStorage();
        List<Resume> resumes = storage.getAllSorted();

        StringBuilder builder = new StringBuilder();

        builder.append("<!DOCTYPE HTML> " +
                "<html> " +
                "<head> " +
                "<meta charset=\"utf-8\"> " +
                "<title>RESUMES</title> " +
                "</head> " +
                "<body> "
        );

        builder.append("<table border=\"1\"> " +
                "<caption> Resumes </caption> " +
                "<tr> " +
                "<th>UUID</th> " +
                "<th>FullName</th>" +
                "</tr> ");

        for (Resume resume : resumes) {
            builder.append("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName() + "</td></tr> ");
        }
        builder.append("</table> </body> </html>");


        response.getWriter().write(builder.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
