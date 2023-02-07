package com.urise.webapp.model;

import java.time.LocalDate;

public class Period {
    private String name;
    private String description;
    private LocalDate beginDate;
    private LocalDate endDate;

    public Period(String name, String description, LocalDate beginDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Period(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Period(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
