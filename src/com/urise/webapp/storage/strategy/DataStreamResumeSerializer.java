package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataStreamResumeSerializer extends AbstractResumeSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getValue());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) entry.getValue()).getValues();
                        dos.writeInt(list.size());
                        for (String value : list) {
                            dos.writeUTF(value);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                        dos.writeInt(companyList.size());
                        for (Company company : companyList) {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getUrl());
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getName());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getBeginDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                AbstractSection section = null;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        int sizeList = dis.readInt();
                        for (int j = 0; j < sizeList; j++) {
                            listSection.addValue(dis.readUTF());
                        }
                        section = listSection;
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = new CompanySection();
                        int sizeList = dis.readInt();
                        for (int j = 0; j < sizeList; j++) {
                            Company company = new Company(dis.readUTF(), dis.readUTF());
                            int periodCount = dis.readInt();
                            for (int k = 0; k < periodCount; k++) {
                                Period period = new Period(dis.readUTF(), dis.readUTF());
                                period.setBeginDate(LocalDate.parse(dis.readUTF()));
                                period.setBeginDate(LocalDate.parse(dis.readUTF()));
                            }
                            companySection.addCompany(company);
                        }
                        section = companySection;
                    }
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }
}
