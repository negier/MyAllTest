package com.xuebinduan.materialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText

private val TEXT_SIZE = 12.dp2px()
private val TEXT_MARGIN = 8.dp2px()
private val HORIZONTAL_OFFSET = 5.dp2px()
private val VERTICAL_OFFSET = 23.dp2px()
private val EXTRA_VERTICAL_OFFSET = 16.dp2px()

class MaterialEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var floatingLabelShown = false
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val animator by lazy{
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f,1f)
    }

    var useFloatingLabel = false
    set(value) {
        if (field != value){
            field = value
            if (field){
                setPadding(
                    paddingLeft,
                    (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                    paddingRight,
                    paddingBottom
                )
            }else{
                setPadding(
                    paddingLeft,
                    (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(),
                    paddingRight,
                    paddingBottom
                )
            }
        }
    }

    init {
        paint.textSize = TEXT_SIZE

        for(index in 0 until attrs.attributeCount){
            Log.e("TAG","Attrs,key:${attrs.getAttributeName(index)},value:${attrs.getAttributeValue(index)}")
        }

        //attrs包含所有的属性。然后这里的意思是过滤一下，只有MaterialEditText下的属性才保留
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.MaterialEditText)
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel,true)
        typedArray.recycle()

        if (useFloatingLabel) {
            setPadding(
                paddingLeft,
                (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
                paddingRight,
                paddingBottom
            )
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (floatingLabelShown && text.isNullOrEmpty()) {
            floatingLabelShown = false
            animator.reverse()
        } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
            floatingLabelShown = true
            animator.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingLabelFraction * 0xff).toInt()
        val currentVerticalValue = VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVerticalValue, paint)
    }

}