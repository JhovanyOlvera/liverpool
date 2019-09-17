package com.android.myliverpool.models.tables;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_search", indices = {@Index(value = {"id"}, unique = true)})
public class HistorySearch {

    @PrimaryKey
    private Integer id;
    private String history;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
