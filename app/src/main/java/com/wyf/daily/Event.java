package com.wyf.daily;

/**
 * Created by admin on 2017/8/5.
 */

public class Event {
    private String event;
    private String date;
    private String time_s;
    private String time_e;
    private Integer pattern;

    public Event(){

    }

    public Event(String event, String date, String time_s, String time_e, Integer pattern) {
        this.event = event;
        this.date = date;
        this.time_s = time_s;
        this.time_e = time_e;
        this.pattern = pattern;
    }

    public String getEvent() {
        return event;
    }

    public String getDate() {
        return date;
    }

    public String getTime_s() {
        return time_s;
    }

    public String getTime_e() {
        return time_e;
    }

    public Integer getPattern() {
        return pattern;
    }
}
