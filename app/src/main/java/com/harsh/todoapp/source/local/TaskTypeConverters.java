package com.harsh.todoapp.source.local;

import android.arch.persistence.room.TypeConverter;

import com.harsh.todoapp.util.TaskStatus;

public class TaskTypeConverters {
    @TypeConverter
    public TaskStatus fromTaskStatus(String status) {
        return status == null ? TaskStatus.PENDING : TaskStatus.valueOf(status);
    }

    @TypeConverter
    public String statusToString(TaskStatus status) {
        if (status == null) {
            return TaskStatus.PENDING.name();
        } else {
            return status.name();
        }
    }
}
