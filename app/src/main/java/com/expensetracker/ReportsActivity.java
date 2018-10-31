package com.expensetracker;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ReportsActivity extends AppCompatActivity {

    TimeController timeController;
    List<DataModel> retrivedataList = new ArrayList<DataModel>();
    private int[] color={
            Color.BLUE,
            Color.RED,
            Color.MAGENTA,
            Color.CYAN,
            Color.YELLOW,
            Color.parseColor("#3399ff"),
            Color.parseColor("#4f4840"),
            Color.parseColor("#bada55"),
            Color.parseColor("#161525"),
            Color.parseColor("#79887a"),
            Color.parseColor("#a6bbf2")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timeController = new TimeController();
        TextView txtPeriod = findViewById(R.id.txt_current_month);
        txtPeriod.setText("Overall Expenses for Year "+timeController.GetCurrentYear());
        setTitle("Yearly Expenses Report");

        launchPieDiagram();
    }

    public void launchPieDiagram(){
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);
        retrivedataList = db.getYearlydata();
        //System.out.println(">>>>>>>>>>date>>>>>>>>>>"+ retrivedataList);


        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();

        for(int i=0; i< retrivedataList.size() ;i++){

            pieData.add(new SliceValue(Integer.parseInt(retrivedataList.get(i).getAmount()), color[i]).setLabel(retrivedataList.get(i).getXname().toString()+" Rs:"+retrivedataList.get(i).getAmount()));
            System.out.println(">>>>>>>>>>date>>>>>>>>>>"+ retrivedataList.get(i).getXname().toString());
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("Expenses This Year").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FF4081"));
        pieChartView.setPieChartData(pieChartData);

    }
}
