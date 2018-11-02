package com.expensetracker.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expensetracker.Activity.Allexpenses_Activity;
import com.expensetracker.Activity.ExpensesDetailViewActivity;
import com.expensetracker.Activity.ReportsActivity;
import com.expensetracker.Adapter.FilterAdapter;
import com.expensetracker.Adapter.SpinnerAdapterFilter;
import com.expensetracker.Controller.ExpensesSQLiteDBHelper;
import com.expensetracker.Controller.TimeController;
import com.expensetracker.Model.DataModel;
import com.expensetracker.Model.ItemData;
import com.expensetracker.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    SpinnerAdapterFilter ad1;
    Spinner spinner_category;
    Spinner spinner_spenton;
    String category="";
    String spentOn = "";
    ListView listview_expense_data_filterwise;
    private List<DataModel> datamodel;
    ExpensesSQLiteDBHelper db;
    TimeController timeController;
    FilterAdapter adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category, container,false);


        spinner_category =  view.findViewById(R.id.spinner_category);
        spinner_spenton =  view.findViewById(R.id.spinner_spenton);
        listview_expense_data_filterwise = view.findViewById(R.id.listview_expense_data_filterwise);
        datamodel = new ArrayList<>();


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

        ad1 = new SpinnerAdapterFilter(getActivity(), R.layout.filter_spinner_with_image, R.id.txtspinneritem, list);
        spinner_category.setAdapter(ad1);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                if(v!=null) {
                    TextView mytextview = v.findViewById(R.id.txtspinneritem);

                    //TextView txtselect_name = v.findViewById(R.id.txtspinneritem);
                    //selected_item_spendon = t.getText().toString();
                    if (mytextview.getText().toString() != null) {
                        category = mytextview.getText().toString();
                        triggerListView();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Spinner with images for FOR Who?
        ArrayList<ItemData> list_for = new ArrayList<>();
        list_for.add(new ItemData("All", R.drawable.other_border));
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
        SpinnerAdapterFilter ad_for1 = new SpinnerAdapterFilter(getActivity(), R.layout.filter_spinner_with_image, R.id.txtspinneritem, list_for);
        spinner_spenton.setAdapter(ad_for1);
        spinner_spenton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                if(v!=null) {
                    TextView t = v.findViewById(R.id.txtspinneritem);
                    if (t.getText().toString() != null) {
                        spentOn =  t.getText().toString();
                        triggerListView();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        listview_expense_data_filterwise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataModel dmodel = datamodel.get(position);
                Intent myIntent = new Intent(getActivity(), ExpensesDetailViewActivity.class);
                myIntent.putExtra("data_id",dmodel.get_id());
                startActivity(myIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void triggerListView(){
        timeController = new TimeController();
        db = new ExpensesSQLiteDBHelper(getActivity());

        datamodel = db.getCategoryWiseData(category, spentOn);
        adapter = new FilterAdapter(getActivity(), datamodel);


        System.out.println("DATABASE SHISHIR "+ datamodel);
        //final ArrayAdapter<DataModel> adapter = new ArrayAdapter<DataModel>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, datamodel);
        listview_expense_data_filterwise.setAdapter(adapter);

    }
}
