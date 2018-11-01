package com.expensetracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shishir.suvarna on 10/26/2018.
 */

public class TimeController {
    private Calendar cal;
    private SimpleDateFormat month_date;
    private SimpleDateFormat w_year;
    private String month_name;
    private String year_name;

    public String GetCurrentMonth(){

        cal = Calendar.getInstance();
        month_date = new SimpleDateFormat("MMMM");
        month_name = month_date.format(cal.getTime());

        return month_name;
    }

    public String GetCurrentYear(){

        cal = Calendar.getInstance();
        w_year = new SimpleDateFormat("YYYY");
        year_name = w_year.format(cal.getTime());

        return year_name;
    }

    public String GetFormatedDate(String mydate){
        String newFormat ="";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        String t = mydate;

        System.out.println("Dateformat>>>>>>>>>>>" + t);

        Date date = null;
        try {
            date = dateFormat.parse(t);
            System.out.println("Dateformat>>>>>>>>>>>" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        newFormat = formatter.format(date);
        System.out.println(".....Date..."+newFormat);

        return newFormat;
    }
}
