package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private String url;
    private final List<Period> periods = new ArrayList<>();

    public Company(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

}
