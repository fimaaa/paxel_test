package com.example.paxeltest.utill

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onScrollChange

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.safeOnClikListener(
    defaultInterval: Int = 1000,
    listener: () -> Unit
) {
    var lastTimeClicked: Long = 0

    this.onClick {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return@onClick
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        listener()
    }
}

fun Context?.showKeyboard() {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun Context?.hideKeyboard(view: View?) {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        view?.windowToken,
        0
    )
}

fun Context.setBackground(drawable: Int): Drawable? = ContextCompat.getDrawable(this, drawable)

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun NestedScrollView.onScroll(
    listener: (v: View?, scrollY: Int, alpha: Float) -> Unit
) {
    onScrollChange { v, _, scrollY, _, _ ->
        listener.invoke(v, scrollY, scrollY.toFloat() / 500)
    }
}

fun View.animateShowFromBottom(isShowing: Boolean, duration: Long = 400L) {
    if (isShowing) {
        if (isVisible) return
        visible()
        val animation = TranslateAnimation(0f, 0f, height.toFloat(), 0f)
        animation.duration = duration
        startAnimation(animation)
    } else {
        if (isGone) return
        val animation = TranslateAnimation(0f, 0f, 0f, height.toFloat())
        animation.duration = duration
        startAnimation(animation)
        gone()
    }
}

/**
 * Used to scroll to the given view.
 *
 * @param scrollViewParent Parent ScrollView
 * @param view View to which we need to scroll.
 */
fun NestedScrollView.scrollToView(view: View) {
    // Get deepChild Offset
    val childOffset = Point()
    getDeepChildOffset(this, view.parent, view, childOffset)
    // Scroll to child.
    this.smoothScrollTo(0, childOffset.y)
}

/**
 * Used to get deep child offset.
 *
 *
 * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
 * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
 *
 * @param mainParent Main Top parent.
 * @param parent Parent.
 * @param child Child.
 * @param accumulatedOffset Accumulated Offset.
 */
private fun getDeepChildOffset(
    mainParent: ViewGroup,
    parent: ViewParent,
    child: View,
    accumulatedOffset: Point
) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}

fun ViewGroup.ChangeSize(width: Int? = null, heigt: Int? = null) {
    val param = layoutParams
    width?.let { param.width = it }
    heigt?.let { param.height = it }
    layoutParams = param
}