package com.example.paxeltest.utill

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.navigation.findNavController
import com.example.paxeltest.R
import com.google.android.material.snackbar.Snackbar
import com.example.paxeltest.base.BaseApplication
import org.jetbrains.anko.sdk27.coroutines.onScrollChange
import java.util.*

const val Toast_Error = -1
const val Toast_Default = 0

fun Context.showSnackBar(
    view: View,
    message: CharSequence,
    typeToast: Int = Toast_Error,
    isLong: Boolean = true
) {
    val snackBar = Snackbar.make(
        view,
        message,
        if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    )
    val snackBarView = snackBar.view
    snackBarView.background = ContextCompat.getDrawable(
        this,
        if (typeToast == Toast_Default) R.drawable.bg_toast else R.drawable.bg_toast_error
    )
    val textView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.WHITE)
    textView.textSize = 13f

    val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.setMargins(16, 50, 16, 0)
    snackBarView.layoutParams = params
    snackBar.view.translationZ = 1000F
    snackBar.show()
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun Activity.getScreenDevice(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}

fun Activity.getWidthAds(): Int {
    val display = windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)

    val density = outMetrics.density

    var adWidthPixels = getScreenDevice().widthPixels.toFloat()
    if (adWidthPixels == 0f) {
        adWidthPixels = outMetrics.widthPixels.toFloat()
    }

    return (adWidthPixels / density).toInt()
}

fun Activity.changeStatusBarColor(color: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(
        BaseApplication
            .applicationContext(), color
    )
}

fun Context.showDateDialog(
    selectedDate: (date: Calendar) -> Unit
) {
    val mCalendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        this, R.style.DialogTheme,
        { _, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate(mCalendar)
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    datePickerDialog.show()
}

fun Activity.scrollBehaviour(scroll: NestedScrollView) {
    scroll.onScrollChange { _, _, scrollY, _, _ ->
        if (scrollY != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(
                BaseApplication
                    .applicationContext(), android.R.color.white
            )
        } else if (scrollY == 0) makeStatusBarTransparent()
    }
}

fun Activity.makeDefaultStatusBar() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(
        BaseApplication
            .applicationContext(), android.R.color.white
    )
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
}

fun Context.openLinkApps(url: String) {
    val packageName = url.substringAfter("=")
    var intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.let {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } ?: let {
        intent = Intent(Intent.ACTION_VIEW)
        intent?.data = Uri.parse(url)
        startActivity(intent)
    }
}

fun Context.convertTODP(size: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (size * scale + 0.5f).toInt()
}

fun Context.openDialer(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}

fun Context.versionAppName(): String {
    var versionAppName = ""
    versionAppName = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        ""
    }
    return versionAppName
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    val pm = packageManager
    return try {
        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: NameNotFoundException) {
        false
    }
}

fun Activity.login() {
    val controller = this.findNavController(R.id.nav_host_fragment_main)
    controller.navigate(R.id.exampleFragment)
}