package com.expensetracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
}
