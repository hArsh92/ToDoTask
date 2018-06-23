package com.harsh.todoapp.source.remote;

import android.support.annotation.NonNull;

import com.harsh.todoapp.source.Task;
import com.harsh.todoapp.source.TaskDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements TaskDataSource {

    private static RemoteDataSource mInstance;

    private TaskRemoteService mRemoteService;

    private RemoteDataSource(TaskRemoteService remoteService) {
        this.mRemoteService = remoteService;
    }

    public static RemoteDataSource getInstance(TaskRemoteService remoteService) {
        if (mInstance == null) {
            mInstance = new RemoteDataSource(remoteService);
        }
        return mInstance;
    }

    @Override
    public void getAllTasks(final LoadDataCallback callback) {
        mRemoteService.getAllTask().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    callback.onDataLoaded(response.body());
                } else {
                    callback.onNoData("No Task Available");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable throwable) {
                callback.onNoData("No Task Available");
            }
        });
    }

    @Override
    public void getAllTasksWithCurrentDate(LoadDataCallback callback) {

    }

    @Override
    public void getAllOlderTasks(LoadDataCallback callback) {

    }

    @Override
    public void saveTask(Task task) {

    }
}
