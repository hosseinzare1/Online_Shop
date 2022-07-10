package com.example.onlineshop.utils;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Created by JFP on 11/22/2017.
 */
@SuppressWarnings("deprecation")
public class Utility {
    public String strWeekDay = "";
    public String strMonth = "";
@SuppressWarnings({"SwitchStatementWithTooFewBranches", "IfStatementWithIdenticalBranches", "deprecation"})
private class SolarCalendar {
        int date;
        int month;
        int year;
        public SolarCalendar() {
            Date Gregorian_date = new Date();
            calcSolarCalendar(Gregorian_date);
        }

        private void calcSolarCalendar(Date Gregorian_date) {
            int ld;
            int gregorianYear = Gregorian_date.getYear() + 1900;
            int gregorianMonth = Gregorian_date.getMonth() + 1;
            int gregorianDate = Gregorian_date.getDate();
            int WeekDay = Gregorian_date.getDay();
            int[] buf1 = new int[12];
            int[] buf2 = new int[12];
            buf1[0] = 0;
            buf1[1] = 31;
            buf1[2] = 59;
            buf1[3] = 90;
            buf1[4] = 120;
            buf1[5] = 151;
            buf1[6] = 181;
            buf1[7] = 212;
            buf1[8] = 243;
            buf1[9] = 273;
            buf1[10] = 304;
            buf1[11] = 334;
            buf2[0] = 0;
            buf2[1] = 31;
            buf2[2] = 60;
            buf2[3] = 91;
            buf2[4] = 121;
            buf2[5] = 152;
            buf2[6] = 182;
            buf2[7] = 213;
            buf2[8] = 244;
            buf2[9] = 274;
            buf2[10] = 305;
            buf2[11] = 335;
            if ((gregorianYear % 4) != 0) {
                date = buf1[gregorianMonth - 1] + gregorianDate;
                if (date > 79) {
                    date = date - 79;
                    if (date <= 186) {
                        switch (date % 31) {
                            case 0:
                                month = date / 31;
                                date = 31;
                                break;
                            default:
                                month = (date / 31) + 1;
                                date = (date % 31);
                                break;
                        }
                    } else {
                        date = date - 186;
                        switch (date % 30) {
                            case 0:
                                month = (date / 30) + 6;
                                date = 30;
                                break;
                            default:
                                month = (date / 30) + 7;
                                date = (date % 30);
                                break;
                        }
                    }
                    year = gregorianYear - 621;
                } else {
                    if ((gregorianYear > 1996) && (gregorianYear % 4) == 1) {
                        ld = 11;
                    } else {
                        ld = 10;
                    }
                    date = date + ld;
                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 9;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 10;
                            date = (date % 30);
                            break;
                    }
                    year = gregorianYear - 622;
                }
            } else {
                date = buf2[gregorianMonth - 1] + gregorianDate;
                if (gregorianYear >= 1996) {
                    ld = 79;
                } else {
                    ld = 80;
                }
                if (date > ld) {
                    date = date - ld;
                    if (date <= 186) {
                        switch (date % 31) {
                            case 0:
                                month = (date / 31);
                                date = 31;
                                break;
                            default:
                                month = (date / 31) + 1;
                                date = (date % 31);
                                break;
                        }
                        year = gregorianYear - 621;
                    } else {
                        date = date - 186;
                        switch (date % 30) {
                            case 0:
                                month = (date / 30) + 6;
                                date = 30;
                                break;
                            default:
                                month = (date / 30) + 7;
                                date = (date % 30);
                                break;
                        }
                        year = gregorianYear - 621;
                    }
                } else {
                    date = date + 10;
                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 9;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 10;
                            date = (date % 30);
                            break;
                    }
                    year = gregorianYear - 622;
                }
            }
            switch (month) {
                case 1:
                    strMonth = "فروردين";
                    break;
                case 2:
                    strMonth = "ارديبهشت";
                    break;
                case 3:
                    strMonth = "خرداد";
                    break;
                case 4:
                    strMonth = "تير";
                    break;
                case 5:
                    strMonth = "مرداد";
                    break;
                case 6:
                    strMonth = "شهريور";
                    break;
                case 7:
                    strMonth = "مهر";
                    break;
                case 8:
                    strMonth = "آبان";
                    break;
                case 9:
                    strMonth = "آذر";
                    break;
                case 10:
                    strMonth = "دي";
                    break;
                case 11:
                    strMonth = "بهمن";
                    break;
                case 12:
                    strMonth = "اسفند";
                    break;
            }
            switch (WeekDay) {
                case 0:
                    strWeekDay = "يکشنبه";
                    break;
                case 1:
                    strWeekDay = "دوشنبه";
                    break;
                case 2:
                    strWeekDay = "سه شنبه";
                    break;
                case 3:
                    strWeekDay = "چهارشنبه";
                    break;
                case 4:
                    strWeekDay = "پنج شنبه";
                    break;
                case 5:
                    strWeekDay = "جمعه";
                    break;
                case 6:
                    strWeekDay = "شنبه";
                    break;
            }
        }
    }
    public static String getCurrentSolarHijri() {
        Locale loc = new Locale("en_US");
        Utility util = new Utility();
        SolarCalendar sc = util.new SolarCalendar();
        return sc.year + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }
    public String day_of_week() {
        Date Gregorian_date = new Date();
        Gregorian_date.setDate(6);
        Gregorian_date.setMonth(12);
        Gregorian_date.setYear(1396);
        SolarCalendar solarCalendar = new SolarCalendar();
        solarCalendar.calcSolarCalendar(Gregorian_date);
        return strWeekDay;
    }
    public String day_of_week(int year, int month, int day) {
        Calendar now = Calendar.getInstance();
        now.set(year, month, day);
        String[] strDays = new String[]{"یکشنبه", "دوشنبه", "سشنبه", "چهارشنبه", "پنجشنبه",
                "جمعه", "شنبه"};
        return strDays[now.get(Calendar.DAY_OF_WEEK)];
    }
}