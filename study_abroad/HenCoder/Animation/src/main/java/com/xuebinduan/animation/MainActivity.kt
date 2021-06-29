package com.xuebinduan.animation

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.minus
import com.xuebinduan.animation.view.CameraView
import com.xuebinduan.animation.view.PointFView
import com.xuebinduan.animation.view.ProvinceEvaluator
import com.xuebinduan.animation.view.ProvinceView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val view = findViewById<ImageView>(R.id.image)
        view.animate().translationX(300.dp2px()).setStartDelay(1000)*/

        /*val circleView = findViewById<CircleView>(R.id.circle_view)
        val animator = ObjectAnimator.ofFloat(circleView, "radius", 150.dp2px())
        animator.startDelay = 1000
        animator.start()*/

        val view = findViewById<ProvinceView>(R.id.view)

        /*val bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
        bottomFlipAnimator.startDelay = 1000
        bottomFlipAnimator.duration = 1000

        val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
        flipRotationAnimator.startDelay = 200
        flipRotationAnimator.duration = 1000

        val topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -60f)
        topFlipAnimator.startDelay = 200
        topFlipAnimator.duration = 1000

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator)
        animatorSet.start()*/

        /*val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
        val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -60f)
        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlipHolder, flipRotationHolder, topFlipHolder)
        holderAnimator.startDelay = 1000
        holderAnimator.duration = 2000
        holderAnimator.start()*/

        /*val length = 200.dp2px()
        val keyframe1 = Keyframe.ofFloat(0f, 0f)
        val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length)
        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
        val keyframe4 = Keyframe.ofFloat(1f, 1f*length)
        val keyframeHolder = PropertyValuesHolder.ofKeyframe("translationX",keyframe1,keyframe2,keyframe3,keyframe4)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view,keyframeHolder)
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        /*val animator = ObjectAnimator.ofObject(view,"point",PointFEvaluator(),PointF(100.dp2px(),200.dp2px()))
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        val animator = ObjectAnimator.ofObject(view,"province",ProvinceEvaluator(),"新疆维吾尔自治区");
        animator.startDelay = 1000
        animator.duration = 10000
        animator.start()

    }

    class PointFEvaluator : TypeEvaluator<PointF>{
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            var pointF = endValue - startValue //这是android团队在KTX中提供的扩展，可以直接对PointF加减
            val currentX = startValue.x + pointF.x * fraction
            val currentY = startValue.y + pointF.y * fraction
            return PointF(currentX,currentY)
        }
    }
}