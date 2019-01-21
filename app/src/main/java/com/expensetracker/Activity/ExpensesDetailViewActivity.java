package com.expensetracker.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.expensetracker.Controller.ExpensesSQLiteDBHelper;
import com.expensetracker.R;
import com.expensetracker.Controller.TimeController;

public class ExpensesDetailViewActivity extends AppCompatActivity {
    String data_id="";
    TextView txtname, txtdesc, txtamount, txtforwho, txtdate, txtduration;
    ImageView imgItem, imgForwho, btn_delete;
    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Expense Summary");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtname = findViewById(R.id.txtitem);
        txtdesc = findViewById(R.id.txtdesciption);
        txtamount = findViewById(R.id.txtamount);
        txtforwho = findViewById(R.id.txtforwho);
        txtduration = findViewById(R.id.txtduration);
        txtdate = findViewById(R.id.txtdate);
        imgItem = findViewById(R.id.img_item);
        imgForwho = findViewById(R.id.img_forwho);
        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getConfirmation();
            }
        });

        if(getIntent().getExtras()!=null) {
            data_id = getIntent().getStringExtra("data_id");
            GetDetailByID();
        }else{
            mainActivity.showMessage("Error", "Something went wrong.");
            finish();
        }
    }

    public void GetDetailByID() {
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(this);
        int id = Integer.parseInt(data_id);
        Cursor data_details = db.getAllDataById(id);

        if (data_details.getCount() == 0) {
            // show message
            mainActivity.showMessage("Error", "Nothing found");

        } else {

            data_details.moveToFirst();
            txtname.setText("Spent On "+data_details.getString(1));

            if(!data_details.getString(2).equals("") || data_details.getString(2)!=null) {
                txtdesc.setText("Description: \n" + data_details.getString(2));
            }
            txtamount.setText("Rs: "+data_details.getString(3)+ "/-");
            txtforwho.setText("You spent on "+data_details.getString(1) + " for " + data_details.getString(7));

            if(!data_details.getString(5).equals("Month")) {
                txtduration.setText("This expenses was for " + data_details.getString(5) + " Month.");
            }

            TimeController timeController = new TimeController();
            String newFormat = timeController.GetFormatedDate(data_details.getString(6));
            txtdate.setText(newFormat);



            switch(data_details.getString(1)){
                case "Dinner":
                    imgItem.setImageResource(R.drawable.dinner_border);
                    break;

                case "Car Petrol":
                    imgItem.setImageResource(R.drawable.petrol_border);
                    break;

                case "Bike Petrol":
                    imgItem.setImageResource(R.drawable.petrol_border);
                    break;

                case "Lunch":
                    imgItem.setImageResource(R.drawable.lunch_border);
                    break;

                case "Drinks":
                    imgItem.setImageResource(R.drawable.beer_border);
                    break;

                case "Shopping":
                    imgItem.setImageResource(R.drawable.shopping_border);
                    break;

                case "Grocery":
                    imgItem.setImageResource(R.drawable.grocery_border);
                    break;

                case "Bill":
                    imgItem.setImageResource(R.drawable.bill_border);
                    break;

                case "Recharge":
                    imgItem.setImageResource(R.drawable.recharge_border);
                    break;

                case "Servent":
                    imgItem.setImageResource(R.drawable.servent_border);
                    break;

                default:
                    imgItem.setImageResource(R.drawable.other_border);
                    break;



            }

            switch(data_details.getString(7)){
                case "Mother":
                    imgForwho.setImageResource(R.drawable.mother);
                    break;

                case "Father":
                    imgForwho.setImageResource(R.drawable.father);
                    break;

                case "Sister":
                    imgForwho.setImageResource(R.drawable.sister);
                    break;


                case "Brother":
                    imgForwho.setImageResource(R.drawable.brother);
                    break;

                case "Wife":
                    imgForwho.setImageResource(R.drawable.wife);
                    break;


                case "Children":
                    imgForwho.setImageResource(R.drawable.kids);
                    break;


                case "Family":
                    imgForwho.setImageResource(R.drawable.family);
                    break;


                case "Friends":
                    imgForwho.setImageResource(R.drawable.friends);
                    break;


                case "Self":
                    imgForwho.setImageResource(R.drawable.self);
                    break;

                case "Other":
                    imgForwho.setImageResource(R.drawable.other_people);
                    break;

                default:
                    imgItem.setImageResource(R.drawable.other);
                    break;



            }
            //imgItem.setText(data_details.getString(1));
            //imgForwho.setText(data_details.getString(1));

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void getConfirmation(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExpensesDetailViewActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_delete_black);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                deleteRecord();
                // Write your code here to invoke YES event

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public void deleteRecord(){

        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(ExpensesDetailViewActivity.this);
        int id = Integer.parseInt(data_id);
        Boolean response = db.deleteData(id);
        if (response) {
            Toast.makeText(ExpensesDetailViewActivity.this, "Delete Data Successfully", Toast.LENGTH_LONG).show();

            finish();
        } else {
            mainActivity.showMessage("Error", "Something went wrong while deleting data.");
        }
    }

}
