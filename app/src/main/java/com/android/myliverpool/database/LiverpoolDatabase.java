package com.android.myliverpool.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.myliverpool.models.tables.HistorySearch;

@Database(entities = {HistorySearch.class}, version = 1)
public abstract class LiverpoolDatabase extends RoomDatabase {

    private static final String LOG_TAG = LiverpoolDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "movies";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static LiverpoolDatabase sInstance;

    public static LiverpoolDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LiverpoolDatabase.class, LiverpoolDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract HistorySearchDAO historySearchDAO();

}
