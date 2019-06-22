package com.dr.leo.etl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-22 19:54
 * @since 1.0
 */
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<Map<String, DateFormat>> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();

    private static DateFormat getDateFormat(String pattern) {
        if (pattern == null || pattern.trim().length() == 0) {
            throw new IllegalArgumentException("pattern cannot be empty.");
        }

        Map<String, DateFormat> dateFormatMap = DATE_FORMAT_THREAD_LOCAL.get();
        if (dateFormatMap != null && dateFormatMap.containsKey(pattern)) {
            return dateFormatMap.get(pattern);
        }

        synchronized (DATE_FORMAT_THREAD_LOCAL) {
            if (dateFormatMap == null) {
                dateFormatMap = new HashMap<>(2);
            }
            dateFormatMap.put(pattern, new SimpleDateFormat(pattern));
            DATE_FORMAT_THREAD_LOCAL.set(dateFormatMap);
        }

        return dateFormatMap.get(pattern);
    }

    public static String format(Date date, String patten) {
        return getDateFormat(patten).format(date);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }
}
