package com.expensetracker.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.expensetracker.Model.DataModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shishir.suvarna on 9/25/2018.
 */

public class ExpensesSQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "expensetrackerdb";
    public static final String EXPENSE_TABLE_NAME = "user_record";
    public static final String EXPENSE_COLUMN_ID = "_id";
    public static final String EXPENSE_COLUMN_XNAME = "xname";
    public static final String EXPENSE_COLUMN_DESC = "description";
    public static final String EXPENSE_COLUMN_AMT = "amount";
    public static final String EXPENSE_COLUMN_PERIOD = "duration";
    public static final String EXPENSE_COLUMN_WHOM = "for_who";
    public static final String EXPENSE_CREATED_AT = "created_at";
    public static final String EXPENSE_FLAG = "flag";

    public ExpensesSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + EXPENSE_TABLE_NAME + " (" +
                EXPENSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXPENSE_COLUMN_XNAME + " TEXT, " +
                EXPENSE_COLUMN_DESC + " TEXT, " +
                EXPENSE_COLUMN_AMT + " INT, UNSIGNED, " +
                EXPENSE_COLUMN_PERIOD + " TEXT, " +
                EXPENSE_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                EXPENSE_COLUMN_WHOM + " TEXT," +
                EXPENSE_FLAG + " INTEGER DEFAULT 0" + ")"); //0 for expenses & 1 for income
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean deleteData(int id){
        SQLiteDatabase db1 = this.getWritableDatabase();
        long result = db1.delete(EXPENSE_TABLE_NAME, EXPENSE_COLUMN_ID + "=" + id, null);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String name1, String desc, String amt, String duration, String whom){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_COLUMN_XNAME, name1);
        contentValues.put(EXPENSE_COLUMN_DESC, desc);
        contentValues.put(EXPENSE_COLUMN_AMT, amt);
        contentValues.put(EXPENSE_COLUMN_PERIOD, duration);
        contentValues.put(EXPENSE_COLUMN_WHOM, whom);


        long result = db1.insert(EXPENSE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+EXPENSE_TABLE_NAME,null);
        return res;
    }
    public Cursor getAllDataById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+EXPENSE_TABLE_NAME+" where "+EXPENSE_COLUMN_ID+" = "+id,null);
        return res;
    }
    public int get_total_exp_amount_this_month(int m, int y){
        int year = y;
        int month = m;
        int total = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("select "+EXPENSE_COLUMN_AMT+" as total_exp from "+EXPENSE_TABLE_NAME,null);
        Cursor res = db.rawQuery("SELECT SUM("+EXPENSE_COLUMN_AMT+") as 'myamount' FROM "+EXPENSE_TABLE_NAME+" WHERE strftime('%Y', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(year)+"' AS INTEGER) and strftime('%m', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(month)+"' AS INTEGER)",null);
        if (res.moveToFirst()) { // data?
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(res));
            //System.out.println(">>>>>>>>>>date>>>>>>>>>>"+res.getString(res.getColumnIndex("myamount")));

            if(res.getString(res.getColumnIndex("myamount"))!= null){
                total = Integer.parseInt(res.getString(res.getColumnIndex("myamount")));
            }

        }
        res.close();

        return total;
    }

    public List<DataModel> getCategoryWiseData(String item, String person){

        String category = item;
        String spendon = person;
        String query_spendon ="";

        if(spendon.equals(null) || spendon.equals("")){
            spendon = "Other";
            query_spendon=  "and "+EXPENSE_COLUMN_WHOM+"='"+spendon+"'";
        }else if(spendon.equals("All")) {
            query_spendon="";
        }else{
            query_spendon=  "and "+EXPENSE_COLUMN_WHOM+"='"+spendon+"'";
        }

        // DataModel dataModel = new DataModel();
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Query","select * from "+EXPENSE_TABLE_NAME+" WHERE "+EXPENSE_COLUMN_XNAME+"='"+category+"' "+query_spendon+" Order By "+ EXPENSE_CREATED_AT);
        Cursor cursor = db.rawQuery("select * from "+EXPENSE_TABLE_NAME+ " WHERE "+EXPENSE_COLUMN_XNAME+"='"+category+"' "+query_spendon+" Order By "+ EXPENSE_CREATED_AT+"",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
            String item_name = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_XNAME));
            String amount = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_AMT));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_DESC));
            String mydate = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_CREATED_AT));
            String data_id = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_ID));


            dataModel.setItem_name(item_name);
            dataModel.set_id(data_id);
            dataModel.setAmt(amount);
            dataModel.setDesc(desc);
            dataModel.setmydate(mydate);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        /*for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getAmt());
        }*/

        //

        return data;

    }


    public List<DataModel> getAllMontlydata(int m, int y){

        int year = y;
        int month = m;
        String total = "";

        //Log.d(">>>>>>>Month/year", ""+String.valueOf(month));
        //Log.d(">>>>>>>Month/year", ""+String.valueOf(year));

        // DataModel dataModel = new DataModel();
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("select * from "+EXPENSE_TABLE_NAME+ " WHERE strftime('%m', "+EXPENSE_CREATED_AT+")='"+String.valueOf(month)+"' and strftime('%Y', "+EXPENSE_CREATED_AT+")='"+String.valueOf(year)+"'",null);

        Cursor cursor = db.rawQuery("select * from "+EXPENSE_TABLE_NAME+" WHERE strftime('%m', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(month)+"' AS INTEGER) and strftime('%Y', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(year)+"' AS INTEGER)",null);

        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;

        //Log.d(">>>>>>>Month/year", "QUERY "+"select * from "+EXPENSE_TABLE_NAME+ " WHERE strftime('%m', "+EXPENSE_CREATED_AT+")='"+String.valueOf(month)+"' and strftime('%Y', "+EXPENSE_CREATED_AT+")='"+String.valueOf(year)+"'");

        while (cursor.moveToNext()) {
            dataModel= new DataModel();


            String item_name = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_XNAME));
            Log.d(">>>>>>>Month/year", "EXPENSE_COLUMN_XNAME "+item_name);
            String amount = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_AMT));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_DESC));
            String mydate = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_CREATED_AT));
            String data_id = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_ID));


            dataModel.setItem_name(item_name);
            dataModel.set_id(data_id);
            dataModel.setAmt(amount);
            dataModel.setDesc(desc);
            dataModel.setmydate(mydate);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getAmt());
        }

        //

        return data;

    }
    public List<DataModel> getMonthlydata(int y, int m){

        List<DataModel> dataList = new ArrayList<DataModel>();
        int year = y;
        int month = m;
        String total = "";

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("select "+EXPENSE_COLUMN_AMT+" as total_exp from "+EXPENSE_TABLE_NAME,null);
        Cursor cursor = db.rawQuery("SELECT Distinct "+EXPENSE_COLUMN_XNAME+" as 'xname' ,SUM("+EXPENSE_COLUMN_AMT+") as 'amount' FROM "+EXPENSE_TABLE_NAME+" WHERE strftime('%Y', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(year)+"' AS INTEGER) and strftime('%m', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(month)+"' AS INTEGER) Group By "+EXPENSE_COLUMN_XNAME,null);

        while (cursor.moveToNext()) { // data?
            DataModel datamodel = new DataModel();
            datamodel.setXname(cursor.getString(cursor.getColumnIndex("xname")));
            datamodel.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
            dataList.add(datamodel);
        }
        cursor.close();

        return dataList;

    }
    public List<DataModel> getYearlydata(int y){

        List<DataModel> dataList = new ArrayList<DataModel>();
        int year = y;
        String total = "";

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("select "+EXPENSE_COLUMN_AMT+" as total_exp from "+EXPENSE_TABLE_NAME,null);
        Cursor cursor = db.rawQuery("SELECT Distinct "+EXPENSE_COLUMN_XNAME+" as 'xname' ,SUM("+EXPENSE_COLUMN_AMT+") as 'amount' FROM "+EXPENSE_TABLE_NAME+" WHERE strftime('%Y', "+EXPENSE_CREATED_AT+")=CAST('"+String.valueOf(year)+"' AS INTEGER) Group By "+EXPENSE_COLUMN_XNAME,null);

        while (cursor.moveToNext()) { // data?
            DataModel datamodel = new DataModel();
            datamodel.setXname(cursor.getString(cursor.getColumnIndex("xname")));
            datamodel.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
            dataList.add(datamodel);
        }
        cursor.close();

        return dataList;

    }

    public List<DataModel> getdata(){
        // DataModel dataModel = new DataModel();
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+EXPENSE_TABLE_NAME+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
            String item_name = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_XNAME));
            String amount = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_AMT));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_DESC));
            String mydate = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_CREATED_AT));
            String data_id = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_COLUMN_ID));


            dataModel.setItem_name(item_name);
            dataModel.set_id(data_id);
            dataModel.setAmt(amount);
            dataModel.setDesc(desc);
            dataModel.setmydate(mydate);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getAmt());
        }

        //

        return data;
    }



}