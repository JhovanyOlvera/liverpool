package com.android.myliverpool.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.myliverpool.models.tables.HistorySearch;

import java.util.List;

@Dao
public interface HistorySearchDAO {

    @Query("SELECT * FROM history_search")
    List<HistorySearch> loadAllHistorySearch();

    @Query("SELECT * FROM history_search WHERE history = :name")
    HistorySearch searchHistorySearch(String name);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveHistorySearch(HistorySearch historySearch);
}
