package com.harsh.todoapp.landing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.harsh.todoapp.R
import com.harsh.todoapp.TaskApp
import com.harsh.todoapp.source.Task
import com.harsh.todoapp.source.TaskDataSource
import com.harsh.todoapp.util.TaskStatus
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskListFragment : Fragment() {

    var adapter: TaskListAdapter? = null
    val mTasks: MutableList<Task> = mutableListOf()
    var screenType = SCREEN_TYPE_TODAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenType = arguments?.getInt(TaskListFragment.screenType) ?: SCREEN_TYPE_TODAY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        view.list.layoutManager = LinearLayoutManager(inflater.context)
        adapter = TaskListAdapter(mTasks)
        view.list.adapter = adapter
        view.list.addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateData()
    }

    private fun updateData() {
        if (screenType == SCREEN_TYPE_TODAY) {
            TaskApp.getInstance().taskRepo.getAllTasksWithCurrentDate(object : TaskDataSource.LoadDataCallback {
                override fun onDataLoaded(tasks: MutableList<Task>?) {
                    handleData(tasks)
                }

                override fun onNoData(message: String?) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                }
            })
        } else {
            TaskApp.getInstance().taskRepo.getAllOlderTasks(object : TaskDataSource.LoadDataCallback {
                override fun onDataLoaded(tasks: MutableList<Task>?) {
                    handleData(tasks)
                }

                override fun onNoData(message: String?) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun handleData(tasks: MutableList<Task>?) {
        if (tasks != null) {
            mTasks.clear()
            mTasks.addAll(tasks)
            val headerPending = Task(-1, "Pending")
            val headerCompleted = Task(-1, "Completed")

            var headerIndex = -1
            for (task: Task in mTasks) {
                if (task.status == TaskStatus.COMPLETED) {
                    headerIndex = mTasks.indexOf(task)
                    break
                }
            }
            if (headerIndex != -1) {
                mTasks.add(headerIndex, headerCompleted)
            }
            mTasks.add(0, headerPending)
            adapter?.notifyDataSetChanged()
        }
    }

    companion object {
        private const val screenType = "screen_type"
        const val SCREEN_TYPE_TODAY = 0
        const val SCREEN_TYPE_TOMORROW = 1


        fun newInstance(type: Int): TaskListFragment {
            val args = Bundle()
            args.putInt(screenType, type)
            val fragment = TaskListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}