package com.uni.common.springboot.util;

import java.time.*;

public class LocalDateTimeUtil {

    /**
     * @return LocalDateTime2Long
     * @Param time
     **/
    public static Long localDateTime2Long(Object time) {
        if (time == null) {
            return null;
        }
        if (time instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) time;
            return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }
        return null;
    }

    /**
     * @return long2LocalDateTime
     * @Param time
     **/
    public static LocalDateTime long2LocalDateTime(long time) {
        return Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @return Long
     * @Param time
     **/
    public static Long localDate2Long(Object time) {
        if (time == null) {
            return null;
        }
        if (time instanceof LocalDate) {
            LocalDate localDate = (LocalDate) time;
            return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        }
        return null;
    }

    /**
     * @return LocalDate
     * @Param time
     **/
    public static LocalDate long2LocalDate(long time) {
        return Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
