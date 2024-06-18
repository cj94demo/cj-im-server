package com.chan.platform.common.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:58
 * FileName: DateTimeUtils
 * Description: 日期时间工具类
 */
public class DateTimeUtils extends DateUtils {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyyMMdd";


    /**
     * 将日期类型转换为字符串
     *
     * @param date    日期
     * @param xFormat 格式
     * @return 日期
     */
    public static String getFormatDate(Date date, String xFormat) {
        date = date == null ? new Date() : date;
        xFormat = StringUtils.isNotEmpty(xFormat) ? xFormat : DATE_TIME_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(xFormat);
        return sdf.format(date);
    }
}
