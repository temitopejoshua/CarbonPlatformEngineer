package com.carbon.hospitalitysystem.utils;

import com.carbon.hospitalitysystem.exception.BadRequestException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    enum Days {
        MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
    }
    public static  boolean isDayOfTheWeek(String day){
        try {
            Days.valueOf(day.toUpperCase());
        }catch (IllegalArgumentException ex){
            throw new BadRequestException(String.format("Invalid Day of the week %s", day));
        }
        if(day.equalsIgnoreCase(Days.SUNDAY.name()) || day.equalsIgnoreCase(Days.SATURDAY.name())) return Boolean.FALSE;

        else return Boolean.TRUE;
    }

    public static long getTimeDifferenceInHours(LocalDateTime a, LocalDateTime b){
        if(a.isAfter(b))
            return  0;
        return ChronoUnit.HOURS.between(a,b);
    }
    public static long getOverstayTimeDifference(LocalDateTime a, LocalDateTime b){
        Duration duration = Duration.between(a,b);
        if(a.isAfter(b))
            return  0;
        if(duration.toMinutes() % 60 != 0){
            return ChronoUnit.HOURS.between(a,b) + 1;

        }
        return ChronoUnit.HOURS.between(a,b);
    }
    public static boolean isWeekend(String day){
        return !isDayOfTheWeek(day);
    }
}
