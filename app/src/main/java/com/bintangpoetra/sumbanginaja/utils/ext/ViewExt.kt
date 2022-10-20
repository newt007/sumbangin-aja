package com.bintangpoetra.sumbanginaja.utils.ext

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import com.airbnb.lottie.LottieAnimationView
import com.bintangpoetra.sumbanginaja.R
import com.facebook.shimmer.ShimmerFrameLayout

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disable() {
    isEnabled = false
}

fun View.enabled() {
    isEnabled = true
}

infix fun View.click(click: () -> Unit) {
    setOnClickListener {
        click()
    }
}

infix fun View.popClick(click: () -> Unit) {
    setOnClickListener {
        it.popTap()
        Handler(Looper.getMainLooper()).postDelayed({
            click()
        }, 200)
    }
}

fun EditText.showError(message: String) {
    error = message
    requestFocus()
}

fun showLoading(loadingView: View, view: View) {
    loadingView.show()
    view.show()
}

fun showLoading(loadingView: View) {
    loadingView.show()
}

fun hideLoading(loadingView: View, view: View) {
    loadingView.gone()
    view.gone()
}

fun hideLoading(loadingView: View) {
    loadingView.gone()
}

fun showShimmerLoading(loadingView: ShimmerFrameLayout, view: View) {
    loadingView.show()
    loadingView.showShimmer(true)
    loadingView.startShimmer()
    view.show()
}

fun hideShimmerLoading(loadingView: ShimmerFrameLayout, view: View) {
    loadingView.gone()
    loadingView.showShimmer(false)
    loadingView.stopShimmer()
    view.gone()
}

fun View.popTap(){
    this.visibility = View.VISIBLE
    this.alpha = 1.0f

    this.pivotX = (this.width / 2).toFloat()
    this.pivotY = (this.height / 2).toFloat()

    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", 0.7f)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", 0.7f)

    scaleDownX.duration = 100
    scaleDownY.duration = 100

    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f)

    scaleUpX.duration = 100
    scaleUpY.duration = 100

    val scaleDown = AnimatorSet()
    scaleDown.play(scaleDownX).with(scaleDownY)
    scaleDown.start()

    val scaleUp = AnimatorSet()
    scaleUp.play(scaleUpX).with(scaleUpY).after(scaleDown)
    scaleUp.start()
}

fun LottieAnimationView.initLottie() {
    setAnimation(R.raw.loading_lottie)
}