package com.example.wenquxing.javabean;

import java.util.Date;

public class Event {
    private String EventContent;
    private Date Time;

    public Event(){
        EventContent = null;
        Time = null;
    }

    public Event(String eventContent, Date time) {
        EventContent = eventContent;
        Time = time;
    }

    public void setEventContent(String eventContent) {
        EventContent = eventContent;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public String getEventContent() {
        return EventContent;
    }

    public Date getTime() {
        return Time;
    }
}
