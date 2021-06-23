package com.xuebinduan.viewpager2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * adapter:
         * FragmentStateAdapter
         * RecyclerView.Adapter
         */

        startActivity(Intent(this,ViewPager2BottomNavigationActivity::class.java))
//        startActivity(Intent(this,ViewPager2TabLayoutActivity::class.java))
//        startActivity(Intent(this,ViewPager2CustomTabActivity::class.java))

    }

}