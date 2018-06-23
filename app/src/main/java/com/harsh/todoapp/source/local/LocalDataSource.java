package com.harsh.todoapp.source.local;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.harsh.todoapp.source.Task;
import com.harsh.todoapp.source.TaskDataSource;
import com.harsh.todoapp.util.DateUtils;

import java.util.List;

public class LocalDataSource implements TaskDataSource {

    private static LocalDataSource mInstance;
    private TasksDao mDao;

    public LocalDataSource(TasksDao tasksDao) {
        this.mDao = tasksDao;
    }

    public static LocalDataSource getInstance(TasksDao tasksDao) {
        if (mInstance == null) {
            mInstance = new LocalDataSource(tasksDao);
        }
        return mInstance;
    }

    @Override
    public void getAllTasks(final LoadDataCallback callback) {
        HandlerThread backgroundThread = new HandlerThread("MyThread");
        backgroundThread.start();
        new Handler(backgroundThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mDao.getTasks();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks == null || tasks.isEmpty()) {
                            callback.onNoData("No Task Found");
                        } else {
                            callback.onDataLoaded(tasks);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getAllTasksWithCurrentDate(final LoadDataCallback callback) {
        HandlerThread backgroundThread = new HandlerThread("MyThread");
        backgroundThread.start();
        new Handler(backgroundThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mDao.getTasksBetweenDate(
                        DateUtils.getCurrentDateStart(DateUtils.serverDateFormat),
                        DateUtils.getCurrentDateEnd(DateUtils.serverDateFormat)
                );
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks == null || tasks.isEmpty()) {
                            callback.onNoData("No Task Found");
                        } else {
                            callback.onDataLoaded(tasks);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getAllOlderTasks(final LoadDataCallback callback) {
        HandlerThread backgroundThread = new HandlerThread("MyThread");
        backgroundThread.start();
        new Handler(backgroundThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mDao.getTasksNotBetweenDate(
                        DateUtils.getCurrentDateStart(DateUtils.serverDateFormat),
                        DateUtils.getCurrentDateEnd(DateUtils.serverDateFormat)
                );
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks == null || tasks.isEmpty()) {
                            callback.onNoData("No Task Found");
                        } else {
                            callback.onDataLoaded(tasks);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void saveTask(final Task task) {
        HandlerThread backgroundThread = new HandlerThread("MyThread");
        backgroundThread.start();
        new Handler(backgroundThread.getLooper()).post(new Runnable() {
            @Override
            public void run() {
                mDao.saveTask(task);
            }
        });
    }
}
