package com.newcore.code.generator.utils;

import com.newcore.code.generator.utils.enums.BusinessExceptionCodeEnum;
import com.newcore.code.generator.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * @author zhouchaowei
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final Calendar CALENDAR = Calendar.getInstance();

    private static DateUtils dateUtilsInstance;

    private DateUtils(){

    }

    public static synchronized DateUtils getDateUtilsInstance(){
        if(dateUtilsInstance == null){
            dateUtilsInstance = new DateUtils();
        }
        return dateUtilsInstance;
    }

    /**
     * 获取day天前的指定时间
     * (例如:生成当前时间前一天时间零点，当前时间为2016/7/18 15:44:30，则生成时间为2016/7/17 00:00:00)
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 日期类型
     */
    public static Date getBeforeDayTimeReturnDate(int day,int hour,int minute,int second){
        setCalendarTime(day,hour,minute,second);
        return CALENDAR.getTime();
    }

    /**
     * 获取当前时间日期
     * @param currentDateStr 当前时间日期字符串
     * @param pattern 模板
     * @return
     */
    public static Date getCurrentDate(String currentDateStr, String pattern){
        try {
            return new SimpleDateFormat(StringUtils.isBlank(pattern) ? "yyyy-MM-dd" : pattern).parse(currentDateStr);
        } catch (ParseException e) {
            throw new BusinessException(BusinessExceptionCodeEnum.FAIL.getCode(),BusinessExceptionCodeEnum.FAIL.getMessage());
        }
    }

    /**
     * 获取day天前的指定时间，返回标准格式的(2016-7-18 15:44:30)
     * (例如:生成当前时间前一天时间零点，当前时间为2016/7/18 15:44:30，则生成时间为2016/7/17 00:00:00)
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 字符串类型
     */
    public static String getBeforeDayTimeReturnString(int day,int hour,int minute,int second){
        setCalendarTime(day,hour,minute,second);
        return getFormatDateString(CALENDAR.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间若干小时之前的时间点
     * @param hour 小时数
     * @return 时间类型
     */
    public static Date getBeforeHourTimeReturnDate(int hour){
        CALENDAR.set(Calendar.HOUR_OF_DAY, CALENDAR.get(Calendar.HOUR_OF_DAY) - hour);
        return CALENDAR.getTime();
    }

    /**
     * 将Date类型的日期按指定格式输出
     * @param date 要格式化的日期
     * @param format 格式化模板
     * @return
     */
    public static String getFormatDateString(Date date,String format){
        if(date == null){
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取格式化的当前日期(不带时分秒)
     * @return
     */
    public static String getCurrentDateTimeOnlyYearMonthDay(){
        return getFormatDateString(new Date(),"yyyy-MM-dd");
    }

    /**
     * 获取格式化的当前日期(带时分秒)
     * @return
     */
    public static String getCurrentDateTime(){
        return getFormatDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 设定日期方法
     * @param day 天
     * @param hour 时
     * @param minute 分
     * @param second 秒
     */
    private static void setCalendarTime(int day,int hour,int minute,int second){
        CALENDAR.setTime(new Date());
        CALENDAR.set(Calendar. HOUR_OF_DAY, hour < 0 || hour > 24 ? 0 : hour);
        CALENDAR.set(Calendar. MINUTE, minute < 0 || minute > 60 ? 0 : minute);
        CALENDAR.set(Calendar. SECOND, second < 0 || second > 60 ? 0 : second);
        CALENDAR.set(Calendar. MILLISECOND, 0);
        CALENDAR.add(Calendar. DAY_OF_MONTH, Integer.valueOf("-" + (day < 0 ? 0 : day)));
    }

    /**
     * 判断某一天是否在指定日期之前若干天和之后若干天之间
     * @param date 要判断的某一天
     * @param assignDate 指定的日期
     * @param beforeDays 指定日期之前的若干天
     * @param afterDays 指定日期之后的若干天
     * @return
     */
    public static boolean checkCurrentTimeBetweenTwoDays(Date date, String assignDate, int beforeDays, int afterDays) {
        Calendar beforeDayTime = Calendar.getInstance();
        Calendar afterDayTime = Calendar.getInstance();
        try{
            beforeDayTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(assignDate));
            afterDayTime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(assignDate));
        }catch (ParseException e){
            throw new BusinessException("日期转换异常");
        }
        beforeDayTime.add(Calendar.DAY_OF_MONTH, beforeDays);
        afterDayTime.add(Calendar.DAY_OF_MONTH, -afterDays);

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);

        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(beforeDayTime.getTime());

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(afterDayTime.getTime());

        return currentDate.after(beginDate) && currentDate.before(endDate);
    }

    /**
     * 判断当前时间是否在指定日期之前若干天和之后若干天之间
     * @param date 指定的日期
     * @param beforeDays 指定日期之前的若干天
     * @param afterDays 指定日期之后的若干天
     * @return
     */
    public static boolean checkCurrentTimeBetweenTwoDays(String date, int beforeDays, int afterDays) {
        return checkCurrentTimeBetweenTwoDays(new Date(),date,beforeDays,afterDays);
    }

    /**
     * 获取指定日期的前一天
     * @param specifiedDay 指定日期(字符串)
     * @return 字符串类型的日期
     */
    public static String getSpecifiedDayBefore(String specifiedDay){
        try {
            CALENDAR.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = CALENDAR.get(Calendar.DATE);
        CALENDAR.set(Calendar.DATE,day-1);
        return new SimpleDateFormat("yyyy-MM-dd").format(CALENDAR.getTime());
    }

    /**
     * 获取指定日期的前一天
     * @param specifiedDay 指定日期(字符串)
     * @return 日期类型的日期
     */
    public static Date getSpecifiedDayBeforeReturnDate(String specifiedDay){
        try {
            CALENDAR.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = CALENDAR.get(Calendar.DATE);
        CALENDAR.set(Calendar.DATE,day-1);
        return CALENDAR.getTime();
    }

    /**
     * 判断某一日期是否在当前时间前若干天之前
     * [举个例子:即判断2020-10-12 是否在当前时间前5天之前,如当前时间是:2020-11-04,也就是判断2020-10-12 是否在 2020-10-30之前]
     * @param dayNum 几天前的天数
     * @return 日期类型的日期
     */
    public static boolean checkTimeBeforeAssignDate(String specificDate,int dayNum){
        CALENDAR.setTime(new Date());
        int day = CALENDAR.get(Calendar.DATE);
        CALENDAR.set(Calendar.DATE,day - dayNum);
        Date time = CALENDAR.getTime();
        logger.info("当前时间的前{}天的时间是:{}", dayNum ,new SimpleDateFormat("yyyy-MM-dd").format(time));

        Calendar currentDate = Calendar.getInstance();
        try {
            currentDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(specificDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate.before(CALENDAR);
    }

    /**
     * 将SQL类型的时间戳转换为标准日期格式的字符串
     * @param timestamp SQL类型的时间戳
     * @param format 日期格式
     * @return
     */
    public static String sqlTimeStampToDateStr(Timestamp timestamp, String format) {
        return new SimpleDateFormat(
                StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format
        ).format(new Date(timestamp.getTime()));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期
     * @param format 日期格式
     * @return
     */
    public static String dateStrToSqlTimeStamp(String dateStr, String format){
        try {
            return new Timestamp(new SimpleDateFormat(
                    StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format
            ).parse(dateStr).getTime()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取得SQL类型的时间戳
     * @param date 时间
     * @return
     */
    public static Timestamp getSqlTimeStamp(Date date){
        return new Timestamp(date.getTime());
    }

    /**
     * 计算两个时间之间的月份差(返回的是月份的差值)
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getPeriodMonth(Date startDate, Date endDate){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }
}











