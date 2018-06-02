package com.projects.melih.builditbigger.backend;

public class JokeBean {
    private final String jokeName;

    public JokeBean(String jokeName) {
        this.jokeName = jokeName;
    }

    public String getData() {
        return jokeName;
    }
}