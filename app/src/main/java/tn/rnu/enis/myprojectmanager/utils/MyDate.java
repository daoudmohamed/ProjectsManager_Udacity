package tn.rnu.enis.myprojectmanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Mohamed on 19/05/2015.
 */
public class MyDate {
    private int day, mouth, year;
    public MyDate(String date){
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(date);
        matcher.matches();
        year = Integer.parseInt(matcher.group(1));
        mouth = Integer.parseInt(matcher.group(2));
        day = Integer.parseInt(matcher.group(3));
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
