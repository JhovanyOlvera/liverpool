package com.android.myliverpool.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Results {

    @Expose
    private ArrayList<Product> records;

    public ArrayList<Product> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Product> records) {
        this.records = records;
    }
}
