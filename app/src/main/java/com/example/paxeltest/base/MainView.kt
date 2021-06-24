package com.example.paxeltest.base

import android.view.MotionEvent

interface MainView {
    fun toolbarVisibility(visible: Boolean)
    fun resetToolbar()
    fun setCustomToolbar(hasHome: Boolean, title: String, menuToolbar: Int)
    fun setCustomOnBackPressed(listner: () -> Unit)
    fun resetOnBackPressed()
    fun setDispatchTouchEvent(listener: ((MotionEvent) -> Boolean?))
    fun resetOnTouchEvent()
    fun changeLanguage(language: String)
}