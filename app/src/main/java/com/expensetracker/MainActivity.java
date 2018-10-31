package com.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int pStatus = 0;
    TextView cirular_prg_txt;
    private Handler handler = new Handler();
    String selected_item_spendon = "";
    String selected_forwho = "";
    int amt=0;
    TimeController timeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);

        final EditText editAmt = findViewById(R.id.editTextAmt);
        final EditText editdescription = findViewById(R.id.editdescription);
        final Spinner spinner_items = (Spinner) findViewById(R.id.spinner_items);
        final Spinner spinner_forwho = (Spinner) findViewById(R.id.spinner_forwhom);
        final Spinner spinner_period = (Spinner) findViewById(R.id.spinner_period);
        final TextView txt_current_month = (TextView) findViewById(R.id.txt_current_month);
        Button btn_save = (Button) findViewById(R.id.btn_save);
        Button btn_add = (Button) findViewById(R.id.btn_save_plus);

        timeController = new TimeController();
        String curent_period = timeController.GetCurrentMonth()+" "+timeController.GetCurrentYear();
        txt_current_month.setText(curent_period);

        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //String items_spentOn = spinner_items.getSelectedItem().toString();
                //String items_spentOn = (ArrayList)spinner_items.getSelectedItem();
                //System.out.println("Shishir*****************************" + selected_item_spendon);

                //String forWhom = spinner_forwho.getSelectedItem().toString();

                String duration = spinner_period.getSelectedItem().toString();
                String description = editdescription.getText().toString();
                String amount = editAmt.getText().toString();

                if (!TextUtils.isEmpty(amount)) {
                    boolean response = db.insertData(selected_item_spendon, description, amount, duration, selected_forwho);
                    if (response) {
                        Toast.makeText(MainActivity.this, "Money Deducted", Toast.LENGTH_LONG).show();
                        editAmt.getText().clear();
                        editdescription.getText().clear();
                        // build_progessbar();
                        showdata();


                    } else {
                        Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please Add The Amount", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        /* Spinner dropdown list Period*/
        List<String> ex_duration = new ArrayList<String>();
        ex_duration.add("Month");
        ex_duration.add("1");
        ex_duration.add("2");
        ex_duration.add("3");
        ex_duration.add("6");
        ex_duration.add("12");

        //Creating adapter for all spinner
        ArrayAdapter<String> dataadapter_duration = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ex_duration);
        dataadapter_duration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_period.setAdapter(dataadapter_duration);


        //Spinner with images for FOR Who?
        ArrayList<ItemData> list_for = new ArrayList<>();
        list_for.add(new ItemData("Other", R.drawable.other_people));
        list_for.add(new ItemData("Mother", R.drawable.mother));
        list_for.add(new ItemData("Father", R.drawable.father));
        list_for.add(new ItemData("Sister", R.drawable.sister));
        list_for.add(new ItemData("Brother", R.drawable.brother));
        list_for.add(new ItemData("Wife", R.drawable.wife));
        list_for.add(new ItemData("Children", R.drawable.kids));
        list_for.add(new ItemData("Family", R.drawable.kids));
        list_for.add(new ItemData("Friends", R.drawable.kids));
        list_for.add(new ItemData("Self", R.drawable.self));
        com.expensetracker.SpinnerAdapter ad_for = new com.expensetracker.SpinnerAdapter(this, R.layout.spinner_with_image, R.id.txt, list_for);
        spinner_forwho.setAdapter(ad_for);
        spinner_forwho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.txt);
                selected_forwho = t.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner with images for spend-on
        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("Other", R.drawable.other_border));
        list.add(new ItemData("Car Petrol", R.drawable.petrol_border));
        list.add(new ItemData("Bike Petrol", R.drawable.petrol_border));
        list.add(new ItemData("Lunch", R.drawable.lunch_border));
        list.add(new ItemData("Dinner", R.drawable.dinner_border));
        list.add(new ItemData("Drinks", R.drawable.beer_border));
        list.add(new ItemData("Shopping", R.drawable.shopping_border));
        list.add(new ItemData("Grocery", R.drawable.grocery_border));
        list.add(new ItemData("Bill", R.drawable.bill_border));
        list.add(new ItemData("Recharge", R.drawable.recharge_border));
        list.add(new ItemData("Servent", R.drawable.servent_border));

        com.expensetracker.SpinnerAdapter ad = new com.expensetracker.SpinnerAdapter(this, R.layout.spinner_with_image, R.id.txt, list);
        spinner_items.setAdapter(ad);
        spinner_items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TextView t = view.findViewById(R.id.txt);
                selected_item_spendon = t.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No Message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Shishir - Test", "On Resume Called");
        Calculate_income();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // launch settings activity
            //startActivity(new Intent(MainActivity.this, SettingsActivity.class));

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_all_ex) {
            //Show all expenses in a listview
            Intent intent = new Intent(MainActivity.this, Allexpenses_Activity.class);
            startActivity(intent);
        }else if (id == R.id.action_exp_yearly) {
            //Show all expenses in a listview
            Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void Calculate_income() {

        //Getting stored salary value and conveting it to INT
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String syncConnPref_salary = sharedPref.getString("key_salary_name", "");
        String syncConnPref_bonus = sharedPref.getString("key_bonus_name", "");
        String syncConnPref_incentive = sharedPref.getString("key_incentive_name", "");
        String syncConnPref_freelance = sharedPref.getString("key_freelance_name", "");
        String syncConnPref_other = sharedPref.getString("key_otherincome_name", "");
        String syncConnPref_partner = sharedPref.getString("key_companionincome_name", "");
        Log.d("Shared prefvalue", syncConnPref_salary);

        TextView t = (TextView) findViewById(R.id.txtinfo);

        int t_income = 0, salry = 0, bnus = 0, incen = 0, freeln = 0, other = 0, partner = 0;
        try {
            if (syncConnPref_salary != null) {
                salry = Integer.parseInt(syncConnPref_salary);
                bnus = Integer.parseInt(syncConnPref_bonus);
                incen = Integer.parseInt(syncConnPref_incentive);
                freeln = Integer.parseInt(syncConnPref_freelance);
                partner = Integer.parseInt(syncConnPref_partner);
                other = Integer.parseInt(syncConnPref_other);
            }

            t_income = salry + bnus + incen + freeln + other + partner;

        } catch (NumberFormatException e) {
            System.out.println("Integer Error " + e);
        }

        t.setText("Total Balance Rs: " + t_income);

        build_progessbar(t_income);
    }

    public void build_progessbar(int total_amt) {
        //Getting total expenses this month.
        ExpensesSQLiteDBHelper d = new ExpensesSQLiteDBHelper(this);
        amt = d.get_total_exp_amount_this_month();
        System.out.println("AMT*************************"+amt);

        pStatus = 0;

        final int total_amount = total_amt;

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);

        cirular_prg_txt = (TextView) findViewById(R.id.tv);
        final ProgressBar cirular_prg = (ProgressBar) findViewById(R.id.circularProgressbar);
        cirular_prg.setProgress(0);   // Main Progress
        cirular_prg.setSecondaryProgress(total_amount); // Secondary Progress
        cirular_prg.setMax(total_amount); // Maximum Progress
        cirular_prg.setProgressDrawable(drawable);

        /*ObjectAnimator animation = ObjectAnimator.ofInt(cirular_prg, "progress", 0, green_circle_update);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/
        final int temp = total_amount - amt;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus < temp) {
                    pStatus += 100;
                    System.out.println(">***************>>>>"+pStatus);
                    if (temp > 2000) {

                        pStatus += 485;

                    } else if (temp > 500 && temp < 2000) {

                        pStatus += 128;

                    } else {

                        pStatus += 12;
                    }

                    //To show accurate amount in the loop
                    if (pStatus > temp) {
                        pStatus = total_amount - amt;

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cirular_prg.setSecondaryProgress(total_amount); // Secondary Progress
                            cirular_prg.setProgress(pStatus);
                            //cirular_prg_txt.setText("Rs: " + pStatus + "\n Balnace");
                            cirular_prg_txt.setText(Html.fromHtml("Rs: " + pStatus + "<br><font color=\"#F51616\"> Balance</font>"));
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(14); //thread will take approx 3 seconds

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void showdata() {
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);
        Cursor data_details = db.getAllData();

        if (data_details.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        } else {

            StringBuffer buffer = new StringBuffer();
            while (data_details.moveToNext()) {
                buffer.append("Id :" + data_details.getString(0) + "\n");
                buffer.append("Spent On :" + data_details.getString(1) + "\n");
                buffer.append("Description :" + data_details.getString(2) + "\n");
                buffer.append("Amount :" + data_details.getString(3) + "\n");
                buffer.append("Duration :" + data_details.getString(5) + " Months \n");
                buffer.append("Date :" + data_details.getString(6) + "\n");
                buffer.append("For Whom? :" + data_details.getString(7) + "\n\n");

            }

            // Show all data
            showMessage("Expenses", buffer.toString());

            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}

