package com.expensetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Allexpenses_Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecycleAdapter recycler;
    List<DataModel> datamodel;
    TimeController timeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting the page title
        timeController = new TimeController();
        setTitle("Expense for "+ timeController.GetCurrentMonth()+", "+timeController.GetCurrentYear());

        datamodel =new ArrayList<DataModel>();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        //ListView listView_data = findViewById(R.id.list_data);
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);


        datamodel=  db.getdata();
        recycler =new RecycleAdapter(datamodel);


        Log.i("Shishir-data",""+datamodel);
        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);


    }

}
