package com.android.myliverpool.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Results {

    @Expose
    private ArrayList<Record> records;

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
