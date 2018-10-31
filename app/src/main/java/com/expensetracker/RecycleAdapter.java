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
    List<DataModel> dataModelArrayList;

    public RecycleAdapter(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView item_name,amount,desc, mydate;
        ImageView img;

        public Myholder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.profile_image);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            amount = (TextView) itemView.findViewById(R.id.amount);
            desc = (TextView) itemView.findViewById(R.id.desc);

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
                holder.img.setImageResource(R.drawable.dinner);
                break;

            case "Car Petrol":
                holder.img.setImageResource(R.drawable.petrol);
                break;

            case "Bike Petrol":
                holder.img.setImageResource(R.drawable.petrol);
                break;

            case "Lunch":
                holder.img.setImageResource(R.drawable.lunch);
                break;

            case "Drinks":
                holder.img.setImageResource(R.drawable.beer);
                break;

            case "Shopping":
                holder.img.setImageResource(R.drawable.shopping);
                break;

            case "Grocery":
                holder.img.setImageResource(R.drawable.grocery);
                break;

            case "Bill":
                holder.img.setImageResource(R.drawable.bill);
                break;

            case "Recharge":
                holder.img.setImageResource(R.drawable.recharge);
                break;

            case "Servent":
                holder.img.setImageResource(R.drawable.servent);
                break;

                default:
                    holder.img.setImageResource(R.drawable.other);
                    break;



        }
        holder.item_name.setText(dataModel.getItem_name());
        holder.amount.setText(dataModel.getAmt());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        String t = dataModel.getmydate();

        System.out.println("Dateformat>>>>>>>>>>>" + t);

        Date date = null;
        try {
            date = dateFormat.parse(t);
            System.out.println("Dateformat>>>>>>>>>>>" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        String newFormat = formatter.format(date);
        System.out.println(".....Date..."+newFormat);

        /*Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = c.get(Calendar.MONTH)+1;*/


        holder.desc.setText(dataModel.getDesc()+" "+ newFormat);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}
