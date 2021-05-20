package com.xuebinduan.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xuebinduan.viewpager2.fragment.Fragment1
import com.xuebinduan.viewpager2.fragment.Fragment2
import com.xuebinduan.viewpager2.fragment.Fragment3

class ViewPager2CustomTabActivity : AppCompatActivity() {

    private val fragments = listOf(Fragment1(), Fragment2(), Fragment3())
    private val tabViewHashMap = mutableMapOf<TabLayout.Tab, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_custom_tab)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)

        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return fragments.elementAt(position)
            }
        }
        TabLayoutMediator(tabLayout, viewPager2,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    val customTab = LayoutInflater.from(this@ViewPager2CustomTabActivity).inflate(R.layout.custom_tab,null)
                    tabViewHashMap[tab] = customTab
                    tab.customView = customTab
                    customTab.findViewById<TextView>(R.id.tab_text).setText("Fragment $position")
                    if (position == 0){
                        customTab.findViewById<ImageView>(R.id.tab_indicator).visibility = View.VISIBLE
                    }
                }
            }).attach();
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView = tabViewHashMap[tab]
                val tabIndicator = tabView?.findViewById<ImageView>(R.id.tab_indicator)
                tabIndicator?.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView = tabViewHashMap[tab]
                val tabIndicator = tabView?.findViewById<ImageView>(R.id.tab_indicator)
                tabIndicator?.visibility = View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }
}