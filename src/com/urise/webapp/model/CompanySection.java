package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

}
