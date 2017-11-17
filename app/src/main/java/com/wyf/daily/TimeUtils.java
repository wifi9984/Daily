package com.wyf.daily;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期事件格式转化的工具，直接调用就可以，避免重写浪费时间
 *
 * @author wifi9984
 * @date 2017/8/31
 */

public class TimeUtils {
    public static String getNowDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 - EE");
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = dateFormat.format(curDate);
        return strDate;
    }

    public static String getNowTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("a - h:mm");
        Date curTime = new Date(System.currentTimeMillis());
        String strTime = dateFormat.format(curTime);
        return strTime;
    }

}
