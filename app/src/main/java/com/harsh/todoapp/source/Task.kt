package com.harsh.todoapp.source

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.v4.util.ObjectsCompat
import com.harsh.todoapp.util.TaskStatus

@Entity(tableName = "task")
class Task(id: Long, description: String, scheduledDate: Long, status: TaskStatus) {
    @PrimaryKey @ColumnInfo(name = "id") var id: Long? = id
    @ColumnInfo(name = "description") var description: String? = description
    @ColumnInfo(name = "scheduledDate") var scheduledDate: Long? = scheduledDate
    @ColumnInfo(name = "status") var status: TaskStatus? = status


    @Ignore constructor(id: Long, description: String): this(id, description, 0, TaskStatus.PENDING)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other::class.java != this::class.java) return false
        return ObjectsCompat.equals(id, (other as Task).id) and ObjectsCompat.equals(scheduledDate, other.scheduledDate)
    }
}