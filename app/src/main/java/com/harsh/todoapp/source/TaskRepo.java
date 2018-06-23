package com.harsh.todoapp.source;

import java.util.List;

public class TaskRepo implements TaskDataSource {

    private TaskDataSource mLocalDataSource;
    private TaskDataSource mRemoteDataSource;

    private static TaskRepo mInstance;


    private TaskRepo(TaskDataSource localDataSource, TaskDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static TaskRepo getInstance(TaskDataSource localDataSource, TaskDataSource remoteDataSource) {
        if (mInstance == null) {
            mInstance = new TaskRepo(localDataSource, remoteDataSource);
        }

        return mInstance;
    }

    @Override
    public void getAllTasks(final LoadDataCallback callback) {
        mLocalDataSource.getAllTasks(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {
                callback.onDataLoaded(tasks);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {  }
                });
            }

            @Override
            public void onNoData(String message) {
                callback.onNoData(message);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {
                        callback.onNoData(message);
                    }
                });
            }
        });
    }

    @Override
    public void getAllTasksWithCurrentDate(final LoadDataCallback callback) {
        mLocalDataSource.getAllTasksWithCurrentDate(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {
                callback.onDataLoaded(tasks);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {  }
                });
            }

            @Override
            public void onNoData(String message) {
                callback.onNoData(message);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {
                        callback.onNoData(message);
                    }
                });
            }
        });
    }

    @Override
    public void getAllOlderTasks(final LoadDataCallback callback) {
        mLocalDataSource.getAllOlderTasks(new LoadDataCallback() {
            @Override
            public void onDataLoaded(List<Task> tasks) {
                callback.onDataLoaded(tasks);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {  }
                });
            }

            @Override
            public void onNoData(String message) {
                callback.onNoData(message);
                mRemoteDataSource.getAllTasks(new LoadDataCallback() {
                    @Override
                    public void onDataLoaded(List<Task> tasks) {
                        refreshLocalData(tasks);
                    }

                    @Override
                    public void onNoData(String message) {
                        callback.onNoData(message);
                    }
                });
            }
        });
    }

    @Override
    public void saveTask(Task task) {
        mLocalDataSource.saveTask(task);
        mRemoteDataSource.saveTask(task);
    }

    private void refreshLocalData(List<Task> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task: tasks) {
                mLocalDataSource.saveTask(task);
            }
        }
    }
}
