package com.expensetracker.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensetracker.Model.DataModel;
import com.expensetracker.Controller.ExpensesSQLiteDBHelper;
import com.expensetracker.Controller.TimeController;
import com.expensetracker.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by shishir.suvarna on 10/31/2018.
 */

public class YearlyFragment extends Fragment {

    TimeController timeController;
    List<DataModel> retrivedataList = new ArrayList<DataModel>();
    int max_amount = 0;
    String max_amt_name = "";
    int tamount = 0;
    TextView txtmaxAmt;
    TextView txttotalamt;
    SharedPreferences sharedPref;
    String syncConnPref_currency;

    private int[] color = {
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

    public YearlyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_yearly, container, false);
        View view = inflater.inflate(R.layout.fragment_yearly, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        syncConnPref_currency = sharedPref.getString("key_upload_quality", "");

        timeController = new TimeController();
        TextView txtPeriod = view.findViewById(R.id.txt_current_month);
        txtPeriod.setText("Overall Expenses for Year " + timeController.GetCurrentYear());

        txttotalamt = view.findViewById(R.id.txt_overall_expense);
        txtmaxAmt = view.findViewById(R.id.txt_highest_amount);

        launchPieDiagram(view);
        return view;
    }

    public void launchPieDiagram(View view) {
        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(getContext());
        retrivedataList = db.getYearlydata(timeController.GetIntCurrentYear());
        //System.out.println(">>>>>>>>>>date>>>>>>>>>>"+ retrivedataList);


        PieChartView pieChartView = view.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();

        for (int i = 0; i < retrivedataList.size(); i++) {
            tamount = tamount + Integer.parseInt(retrivedataList.get(i).getAmount());
            if (max_amount < Integer.parseInt(retrivedataList.get(i).getAmount())) {
                max_amount = Integer.parseInt(retrivedataList.get(i).getAmount());
                max_amt_name = retrivedataList.get(i).getXname().toString();
            }


            pieData.add(new SliceValue(Integer.parseInt(retrivedataList.get(i).getAmount()), color[i]).setLabel(retrivedataList.get(i).getXname().toString() + " Rs:" + retrivedataList.get(i).getAmount()));
            System.out.println(">>>>>>>>>>date>>>>>>>>>>" + retrivedataList.get(i).getXname().toString());
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("Expenses This Year").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FFFFFF"));
        pieChartView.setPieChartData(pieChartData);


        try {
            if (Integer.parseInt(syncConnPref_currency) == 1) {

                txtmaxAmt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar32, 0, 0, 0);
                txttotalamt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar32, 0, 0, 0);
            } else {

                txtmaxAmt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupee32, 0, 0, 0);
                txttotalamt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupee32, 0, 0, 0);


            }
        } catch (NumberFormatException e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>YearlyFragment line 118 - not a number");
        }
        txtmaxAmt.setText("Most Money Spent On: " + max_amt_name + "-" + String.valueOf(max_amount));
        txttotalamt.setText("Total Spent : " + String.valueOf(tamount));

    }
}
