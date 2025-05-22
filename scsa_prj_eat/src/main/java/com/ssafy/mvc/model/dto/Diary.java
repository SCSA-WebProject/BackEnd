package com.ssafy.mvc.model.dto;

import java.sql.Date;

public class Diary {
    private int id;
    private String title;
    private Date date;
    private String context;

    // 기본 생성자 (no-argument constructor)
    public Diary() {
    }

    // 모든 필드를 초기화하는 생성자 (parameterized constructor)
    public Diary(int id, String title, Date date, String context) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.context = context;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for date
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getter and Setter for context
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
