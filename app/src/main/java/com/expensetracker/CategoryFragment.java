package com.expensetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container,false);


        final Spinner spinner_category =  view.findViewById(R.id.spinner_category);
        final Spinner spinner_spenton =  view.findViewById(R.id.spinner_spenton);

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

        com.expensetracker.SpinnerAdapter ad = new com.expensetracker.SpinnerAdapter(getActivity(), R.layout.spinner_with_image, R.id.txt, list);
        spinner_category.setAdapter(ad);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                TextView t = view.findViewById(R.id.txt);
                //selected_item_spendon = t.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        com.expensetracker.SpinnerAdapter ad_for = new com.expensetracker.SpinnerAdapter(getActivity(), R.layout.spinner_with_image, R.id.txt, list_for);
        spinner_spenton.setAdapter(ad_for);
        spinner_spenton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.txt);
                //spinner_spenton = t.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
