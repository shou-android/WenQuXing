package com.example.wenquxing.javabean;

import java.util.Calendar;
import java.util.Date;

public class Event {
    private Integer ID;
    private String EventContent;
    private Date Time;

    public Event(){
        ID = null;
        EventContent = null;
        Time = null;
    }

    public Event(String eventContent, Date time, Integer id) {
        ID = id;
        EventContent = eventContent;
        Time = time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
