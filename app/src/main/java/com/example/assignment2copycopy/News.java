package com.example.assignment2copycopy;

public class News {
    String title, body;

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public News(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
