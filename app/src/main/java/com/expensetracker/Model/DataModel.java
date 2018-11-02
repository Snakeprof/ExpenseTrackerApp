package com.expensetracker.Model;

/**
 * Created by shishir.suvarna on 10/9/2018.
 */

public class DataModel {
    public String item_name;
    public String desc;
    public String amt;
    public String whom;
    public String mydate;
    public String xname;
    public String amount;

    public String _id;

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDesc() {
        return desc;
    }

    public String getmydate() { return mydate; }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setmydate(String mydate) { this.mydate = mydate; }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getWhom() { return whom; }

    public void setWhom(String whom) { this.whom = whom; }

    public String get_id() { return _id; }

    public void set_id(String _id) { this._id = _id; }
}
