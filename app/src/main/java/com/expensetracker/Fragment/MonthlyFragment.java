package com.expensetracker.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensetracker.Controller.ExpensesSQLiteDBHelper;
import com.expensetracker.Controller.TimeController;
import com.expensetracker.Model.DataModel;
import com.expensetracker.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class MonthlyFragment extends Fragment {

    TimeController timeController;
    int max_amount1 = 0;
    String max_amt_name1 = "";
    int tamount1 = 0;
    TextView txtmaxAmt1;
    TextView txttotalamt1;
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
    public MonthlyFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        syncConnPref_currency = sharedPref.getString("key_upload_quality", "");

        timeController = new TimeController();
        TextView txtPeriod = view.findViewById(R.id.txt_current_month);
        txtPeriod.setText("Overall Expenses for " + timeController.GetCurrentMonth());

        txttotalamt1 = view.findViewById(R.id.txt_overall_expense);
        txtmaxAmt1 = view.findViewById(R.id.txt_highest_amount);

        launchPieDiagram(view);


        return view;
    }

    public void launchPieDiagram(View view) {

        final ExpensesSQLiteDBHelper db = new ExpensesSQLiteDBHelper(getContext());
        List<DataModel> retrivedataList1 = db.getMonthlydata(timeController.GetIntCurrentYear(), timeController.GetIntCurrentMonth());
        //System.out.println(">>>>>>>>>>date>>>>>>>>>>"+ retrivedataList1);


        PieChartView pieChartView = view.findViewById(R.id.monthlychart);
        List<SliceValue> pieData = new ArrayList<>();
        tamount1 = 0;
        for (int i = 0; i < retrivedataList1.size(); i++) {
            tamount1 = tamount1 + Integer.parseInt(retrivedataList1.get(i).getAmount());
            if (max_amount1 < Integer.parseInt(retrivedataList1.get(i).getAmount())) {
                max_amount1 = Integer.parseInt(retrivedataList1.get(i).getAmount());
                max_amt_name1 = retrivedataList1.get(i).getXname().toString();
            }


            pieData.add(new SliceValue(Integer.parseInt(retrivedataList1.get(i).getAmount()), color[i]).setLabel(retrivedataList1.get(i).getXname().toString() + " Rs:" + retrivedataList1.get(i).getAmount()));
            System.out.println(">>>>>>>>>>date>>>>>>>>>>" + retrivedataList1.get(i).getXname().toString());
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("Current Month").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#FFFFFF"));
        pieChartView.setPieChartData(pieChartData);


        try {
            if (Integer.parseInt(syncConnPref_currency) == 1) {

                txtmaxAmt1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar32, 0, 0, 0);
                txttotalamt1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar32, 0, 0, 0);
            } else {

                txtmaxAmt1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupee32, 0, 0, 0);
                txttotalamt1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rupee32, 0, 0, 0);


            }
        } catch (NumberFormatException e) {

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>YearlyFragment line 118 - not a number");
        }
        txtmaxAmt1.setText("Most Money Spent On: " + max_amt_name1 + "-" + String.valueOf(max_amount1));
        txttotalamt1.setText("Total Spent : " + String.valueOf(tamount1));

    }
}
