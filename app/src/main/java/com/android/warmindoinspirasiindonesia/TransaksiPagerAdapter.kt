package com.android.warmindoinspirasiindonesia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class TransaksiPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Shift1Fragment()
            1 -> Shift2Fragment()
            else -> Shift1Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}