package com.wyf.daily;

/**
 *  Event作为一个类，有几个属性，通过对应的getXXX方法可以找到对应的数据
 */

public class Event {
    private String event;
    private String date;
    private String time_s;
    private String time_e;
    private Integer pattern;

    // 类的空构造方法，不能删= =
    public Event(){

    }

    // 重载的构造方法，用来写入参数，在NewEventActivity里面有引用
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
