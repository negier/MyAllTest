package com.xuebinduan.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xuebinduan.viewpager2.fragment.Fragment1
import com.xuebinduan.viewpager2.fragment.Fragment2
import com.xuebinduan.viewpager2.fragment.Fragment3

class ViewPager2TabLayoutActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private val fragments = listOf(Fragment1(), Fragment2(), Fragment3())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_tab_layout)

        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return fragments.elementAt(position)
            }
        }
        val tabLayoutMediator = TabLayoutMediator(
            tabLayout,
            viewPager2,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.setText("Fragment $position")
                }
            })
        tabLayoutMediator.attach();
    }
}