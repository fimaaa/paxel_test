package com.example.paxeltest.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.paxeltest.R
import com.example.paxeltest.databinding.ActivityMainBinding
import com.example.paxeltest.di.ExternalData
import com.example.paxeltest.utill.changeStatusBarColor
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainView {

    private var menuToolbar = 0
    private var registeredHandler: (() -> Unit)? = null
    private var onDispatchHandler: ((ev: MotionEvent) -> Boolean?)? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExternalData
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarParent)
        resetToolbar()
        toolbarVisibility(false)
        MobileAds.initialize(this) { }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
//                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )
        changeStatusBarColor(R.color.colorPrimary)
    }

    override fun toolbarVisibility(visible: Boolean) {
        if (visible) supportActionBar?.show() else supportActionBar?.hide()
    }

    override fun resetToolbar() {
        menuToolbar = R.menu.toolbar_default
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun setCustomToolbar(hasHome: Boolean, title: String, menuToolbar: Int) {
        this.menuToolbar = menuToolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(hasHome)
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuToolbar != 0) {
            menuInflater.inflate(menuToolbar, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setCustomOnBackPressed(listner: () -> Unit) {
        registeredHandler = listner
    }

    override fun resetOnBackPressed() {
        registeredHandler = null
    }

    override fun setDispatchTouchEvent(listener: (MotionEvent) -> Boolean?) {
        this.onDispatchHandler = listener
    }

    override fun resetOnTouchEvent() {
        onDispatchHandler = null
    }

    override fun onBackPressed() {
        if (registeredHandler != null) registeredHandler?.invoke() else super.onBackPressed()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val touchCustom = onDispatchHandler?.invoke(ev)
        return touchCustom ?: super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun changeLanguage(language: String) {
//        updateLocale(Locale(language))
    }
}