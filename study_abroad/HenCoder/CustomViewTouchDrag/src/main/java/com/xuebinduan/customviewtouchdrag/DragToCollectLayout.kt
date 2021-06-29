package com.xuebinduan.customviewtouchdrag

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat

class DragToCollectLayout(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    private lateinit var avatarView: ImageView
    private lateinit var logoView: ImageView
    private lateinit var collectorLayout: LinearLayout

    private var dragStarter = OnLongClickListener { v ->
        val imageData = ClipData.newPlainText("name", v.contentDescription)
        ViewCompat.startDragAndDrop(v, imageData, DragShadowBuilder(v), null, 0)
    }
    private var dragListener: OnDragListener = CollectListener()

    override fun onFinishInflate() {
        super.onFinishInflate()

        avatarView = findViewById(R.id.avatar_view)
        logoView = findViewById(R.id.logo_view)
        collectorLayout = findViewById(R.id.collector_layout)

        avatarView.setOnLongClickListener(dragStarter)
        logoView.setOnLongClickListener(dragStarter)
        collectorLayout.setOnDragListener(dragListener)
    }

    inner class CollectListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP ->
                    if (v is LinearLayout) {
                        val layout = v as LinearLayout
                        val textView = TextView(context)
                        textView.textSize = 16f
                        textView.text = event.clipData.getItemAt(0).text
                        layout.addView(textView)
                    }
            }
            return true
        }
    }

}