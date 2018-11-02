package com.expensetracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.expensetracker.Controller.TimeController;
import com.expensetracker.Model.DataModel;
import com.expensetracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishir.suvarna on 11/2/2018.
 */

public class FilterAdapter extends ArrayAdapter<DataModel> {
    public FilterAdapter(Context context, List<DataModel> dataModel1) {
        super(context, 0, dataModel1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemview, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.item_name);
        TextView tvHome = convertView.findViewById(R.id.desc);
        TextView txtid = convertView.findViewById(R.id.txtid);
        ImageView img = convertView.findViewById(R.id.profile_image);
        TextView amount = convertView.findViewById(R.id.amount);

        switch(dataModel.getItem_name()){
            case "Dinner":
                img.setImageResource(R.drawable.dinner_border);
                break;

            case "Car Petrol":
                img.setImageResource(R.drawable.petrol_border);
                break;

            case "Bike Petrol":
                img.setImageResource(R.drawable.petrol_border);
                break;

            case "Lunch":
                img.setImageResource(R.drawable.lunch_border);
                break;

            case "Drinks":
                img.setImageResource(R.drawable.beer_border);
                break;

            case "Shopping":
                img.setImageResource(R.drawable.shopping_border);
                break;

            case "Grocery":
                img.setImageResource(R.drawable.grocery_border);
                break;

            case "Bill":
                img.setImageResource(R.drawable.bill_border);
                break;

            case "Recharge":
                img.setImageResource(R.drawable.recharge_border);
                break;

            case "Servent":
                img.setImageResource(R.drawable.servent_border);
                break;

            default:
                img.setImageResource(R.drawable.other_border);
                break;



        }

        // Populate the data into the template view using the data object
        tvName.setText(dataModel.getItem_name());
        txtid.setText(dataModel.get_id());
        amount.setText(dataModel.getAmt());

        TimeController timeController = new TimeController();
        String newFormat = timeController.GetFormatedDate(dataModel.getmydate());
        tvHome.setText(dataModel.getDesc()+"\n"+ newFormat);



        // Return the completed view to render on screen
        return convertView;
    }

}
