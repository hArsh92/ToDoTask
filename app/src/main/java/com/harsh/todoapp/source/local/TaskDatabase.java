package com.harsh.todoapp.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.harsh.todoapp.source.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters(value = {TaskTypeConverters.class})
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase mInstance;

    public abstract TasksDao tasksDao();

    private static final Object sLock = new Object();

    public static TaskDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TaskDatabase.class, "tasks.db")
                        .build();
            }
            return mInstance;
        }
    }
}
