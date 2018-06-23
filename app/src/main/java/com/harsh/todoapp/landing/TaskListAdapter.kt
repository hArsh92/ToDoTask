package com.harsh.todoapp.landing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harsh.todoapp.R
import com.harsh.todoapp.TaskApp
import com.harsh.todoapp.source.Task
import com.harsh.todoapp.util.DateUtils
import com.harsh.todoapp.util.TaskStatus
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val tasks: MutableList<Task>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)

        if (viewType == R.layout.item_header) {
            return TaskTitleView(view)
        }

        val holder = TaskItem(view)

        holder.itemView?.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != -1) {
                holder.updateStatus(tasks?.get(currentPosition))
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TaskItem) {
            holder.bindView(tasks?.get(position))
        } else if (holder is TaskTitleView) {
            holder.bindView(tasks?.get(position)?.description ?: "")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (tasks?.get(position)?.id == -1L) {
            return R.layout.item_header
        }
        return R.layout.item_task
    }
}

class TaskItem(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(task: Task?) {
        itemView?.cb_task?.text = task?.description
        itemView?.tv_date?.text = DateUtils.parseDate(
                task?.scheduledDate?.toString(),
                DateUtils.serverDateFormat,
                DateUtils.readableDateFormat
        )
    }

    fun updateStatus(task: Task?) {
        if (task != null) {
            val status = task.status ?: TaskStatus.PENDING
            task.status = if (status == TaskStatus.PENDING) {
                TaskStatus.COMPLETED
            } else {
                TaskStatus.PENDING
            }
            TaskApp.getInstance().taskRepo.saveTask(task)
        }
    }
}

class TaskTitleView(view: View) : RecyclerView.ViewHolder(view) {
    fun bindView(title: String) {
        itemView.tv_item_header.text = title
    }
}