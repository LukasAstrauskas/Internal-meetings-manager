package com.VismaProject.InternalMeetings.model;

import java.util.Set;

public class ResponseMessage {

    private String message;
    private Set<String> meetingsName;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message, Set<String> meetingsName) {
        this.message = message;
        this.meetingsName = meetingsName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getMeetingsName() {
        return meetingsName;
    }

    public void setMeetingsName(Set<String> meetingsName) {
        this.meetingsName = meetingsName;
    }
}
