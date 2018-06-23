package com.harsh.todoapp.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.harsh.todoapp.source.Task;

import java.util.List;

@Dao
public interface TasksDao {

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM task order by status DESC")
    List<Task> getTasks();

    /**
     * Select all tasks from the tasks table between given dates.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM task WHERE scheduledDate BETWEEN :startDate AND :endDate order by status DESC")
    List<Task> getTasksBetweenDate(String startDate, String endDate);


    /**
     * Select all tasks from the tasks table Not between given dates.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM task WHERE scheduledDate < :startDate OR scheduledDate > :endDate order by status DESC")
    List<Task> getTasksNotBetweenDate(String startDate, String endDate);

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTask(Task task);
}
