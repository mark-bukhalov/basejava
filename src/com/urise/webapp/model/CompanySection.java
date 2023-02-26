package com.urise.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Company> companies = new ArrayList<>();

    public CompanySection() {
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
