package com.harsh.todoapp;

import android.app.Application;

import com.harsh.todoapp.source.TaskDataSource;
import com.harsh.todoapp.source.TaskRepo;
import com.harsh.todoapp.source.local.LocalDataSource;
import com.harsh.todoapp.source.local.TaskDatabase;
import com.harsh.todoapp.source.remote.RemoteDataSource;
import com.harsh.todoapp.source.remote.TaskRemoteService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskApp extends Application {

    private final String baseUrl = "http://www.mocky.io/";
    private TaskRemoteService remoteService;

    private static TaskApp mInstance;
    private TaskRepo taskRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        remoteService = retrofit.create(TaskRemoteService.class);
    }

    public static TaskApp getInstance() {
        return mInstance;
    }

    public TaskRemoteService getRemoteService() {
        return remoteService;
    }

    public TaskRepo getTaskRepo() {
        if (taskRepo == null) {
            taskRepo = TaskRepo.getInstance(
                    LocalDataSource.getInstance(TaskDatabase.getInstance(this).tasksDao()),
                    RemoteDataSource.getInstance(getRemoteService())
            );
        }
        return taskRepo;
    }
}
