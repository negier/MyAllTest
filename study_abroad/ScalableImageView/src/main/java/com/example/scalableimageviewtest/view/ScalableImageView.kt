package com.example.scalableimageviewtest.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ScaleGestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.scalableimageviewtest.dp
import com.example.scalableimageviewtest.getAvator
import kotlin.math.max
import kotlin.math.min

/**
 * 思路流程：
 * 先做功能大图小图；再加手势双击缩放切换显示大图和小；然后再加属性动画；然后再加手势拖动，然后再加边界判断，使其不滑出边界；然后做惯性滑动；然后做点击的部位从那放大功能；然后做双指缩放；
 */
private val IMAGE_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FACTOR = 1.5f
class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,
    ScaleGestureDetector.OnScaleGestureListener {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvator(resources,IMAGE_SIZE)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 1f
    private var bigScale = 1f
    private val gestureDetector = GestureDetectorCompat(context,this)
    private val scaleGestureDetector = ScaleGestureDetector(context,this)
    private val flingRunner = FlingRunner()
    private var big = false
    private var currentScale = 1f
        set(value) {
            field = value
            invalidate()
        }
    private val scaleAnimator = ObjectAnimator.ofFloat(this,"currentScale",smallScale,bigScale)

    // 它就是一个计算器
    private val scroller = OverScroller(context)

    //todo 不是定值，需要分情况
    //小图：内贴边，完整显示
    //大图：外贴边，还需要结合屏幕的宽高来看
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //居中
        originalOffsetX = (width - IMAGE_SIZE)/2f
        originalOffsetY = (height - IMAGE_SIZE)/2f

        //图片比较宽，胖图
        if(bitmap.width/bitmap.height.toFloat() > width / height.toFloat()){
            smallScale = width/bitmap.width.toFloat()
            bigScale = height/bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        }else{
            //图片比较高，瘦图，瘦高瘦高的
            smallScale = height/bitmap.height.toFloat()
            bigScale = width/bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale,bigScale)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    //这里倒着想问题会简单很多
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        val scaleFraction = (currentScale-smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX*scaleFraction,offsetY*scaleFraction)
        canvas.scale(currentScale,currentScale,width/2f,height/2f)
        canvas.drawBitmap(bitmap,originalOffsetX,originalOffsetY,paint)
        canvas.restore()
    }

    /**
     * todo 主要是这个，必须要返回true，才能获取这个事件序列
     */
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    /**
     * @return 返回true或false都可以，没影响。这是给系统做记录用的。
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    /**
     * 这个其实就是onMove，手指发生移动的时候这个就会被触发
     *
     * @param distanceX和distanceY，它们是旧位置减去新位置，反正如果出错了，你加个负号就好了
     */
    override fun onScroll(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if(big) {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffsets()
            invalidate()
        }
       return false
    }

    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    /**
     * 惯性滑动
     * @param velocityX和velocityY，这是横向和竖向的速率，速率是矢量，有大小有方向，它表示单位时间之内的位移。
     */
    override fun onFling(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if(big){
            //HenCoder课程OverScroller的物理模型讲解在：ScalableImageView（1）的 1:22:56开始；
            //这些坐标都是相对的，可以自己定的，可以选择一个方便自己的。
            scroller.fling(offsetX.toInt(), offsetY.toInt(),velocityX.toInt(),velocityY.toInt(),
                (-(bitmap.width*bigScale-width)/2).toInt(),
                ((bitmap.width*bigScale-width)/2).toInt(),
                (-(bitmap.height*bigScale-height)/2).toInt(),
                ((bitmap.height*bigScale-height)/2).toInt(),
                40.dp.toInt(),
                40.dp.toInt()
            )
            // 表示在下一帧调用
            ViewCompat.postOnAnimation(this,flingRunner)
        }
        return false
    }

    /**
     * 判断规则比onSingleTapUp更严格一些，它和双击不会冲突的单击。
     * 如果你支持双击，那么用onSingleTapConfirmed比较准确，如果你不支持双击，用这个就不太准确了，这时我们可以用onSingleTapUp。
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * @return 返回值没用
     */
    override fun onDoubleTap(e: MotionEvent): Boolean {
        //这一段是我加的
        big = currentScale > smallScale
        if (big){
            scaleAnimator.setFloatValues(smallScale,currentScale)
        }else{
            scaleAnimator.setFloatValues(currentScale,bigScale)
        }

        big = !big
        if (big){
            offsetX = (e.x - width/2)*(1-bigScale/smallScale)
            offsetY = (e.y - height/2)*(1-bigScale/smallScale)
            fixOffsets()
            scaleAnimator.start()
        }else{
            scaleAnimator.reverse()
        }
        return true
    }

    /**
     * 如果要处理双击用onDoubleTap，如果要处理双击后的后续操作用onDoubleTapEvent这个，一些Move、Up都会多次触发这个。
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    /*
    //可以用这个来简化代码，但我就不简化了，因为我希望保留注释意思
    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }
    //25:06
    */

    inner class FlingRunner:Runnable{
        override fun run() {
            if(scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                //这个是为了让其调用onDraw
                invalidate()
                //表示每帧更新一次
                ViewCompat.postOnAnimation(this@ScalableImageView,this)
            }
        }
    }

    /**
     * @return true,detector.scaleFactor获取到的是此刻状态和上一刻状态的比值；false,此刻状态和初始状态的比值，TODO 上一刻的状态会一直保存着
     */
    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val tempCurrentScale = currentScale * detector.scaleFactor
        if(tempCurrentScale < smallScale || tempCurrentScale > bigScale){
            return false
        }else{
            currentScale *= detector.scaleFactor
            return true
        }
        //最少是smallScale，最大是bigScale，这个语法是真的清晰 todo 没啥用了现在，留着看看
        //currentScale = currentScale.coerceAtLeast(smallScale).coerceAtMost(bigScale)
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        offsetX = (detector.focusX - width/2)*(1-bigScale/smallScale)
        offsetY = (detector.focusY - height/2)*(1-bigScale/smallScale)
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

}