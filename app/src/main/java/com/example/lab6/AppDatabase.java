package com.example.lab6;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Person.class}, version = 1)
@TypeConverters({UUIDConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();

    private static AppDatabase instance = null;
    public static AppDatabase createInstance(Context context) {
        instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "lab6").allowMainThreadQueries().build();
        return instance;
    }
    public static AppDatabase getInstance(){
        if(instance == null) throw new IllegalStateException("Instance must be created");
        return instance;
    }
}
