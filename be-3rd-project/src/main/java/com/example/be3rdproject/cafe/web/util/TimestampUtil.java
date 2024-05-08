package com.example.be3rdproject.cafe.web.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimestampUtil {

    public static long convertLocalDateTimeToTimestamp(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
