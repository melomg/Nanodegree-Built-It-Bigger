package com.projects.melih.builditbigger;

class Result {
    private String data;

    private String error;

    public Result(String data, String error) {
        this.data = data;
        this.error = error;
    }

    public String getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}