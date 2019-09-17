package com.android.myliverpool.models;

import com.google.gson.annotations.Expose;

public class SearchResult {

    @Expose
    private Results plpResults;

    public Results getPlpResults() {
        return plpResults;
    }

    public void setPlpResults(Results plpResults) {
        this.plpResults = plpResults;
    }
}
