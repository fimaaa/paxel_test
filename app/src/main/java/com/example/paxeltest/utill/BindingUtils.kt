package com.example.paxeltest.utill

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paxeltest.R
import com.example.paxeltest.base.BaseApplication
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import java.util.*


@BindingAdapter("textHTML")
fun TextView.setHTMLText(value: String?) {
    val mContext = BaseApplication.applicationContext()
    value?.let {
        HTMLUtils.setTextViewHTML(
            mContext,
            this,
            value
        )
    }
}

@BindingAdapter(value = ["imageUrl", "isRounded", "errorImage"], requireAll = false)
fun ImageView.setGlideImage(
    imageUrl: String?,
    isRounded: Boolean = false,
    errorImage: Int? = R.color.colorPrimary
) {
    val mContext = BaseApplication.applicationContext()
    val radius = mContext.resources.getDimensionPixelSize(R.dimen.dimen_small)
    imageUrl?.let {
        if (it.substringAfterLast(".").equals("svg", true))
            GlideToVectorYou
                .init()
                .with(mContext)
                .requestBuilder
                .load(Uri.parse(it))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply {
                    if (isRounded) transform(RoundedCorners(radius))
                }
                .error(errorImage)
                .placeholder(R.drawable.bg_rounded)
                .into(this)
        else
            Glide
                .with(context)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply {
                    if (isRounded) transform(RoundedCorners(radius))
                }
                .error(errorImage)
                .placeholder(R.drawable.bg_rounded)
                .into(this)
    } ?: run {
        this.setImageDrawable(ContextCompat.getDrawable(mContext, errorImage ?: R.color.colorPrimary))
    }
}

@BindingAdapter("setProfile")
fun ImageView.setProfileImage(
    profile: String?
) {
    val mContext = BaseApplication.applicationContext()
    profile?.let {
        if (it.substringAfterLast(".").equals("svg", true))
            GlideToVectorYou
                .init()
                .with(mContext)
                .requestBuilder
                .load(Uri.parse(it))
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .error(R.drawable.shape_oval)
                .placeholder(android.R.color.black)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(this)
        else
            Glide
                .with(context)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .error(R.drawable.shape_oval)
                .placeholder(R.drawable.shape_oval)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(this)
    } ?: run {
        Glide
            .with(context)
            .load(R.color.colorPrimary)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .error(R.drawable.shape_oval)
            .placeholder(R.drawable.shape_oval)
            .into(this)
    }
}

/**
 * @param dateString (required)
 * @param inFormat (required)
 * @param outFormat (required)
 * @param locale default (optional)
 * @param isOrdinal (optional)
 * */
@BindingAdapter(
    value = ["dateString", "inFormat", "outFormat", "locale", "isOrdinal"],
    requireAll = false
)
fun TextView.bindDate(
    dateString: String?,
    inFormat: String?,
    outFormat: String,
    locale: Locale?,
    isOrdinal: Boolean?
) {
    text = dateString?.let {
        DateUtils.getDateStringWithFormat(
            it,
            inFormat ?: DateUtils.DEFAULT_TIME_FORMAT,
            outFormat,
            locale,
            isOrdinal
        )
    }
}

@BindingAdapter("showSkeleton")
fun SkeletonLayout.showSkeleton(displayed: Boolean) {
    if (displayed) showSkeleton()
    else showOriginal()
}

@BindingAdapter(
    value = ["showLoadingButton", "textButtonLoading", "progressButtonColor"],
    requireAll = false
)
fun AppCompatButton.setLoadingButton(
    showLoadingButton: Boolean,
    textButtonLoading: String,
    @ColorRes progressButtonColor: Int? = null
) {
    if (showLoadingButton) showProgress {
        progressColor = progressButtonColor ?: Color.WHITE
    } else hideProgress(textButtonLoading)
}

@BindingAdapter("underline")
fun SearchView.setUnderline(
    @ColorRes colorUnderline: Int?
) {
    colorUnderline?.let {
        val searchplate =
            findViewById<View>(androidx.appcompat.R.id.search_plate)
        searchplate.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            it, BlendModeCompat.SRC_ATOP)
    }
}

@BindingAdapter("loadText")
fun WebView.setLoadText(
    textHtml: String?
) {
    textHtml?.let {
        this.loadData(
            it,
        "text/html",
        "UTF-8"
        )
    }
}