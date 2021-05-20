package com.xuebinduan.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xuebinduan.viewpager2.fragment.Fragment1
import com.xuebinduan.viewpager2.fragment.Fragment2
import com.xuebinduan.viewpager2.fragment.Fragment3

class ViewPager2BottomNavigationActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager2PageChangeCallback: ViewPager2.OnPageChangeCallback
    private val menuItemToFragments = mutableMapOf(
        R.id.action_1 to Fragment1(),
        R.id.action_2 to Fragment2(),
        R.id.action_3 to Fragment3()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2_bottom_navigation)

        //ViewPager2和BottomNavigationView是有联动的
        viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = menuItemToFragments.values.size
            override fun createFragment(position: Int): Fragment {
                return menuItemToFragments.values.elementAt(position)
            }
        }
        viewPager2PageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.e("TAG", "onPageSelected:${position}")
                bottomNavigationView.selectedItemId = menuItemToFragments.keys.elementAt(position)
            }
        }
        viewPager2.registerOnPageChangeCallback(viewPager2PageChangeCallback)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            Log.e("TAG", "onNavigationItemSelectedListener: ${it.itemId}")
            var currentItem = 0
            menuItemToFragments.onEachIndexed { index, entry ->
                if (entry.key == it.itemId) {
                    currentItem = index
                }
            }
            viewPager2.currentItem = currentItem
            //todo 这里需要返回true，不然UI没效果
            true
        }
    }

    override fun onDestroy() {
        viewPager2.unregisterOnPageChangeCallback(viewPager2PageChangeCallback)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            super.onBackPressed()
        }else{
            viewPager2.currentItem = 0
        }
    }
}