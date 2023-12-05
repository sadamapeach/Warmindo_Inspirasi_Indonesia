package com.android.warmindoinspirasiindonesia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class MyViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CrudRoleFragment()
            1 -> CrudPenggunaFragment()
            else -> CrudRoleFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}