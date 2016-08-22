package com.github.vv_off.note;

public class Message {

    private int id;
    private String message;
    private String date;

    Message(String message, String date) {
        this.message = message;
        this.date = date;
    }

    Message(int id, String message, String date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
