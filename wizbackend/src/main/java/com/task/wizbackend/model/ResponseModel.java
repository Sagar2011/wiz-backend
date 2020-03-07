package com.task.wizbackend.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseModel {

    private int statusCode;
    private String message;
    private ArrayList<?> results;

    public ResponseModel() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<?> getResults() {
        return results;
    }

    public void setResults(ArrayList<?> results) {
        this.results = results;
    }
}
