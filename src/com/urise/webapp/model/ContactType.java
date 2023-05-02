package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype") {
//        @Override
//        public String toHtml0(String value) {
//            return "<a gref='skype:" + value + "'>" + value + "</a>";
//        }
    },

    EMAIL("Почта") {
//        @Override
//        public String toHtml0(String value) {
//            return "<a gref='mailto:" + value + "'>" + value + "</a>";
//        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");
    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toHtml0(String value) {
        return name + ": " + value;
    }

}
