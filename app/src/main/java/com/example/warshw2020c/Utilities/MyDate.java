package com.example.warshw2020c.Utilities;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;



public class MyDate {
    public static enum monthName {
        Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
    };

    final static int MINIMUM_YEAR = 2010;
    // MyDate date=new MyDate(19, 6, 2015);

    private int day;
    private int month;
    private int year;
    private final static int[] DAYS_MONTHS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };



    public MyDate(int day, int month, int year) {
        setMonthNDay(day, month);
        setYear(year);
    }
    public MyDate(long timeStamp){
        String parseMeToObject  = parseTimeStampToString(timeStamp);
        String[] splitToFields = parseMeToObject.split("-");
        year = Integer.parseInt(splitToFields[0]);
        month = Integer.parseInt(splitToFields[1]);
        day = Integer.parseInt(splitToFields[2]);

    }


    public MyDate() { } // new MyDate().parseDateFromTimeStamp(timeStamp);

    // date Exception //
    public static int makeDate(Scanner s) {
        int dateNum = 0;
        while (dateNum == 0) {
            try {
                dateNum = Integer.parseInt(s.nextLine());
            } catch (Exception e) {
                System.err.println("Error! Wrong input. Try again!");
            }
        }
        return dateNum;
    }

    public MyDate(Scanner scn) {
        System.out.println("Insert Day and Month (numbers only) : ");
        setMonthNDay(makeDate(scn), makeDate(scn));
        System.out.println("Insert year (4 Digits) :");
        setYear(makeDate(scn));
    }


    private void setYear(int year) {
        if (year < MINIMUM_YEAR) {
            this.year = MINIMUM_YEAR;
        } else
            this.year = year;
    }

    private void setMonthNDay(int day, int month) {
        this.month = month;
        if (month > 12 || month < 1) { // case is invalid is go to January
            System.out.println(day + "/" + month);
            System.out.println("Wrong month input - goes January by Default.");
            this.month = 1;
            this.day = 31;
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            this.day = 31; // max days in month
        } else if (month == 2) {
            this.day = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            this.day = 30;
        }

        setDay(day); // here we really set object's day.
    }

    private void setDay(int day) {
        if (day > 0 && day < this.day)
            this.day = day;
        else
            this.day = 1;
    }

    // AVAILABLE IN API 26 and above
//    public int daysCount(MyDate d) {
//        LocalDate enter = LocalDate.of(year, month, day);
//        LocalDate out = LocalDate.of(d.year, d.month, d.day);
//        Period period = Period.between(enter, out);
//        int diff = Math.abs(period.getDays() + period.getMonths() * DAYS_MONTHS[month - 1] + period.getYears());
//        return diff;
//    }

    // AVAILABLE IN API 26 and above
//    public int yearsCount(MyDate endDate) {
//        return (int) (daysCount(endDate)) / 365;
//        // not considering leap year and maybe other stuff
//    }

    @Override
    public String toString() {
        String nDay = day + "";
        String nMonth = month + "";

        if (day < 10)
            nDay = "0" + nDay;
        if (month < 10)
            nMonth = "0" + nMonth;

        return nDay + "/" + nMonth + "/" + year;
    }

    public int getYear() {
        return this.year;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public String getMonthYearName() {
        monthName monthStr = monthName.values()[(this.month - 1)];
        return monthStr.toString() + this.year;
    }

    public boolean before(MyDate date) {
        if (date == null)
            return true;
        if (this.year < date.getYear())
            return true;
        else if (this.year == date.getYear() && this.month < date.getMonth())
            return true;
        else if (this.year == date.getYear() && this.month == date.getMonth() && this.day < date.getDay())
            return true;
        return false;
    }

    public boolean after(MyDate date) {
        if (date == null)
            return true;
        if (this.year > date.getYear())
            return true;
        else if (this.year == date.getYear() && this.month > date.getMonth())
            return true;
        else if (this.year == date.getYear() && this.month == date.getMonth() && this.day > date.getDay())
            return true;
        return false;
    }

    public static MyDate parseFromString(String date) {
        //format: DD/MM/YYYY
        MyDate tempDate;
        try {
            String[] splitToFields = date.split("/");
            int day = Integer.parseInt(splitToFields[0]);
            int month = Integer.parseInt(splitToFields[1]);
            int year = Integer.parseInt(splitToFields[2]);
            tempDate = new MyDate(day, month, year);
        }catch (Exception e){
            tempDate = parseFromHtmlString(date);
        }
        return tempDate;
    }
    public static MyDate parseFromHtmlString(String date) {
        //format: YYYY-MM-DD
        String[] splitToFields = date.split("-");
        int year = Integer.parseInt(splitToFields[0]);
        int month = Integer.parseInt(splitToFields[1]);
        int day = Integer.parseInt(splitToFields[2]);
        return new MyDate(day, month, year);
    }
    public static MyDate getDateFromUser(Scanner scn) {
        System.out.println("enter date 'day' 'month' 'year' (example :23 06 1994)");
        int day = scn.nextInt();
        int month = scn.nextInt();
        int year = scn.nextInt();
        scn.nextLine(); // clear buffer
        return new MyDate(day, month, year);
    }

    public static String parseTimeStampToString(long timeStamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp * 1000);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }

}
