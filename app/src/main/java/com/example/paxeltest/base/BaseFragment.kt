package com.example.paxeltest.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.paxeltest.utill.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
abstract class BaseFragment(
    private val toolbarShow: Boolean = false,
    private val statusBarShow: Boolean = true
) : Fragment() {

//    @Inject
//    lateinit var prefs: PrefManagerImp

    private lateinit var job: Job
    private lateinit var mainView: MainView

    open fun onInitialization() = Unit

    abstract fun onReadyAction()

    open fun onObserveAction() = Unit

    open fun onFragmentDestroyed() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setHasOptionsMenu(true)
        job = Job()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitialization()
        mainView.toolbarVisibility(toolbarShow)
        activity.hideKeyboard(view)
        onObserveAction()
    }

    override fun onStart() {
        super.onStart()
        onReadyAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onFragmentDestroyed()
        mainView.resetToolbar()
        mainView.resetOnBackPressed()
        mainView.resetOnTouchEvent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainView = activity as MainView
    }

    @Suppress("DEPRECATION")
    private fun setStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(statusBarShow)
        } else {
            if (statusBarShow) {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//                activity?.makeStatusBarTransparent()
            } else {
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }

    fun changeOrientation(orientation: Int) {
        activity?.requestedOrientation = orientation
    }

    fun setCustomToolbar(hasHome: Boolean, title: String, menuToolbar: Int) {
        mainView.setCustomToolbar(hasHome, title, menuToolbar)
    }

    fun setOnBackPresed(listener: () -> Unit) {
        mainView.setCustomOnBackPressed(listener)
    }

    fun setDispatchEvent(listener: (MotionEvent) -> Boolean?) {
        mainView.setDispatchTouchEvent(listener)
    }

    fun changeLanguage(language: String) {
//        prefs.prefLocale = language
        mainView.changeLanguage(language)
    }
}