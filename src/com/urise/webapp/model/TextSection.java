package com.urise.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final TextSection EMPTY = new TextSection("");
    private String value;

    public TextSection() {
    }

    public TextSection(String value) {
        Objects.requireNonNull(value, " \"value must not be null\"");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
