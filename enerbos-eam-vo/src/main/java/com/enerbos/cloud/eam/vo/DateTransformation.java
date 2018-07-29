package com.enerbos.cloud.eam.vo;/**
 * Created by enerbos on 2017/11/6.
 */

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/11/6 10:25
 * @Description: 时间转换
 */
public class DateTransformation {
    // 01. java.util.Date --> java.time.LocalDateTime
    public static LocalDateTime UDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
    // 02. java.util.Date --> java.time.LocalDate
    public static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    // 03. java.util.Date --> java.time.LocalTime
    public static LocalTime UDateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }


    // 04. java.time.LocalDateTime --> java.util.Date
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }


    // 05. java.time.LocalDate --> java.util.Date
    public static Date LocalDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    // 06. java.time.LocalTime --> java.util.Date
    public static Date LocalTimeToUdate( LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }
    //将时间转换为特定的时间输出
    public  static Date DateFormat(Date date,String format){
        if(format==null||"".equals(format)){
            format="yyyy-MM-dd HH:mm:ss";
        }
        Date newDate=new Date();
        SimpleDateFormat time=new SimpleDateFormat(format);
        String timeString=time.format(date);
        try {
             newDate = time.parse( timeString );
        }catch (Exception e){
           e.printStackTrace();
        }
         return  newDate;

    }

}
