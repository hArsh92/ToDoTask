package com.harsh.todoapp

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.harsh.todoapp.landing.TaskListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleFragmentMap = mutableMapOf<String, Fragment>()
        titleFragmentMap["Today"] = TaskListFragment.newInstance(TaskListFragment.SCREEN_TYPE_TODAY)
        titleFragmentMap["Later"] = TaskListFragment.newInstance(TaskListFragment.SCREEN_TYPE_TOMORROW)

        tab_layout.setupWithViewPager(viewpager)

        val tabAdapter = TabAdapter(supportFragmentManager, titleFragmentMap)
        viewpager.adapter = tabAdapter

        tab_layout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))
    }
}
