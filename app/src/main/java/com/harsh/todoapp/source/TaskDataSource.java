package com.harsh.todoapp.source;

import java.util.List;

public interface TaskDataSource {

    interface LoadDataCallback {
        void onDataLoaded(List<Task> tasks);

        void onNoData(String message);
    }

    void getAllTasks(LoadDataCallback callback);

    void getAllTasksWithCurrentDate(LoadDataCallback callback);

    void getAllOlderTasks(LoadDataCallback callback);

    void saveTask(Task task);
}
