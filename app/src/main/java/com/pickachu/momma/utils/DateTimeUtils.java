package com.pickachu.momma.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vaibhavsinghal on 7/11/15.
 */
public class DateTimeUtils {

    public static final String SERVER_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SERVER_DATE = "yyyy-MM-dd";
    public static final String APP_DATE_TIME = "dd-MMM-yyyy hh:mm: aa";
    public static final String APP_DATE = "dd-MMM-yyyy";
    public static final String APP_DATE_NO_YEAR = "dd-MMM";
    public static final String APP_DAY = "EEE";

    public static SimpleDateFormat getServerSimpleDateFormat() {
        return new SimpleDateFormat(SERVER_DATE_TIME);
    }

    public static Date extractDateFromString(String inputTimeFormat, String stringifiedDateTime) {
        DateFormat df = new SimpleDateFormat(inputTimeFormat);
        Date date = new Date();
        try {
            date = df.parse(stringifiedDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringDateTimeToAnother(String inputTimeFormat, String outputTimeFormat, String stringifiedDateTime) {
        String dateTimeStringToReturn = "";
        Date extractedDate = extractDateFromString(inputTimeFormat, stringifiedDateTime);
        try {
            dateTimeStringToReturn = new SimpleDateFormat(outputTimeFormat).format(extractedDate);
        } catch (Exception e) {
            e.printStackTrace();
            return stringifiedDateTime;
        }
        return dateTimeStringToReturn;
    }

    /*
        Return the current date as String
         */
    public static String currDateInString() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat(SERVER_DATE);

        return df.format(c.getTime());
    }

    /* Return the yesterday date as String */

    public static String yesterdayDateInString() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -24);

        SimpleDateFormat df = new SimpleDateFormat(SERVER_DATE_TIME);

        return df.format(calendar.getTime());
    }

    /*
        Return the current time as String
         */
    public static String currDateTimeInString() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = getServerSimpleDateFormat();
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return df.format(c.getTime());
    }

    public static String convertISTToUTC(String timeInIST) {

        Date dateObjectForTimeStamp = extractDateFromString(DateTimeUtils.SERVER_DATE_TIME, timeInIST);
        Calendar tempCalendarForUTC = Calendar.getInstance();

        tempCalendarForUTC.setTime(dateObjectForTimeStamp);
        tempCalendarForUTC.add(Calendar.HOUR_OF_DAY, -5);
        tempCalendarForUTC.add(Calendar.MINUTE, -30);

        return getServerSimpleDateFormat().format(tempCalendarForUTC.getTimeInMillis());
    }

    /**
     * Called for converting UTC time to IST time
     *
     * @param timeInUTC String with UTC time
     * @return String which is time in IST
     */
    public static String convertUTCToIST(String timeInUTC) {
        String time_parsed = null;
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        try {
            time_parsed = outputDateFormat.format(inputDateFormat.parse(timeInUTC));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time_parsed;
    }

    public static String stringifyOrderDateTimeFromServer(String serverOrderDateTime) {

        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat outputDateFormat = new SimpleDateFormat("hh:mm a");
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String readableOrderDateTime = "";

        try {
            readableOrderDateTime = outputDateFormat.format(inputDateFormat.parse(serverOrderDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readableOrderDateTime;
    }
}
