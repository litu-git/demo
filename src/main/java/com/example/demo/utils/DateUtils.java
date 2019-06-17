/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: WangYanChao[wang_yc@suixingpay.com]
 * @date: 2017年4月18日 下午5:16:57
 * @Copyright ©2017 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.example.demo.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间操作工具类
 *
 * @Author: litu[li_tu@suixingpay.com]
 * @Date: 17:42 2019/5/21
 **/
public class DateUtils {
    /**
     *
     */
    public static final String YEAR_DATE_FORMAT_STR = "yyyy";
    /**
     *
     */
    public static final String MONTH_DATE_FORMAT_STR = "MM";
    /**
     *
     */
    public static final String DAY_DATE_FORMAT_STR = "dd";
    /**
     *
     */
    public static final String ABBR_SHORT_DATE_FORMAT_STR = "yyyyMMdd";

    /**
     * 使用参数Format格式化Date成字符串
     *
     * @return String
     */
    public static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateTime.format(new Date());
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static Long getTimeStamp(String timeStamp) {
        return new Date(new Long(timeStamp)).getTime();
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static Date stringToDate(String strValue) {
        if (StringUtils.isEmpty(strValue)) {
            return null;
        }
        SimpleDateFormat clsFormat = null;
        if (strValue.length() > 19) {
            strValue = strValue.substring(0, 19);
        }
        if (strValue.length() == 19) {
            clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (strValue.length() == 10) {
            clsFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (strValue.length() == 14) {
            clsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (strValue.length() == 8) {
            clsFormat = new SimpleDateFormat("yyyyMMdd");
        }

        ParsePosition pos = new ParsePosition(0);
        return clsFormat.parse(strValue, pos);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static String DateToTimeStamp(String str, DateEnum dateFormat) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getDateFormat());
        Date date;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return "";
        }
        long l = date.getTime();
        return String.valueOf(l);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static String TimeStampToDate(String str, DateEnum dateFormat) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getDateFormat());
        long l = new Long(str);
        Date date = new Date(l);
        return sdf.format(date);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:38:56
     * @version: V1.0
     */
    public static String changeDate(String date, DateEnum oldDateFormat, DateEnum newDateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldDateFormat.getDateFormat());
        Date d = new Date();
        try {
            d = simpleDateFormat.parse(date);
        } catch (ParseException e) {
        }
        simpleDateFormat = new SimpleDateFormat(newDateFormat.getDateFormat());
        return simpleDateFormat.format(d);
    }

    /**
     * 时间改格式
     *
     * @param date
     * @param oldDateFormat
     * @param newDateFormat
     * @return
     */
    public static String getDate(String date, DateEnum oldDateFormat, DateEnum newDateFormat) {
        if (null == date || "".equals(date)) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newDateFormat.getDateFormat());
            date = simpleDateFormat.format(calendar.getTime());
        } else if ((null != oldDateFormat) && ((8 == date.length()) || (14 == date.length()) || (-1 != date
                .indexOf(":")) || (-1 != date.indexOf("-")) || (-1 != date.indexOf(" ")))) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldDateFormat.getDateFormat());
            Date d = new Date();
            try {
                d = simpleDateFormat.parse(date);
            } catch (ParseException e) {
            }
            simpleDateFormat = new SimpleDateFormat(newDateFormat.getDateFormat());
            date = simpleDateFormat.format(d);
        } else {
            Long timestamp = Long.parseLong(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newDateFormat.getDateFormat());
            Date d = new Date(timestamp);
            date = simpleDateFormat.format(d);
        }
        return date;
    }

    /**
     * String 转 Date
     *
     * @param date
     * @return
     */
    public static Date toDate(String date, DateEnum dateFormat) {
        if (StringUtils.isEmpty(date)) {
            return newDate(1970, 1, 1);
        }
        return DateUtils.parse(date, dateFormat.getDateFormat());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 新建一个日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date newDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month - 1, day);
        return c.getTime();
    }

    /**
     * 新建一个日期
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month - 1, day, hour, minute, second);
        return c.getTime();
    }

    /**
     * 指定时间的小时开始
     *
     * @param date
     * @return ... HH:00:00.000
     */
    public static Date hourBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 指定时间的小时最后一毫秒
     *
     * @param date
     * @return ... HH:59:59.999
     */
    public static Date hourEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取今天 00:00:00.000 的时间
     */
    public static Date dayBegin() {
        return dayBegin(now());
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取今天 23:59:59.999 的时间
     */
    public static Date dayEnd() {
        return dayEnd(now());
    }

    /**
     * 月首
     *
     * @param date
     * @return
     */
    public static Date monthBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 月末
     *
     * @param date
     * @return
     */
    public static Date monthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 年首
     *
     * @param date
     * @return
     */
    public static Date yearBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 年末
     *
     * @param date
     * @return
     */
    public static Date yearEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean isTheDay(Date date, Date day) {
        return date.getTime() >= DateUtils.dayBegin(day).getTime() && date.getTime() <= DateUtils.dayEnd(day).getTime();
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        return isTheDay(date, DateUtils.now());
    }

    /**
     * 增加、减少指定时数
     *
     * @param date
     * @param hour 要增加的时数（减少则为 负数）
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    /**
     * 增加、减少指定天数
     *
     * @param date
     * @param day  要增加的天数（减少则为 负数）
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * 增加、减少指定月数
     *
     * @param date
     * @param month 要增加的月数（减少则为 负数）
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 增加、减少指定年数
     *
     * @param date
     * @param year 要增加的年数（减少则为 负数）
     * @return
     */
    public static Date addYear(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * 获取两个日期间的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int amountDays(Date startDate, Date endDate) {
        long ms = endDate.getTime() - startDate.getTime();
        int ret = (int) (ms / 86400000L);
        return ret;
    }

    /**
     * 获取两个日期间的月数（待改进）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int amountMonths(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        boolean rev = false; // 翻转（是否 startDate > endDate）
        Date d1 = startDate;
        Date d2 = endDate;
        if (startDate.getTime() > endDate.getTime()) {
            rev = true;
            d1 = endDate;
            d2 = startDate;
        }
        c1.setTime(d1);
        c2.setTime(d2);
        // count of year
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int cy = year2 - year1;
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int ret;
        if (cy > 0) {
            ret = (cy - 1) * 12;
            ret += month2;
            ret += (12 - month1);
        } else {
            ret = month2 - month1;
        }
        return rev ? (-1 * ret) : ret;
    }

    /**
     * 获取两个日期间的年数（待改进）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int amountYears(Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        boolean rev = false; // 翻转（是否 startDate > endDate）
        Date d1 = startDate;
        Date d2 = endDate;
        if (startDate.getTime() > endDate.getTime()) {
            rev = true;
            d1 = endDate;
            d2 = startDate;
        }
        c.setTime(d1);
        int y1 = c.get(Calendar.YEAR);
        c.setTime(d2);
        int y2 = c.get(Calendar.YEAR);
        int ret = y2 - y1;
        return rev ? (-1 * ret) : ret;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final int YMDHMS = 1;
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final int YMDHM = 2;
    /**
     * yyyy-MM-dd HH
     */
    public static final int YMDH = 3;
    /**
     * yyyy-MM-dd
     */
    public static final int YMD = 4;
    /**
     * yyyy-MM
     */
    public static final int YM = 5;
    /**
     * yyyy
     */
    public static final int Y = 6;

    private static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat SDF3 = new SimpleDateFormat("yyyy-MM-dd HH");
    private static final SimpleDateFormat SDF4 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat SDF5 = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat SDF6 = new SimpleDateFormat("yyyy");

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toString(Date date) {
        if (date == null) {
            return "";
        }
        return SDF1.format(date);
    }

    /**
     * 转换到字符串
     *
     * @param date
     * @param type DateUtils.YMDHMS : "yyyy-MM-dd HH:mm:ss", DateUtils.YMD :
     *             "yyyy-MM-dd", DateUtils.YM : "yyyy-MM", DateUtils.Y : "yyyy"
     * @return
     */
    public static String toString(Date date, int type) {
        if (date == null) {
            return "null";
        }
        switch (type) {
            case YMDHMS:
                return SDF1.format(date);
            case YMDHM:
                return SDF2.format(date);
            case YMDH:
                return SDF3.format(date);
            case YMD:
                return SDF4.format(date);
            case YM:
                return SDF5.format(date);
            case Y:
                return SDF6.format(date);
        }
        return "unknow";
    }

    /**
     * Description: TODO(转换日期格式【yyyy-MM-dd】)
     *
     * @param dateStr
     * @param @return
     * @return String
     * @throws ParseException
     * @throws
     * @author xu_jy@suixingpay.com
     */
    public static String dateTrans(String dateStr) throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return nowDate;
    }

    /**
     * Description: TODO(转换日期格式【yyyy-MM-dd HH:mm:ss】)
     *
     * @param dateStr
     * @param @return
     * @return String
     * @throws ParseException
     * @throws
     * @author xu_jy@suixingpay.com
     */
    public static String dateTransTime(String dateStr) throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr);
        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return nowDate;
    }

    /**
     * Description: TODO(转换时间格式【HH:mm:ss】)
     *
     * @param dateStr
     * @param @return
     * @return String
     * @throws ParseException
     * @throws
     * @author xu_jy@suixingpay.com
     */
    public static String dateTransSimpleTime(String dateStr) throws ParseException {
        Date date = new SimpleDateFormat("HHmmss").parse(dateStr);
        String nowDate = new SimpleDateFormat("HH:mm:ss").format(date);
        return nowDate;
    }

    /**
     * 转换到字符串
     *
     * @param date
     * @param fmt  日期格式化字符串
     * @return
     */
    public static String toString(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    /**
     * 从字符串解析为日期型
     *
     * @param s
     * @param fmt
     * @return
     */
    public static Date parse(String s, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 从字符串(yyyy-MM-dd HH:mm:ss)解析为日期型
     *
     * @param s
     * @return
     */
    public static Date parse(String s) {
        try {
            return SDF1.parse(s);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:43:41
     * @version: V1.0
     */
    public static long parseLong(String s, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            Date t = sdf.parse(s);
            return t == null ? 0L : t.getTime();
        } catch (ParseException e) {
        }
        return 0L;
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:43:46
     * @version: V1.0
     */
    public static long parseLong(String s) {
        try {
            Date t = SDF1.parse(s);
            return t == null ? 0L : t.getTime();
        } catch (ParseException e) {
        }
        return 0L;
    }

    /**
     * 判断日期间隔是否超过一个月
     *
     * @param startDt
     * @param endDt
     * @return
     */
    public static boolean checkDate(String startDt, String endDt) {
        Long time = DateUtils.parseLong(endDt) - DateUtils.parseLong(startDt);
        if (((time < 0) ? (-time) : time) > 2678400000L) {
            return true;
        }
        return false;
    }

    /**
     * 判断日期间隔是否超过一个月
     *
     * @param startDt 时间戳格式
     * @param endDt
     * @return
     */
    public static boolean checkTimeDate(String startDt, String endDt) {
        Long time = Long.valueOf(endDt) - Long.valueOf(startDt);
        if (((time < 0) ? (-time) : time) > 2678400000L) {
            return true;
        }
        return false;
    }

    /**
     * <br>
     * 传入的日期减去天数后返回的日期</br>
     *
     * @param date 被减日期
     * @param day  减去的天数
     * @return 返回减去指定天数后的日期
     */
    public static Date subtractDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        dayOfMonth -= day;
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }

    public static String getCurDT() {
        return toString(new Date(), ABBR_SHORT_DATE_FORMAT_STR);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:43:56
     * @version: V1.0
     */
    public static String getMilliseconds() {
        long now = System.currentTimeMillis();
        return now + "";
    }

    /**
     * Description: TODO(判断两个日期是否跨月（是否是同一个月）)
     *
     * @return boolean 同一个月true，反之false
     * @throws
     * @author tian_xc@suixingpay.com
     */
    public static boolean extendInToNextMonth(Date dateStr, Date dateEnd) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateStr);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateEnd);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * @author: litu[li_tu@suixingpay.com]
     * @date: 2018年11月5日 下午2:44:02
     * @version: V1.0
     */
    public static String changeDateType(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnStr = "";
        try {
            Date date = sdf.parse(str);
            returnStr = sdf2.format(date);
        } catch (ParseException e) {
        }
        return returnStr;
    }
}
