package com.tangex.admin.sexology_text;

public class message_in_db {
    private String from;
    private String message;

    public message_in_db() {
    }

    public message_in_db(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
