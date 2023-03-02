package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamResumeSerializer implements ResumeSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(dos, sections.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getValue());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) entry.getValue()).getValues();
                        writeWithException(dos, list, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companyList = ((CompanySection) entry.getValue()).getCompanies();
                        writeWithException(dos, companyList, company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getUrl());
                            List<Period> periods = company.getPeriods();
                            writeWithException(dos, periods, period -> {
                                dos.writeUTF(period.getName());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getBeginDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                    }
                }
            });
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, WriteElement<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.writeElement(element);
        }
    }

    @FunctionalInterface
    private interface WriteElement<T> {
        void writeElement(T element) throws IOException;
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
