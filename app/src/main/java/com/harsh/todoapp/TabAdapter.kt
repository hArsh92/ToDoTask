package com.harsh.todoapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabAdapter(
        fragmentManager: FragmentManager,
        private val titleFragmentMap: Map<String, Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return ArrayList(titleFragmentMap.values)[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ArrayList(titleFragmentMap.keys)[position]
    }

    override fun getCount(): Int {
        return titleFragmentMap.size
    }
}