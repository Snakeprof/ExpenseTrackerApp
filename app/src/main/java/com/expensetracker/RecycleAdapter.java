package com.expensetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by shishir.suvarna on 10/9/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {
    private List<DataModel> dataModelArrayList;

    public RecycleAdapter(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView item_name,amount,desc, mydate, txtid;
        ImageView img;

        public Myholder(View itemView) {
            super(itemView);

            txtid = itemView.findViewById(R.id.txtid);
            img = itemView.findViewById(R.id.profile_image);
            item_name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.amount);
            desc = itemView.findViewById(R.id.desc);

        }
    }


    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {


        DataModel dataModel = dataModelArrayList.get(position);


        switch(dataModel.getItem_name()){
            case "Dinner":
                holder.img.setImageResource(R.drawable.dinner_border);
                break;

            case "Car Petrol":
                holder.img.setImageResource(R.drawable.petrol_border);
                break;

            case "Bike Petrol":
                holder.img.setImageResource(R.drawable.petrol_border);
                break;

            case "Lunch":
                holder.img.setImageResource(R.drawable.lunch_border);
                break;

            case "Drinks":
                holder.img.setImageResource(R.drawable.beer_border);
                break;

            case "Shopping":
                holder.img.setImageResource(R.drawable.shopping_border);
                break;

            case "Grocery":
                holder.img.setImageResource(R.drawable.grocery_border);
                break;

            case "Bill":
                holder.img.setImageResource(R.drawable.bill_border);
                break;

            case "Recharge":
                holder.img.setImageResource(R.drawable.recharge_border);
                break;

            case "Servent":
                holder.img.setImageResource(R.drawable.servent_border);
                break;

                default:
                    holder.img.setImageResource(R.drawable.other_border);
                    break;



        }
        holder.item_name.setText(dataModel.getItem_name());
        holder.txtid.setText(dataModel.get_id());
        holder.amount.setText(dataModel.getAmt());

        TimeController timeController = new TimeController();
        String newFormat = timeController.GetFormatedDate(dataModel.getmydate());

        holder.desc.setText(dataModel.getDesc()+"\n"+ newFormat);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}
