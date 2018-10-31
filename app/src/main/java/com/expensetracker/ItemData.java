package com.expensetracker;

/**
 * Created by shishir.suvarna on 10/16/2018.
 */

public class ItemData {
    String text;
    Integer imageId;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}
