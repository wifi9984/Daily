package com.wyf.daily;

/**
 * Event作为一个类，有几个属性，通过对应的getXXX方法可以找到对应的数据
 *
 * @author wifi9984
 * @date 2017/8/31
 */

public class Event {
    private String event;
    private String date;
    private String timeStart;
    private String timeEnd;
    private Integer pattern;

    /**
     * 类的空构造方法，不能删= =
     */
    public Event() {

    }

    /**
     * 重载的构造方法，用来写入参数，在NewEventActivity里面有引用
     *
     * @param event 事件名
     * @param date 日期
     * @param timeStart 事件开始时间
     * @param timeEnd 事件结束时间
     * @param pattern 提醒方式
     */
    public Event(String event, String date, String timeStart, String timeEnd, Integer pattern) {
        this.event = event;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.pattern = pattern;
    }

    public String getEvent() {
        return event;
    }

    public String getDate() {
        return date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public Integer getPattern() {
        return pattern;
    }
}
