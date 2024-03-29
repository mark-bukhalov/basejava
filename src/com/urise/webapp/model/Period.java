package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final Period EMPTY = new Period();
    private String name;
    private String description;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate beginDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    public Period() {
    }

    public Period(String name, String description, LocalDate beginDate, LocalDate endDate) {
        Objects.requireNonNull(name, " \"name must not be null\"");
        Objects.requireNonNull(beginDate, " \"beginDate must not be null\"");
        Objects.requireNonNull(endDate, " \"endDate must not be null\"");
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Period(String name, String description) {
        Objects.requireNonNull(name, " \"name must not be null\"");
        Objects.requireNonNull(description, " \"description must not be null\"");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!name.equals(period.name)) return false;
        if (!description.equals(period.description)) return false;
        if (!beginDate.equals(period.beginDate)) return false;
        return endDate.equals(period.endDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
