package com.example.sampleproject.animationutils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import com.example.sampleproject.constants.Constants.Companion.DURATION

object AnimationUtils {

    fun startFlipAnimation(view: View): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        val objectAnimatorFlip = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        objectAnimator.apply {
            duration = DURATION
            interpolator = DecelerateInterpolator()
            doOnEnd {
                objectAnimatorFlip.start()
            }
            start()
        }
        objectAnimatorFlip.apply {
            duration = DURATION
            interpolator = AccelerateDecelerateInterpolator()
        }
        return objectAnimatorFlip
    }
}