package com.expensetracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.expensetracker.Model.DataModel;
import com.expensetracker.Controller.ExpensesSQLiteDBHelper;
import com.expensetracker.R;
import com.expensetracker.Adapter.RecycleAdapter;
import com.expensetracker.Adapter.RecyclerTouchListener;
import com.expensetracker.Controller.TimeController;

import java.util.ArrayList;
import java.util.List;

public class Allexpenses_Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecycleAdapter recycler;
    TimeController timeController;
    private List<DataModel> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Setting the page title
        timeController = new TimeController();
        setTitle("Expense for " + timeController.GetCurrentMonth() + ", " + timeController.GetCurrentYear());

        datamodel = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        //ListView listView_data = findViewById(R.id.list_data);
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);


        datamodel = db.getAllMontlydata(timeController.GetIntCurrentMonth(), timeController.GetIntCurrentYear());
        recycler = new RecycleAdapter(datamodel);


        Log.i("Shishir-data", "" + datamodel);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                DataModel dmodel = datamodel.get(position);
                //Toast.makeText(getApplicationContext(), dmodel.get_id() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(Allexpenses_Activity.this, ExpensesDetailViewActivity.class);
                myIntent.putExtra("data_id",dmodel.get_id());
                startActivity(myIntent);
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {


            @Override
            public void onClick(View view, int position) {

                Log.d("Shishir debug", "Reached");
                //DataModel dmodel = datamodel.get(position);
                //Toast.makeText(getApplicationContext(), dmodel.getItem_name() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("Shishir debug", "LONG Reached");
            }
        }));*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        recycler.notifyDataSetChanged();
        super.onResume();
    }
}
