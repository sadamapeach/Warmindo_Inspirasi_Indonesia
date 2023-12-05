package com.android.warmindoinspirasiindonesia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class CrudFragment : Fragment() {
    var tabLayout: TabLayout? = null
    var viewPager2: ViewPager2? = null
    var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crud, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager2 = view.findViewById(R.id.view_pager)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewPagerAdapter = MyViewPagerAdapter(requireActivity())
        viewPager2?.adapter = myViewPagerAdapter

        tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2?.setCurrentItem(tab.position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout?.getTabAt(position)?.select()
            }
        })
    }
}
