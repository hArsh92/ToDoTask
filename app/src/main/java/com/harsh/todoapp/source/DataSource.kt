package com.harsh.todoapp.source

interface LocalDataSourceOperation {
    fun getAllTasksWithCurrentDate()
    fun getAllOlderTasks()

    fun saveTask(task: Task)
}


interface RemoteDataSoureOperatio{
    fun getAllTasks()
}
