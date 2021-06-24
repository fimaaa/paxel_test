package com.example.paxeltest.utill

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.paxeltest.R
import com.example.paxeltest.databinding.CustomTwobuttonDialogBinding
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick

object DialogUtils {

    fun showDialog(
        context: Context,
        title: String,
        desc: String,
        txtButton1: String,
        txtButton2: String,
        txtLink: String,
        iconVisibility: Boolean,
        listenerBtn1: () -> Unit,
        listenerBtn2: (() -> Unit)? = null,
        listenerLink: (() -> Unit)? = null
    ) {
        val view = context.layoutInflater.inflate(R.layout.custom_twobutton_dialog, null as ViewGroup?)
        val binding = CustomTwobuttonDialogBinding.bind(view)
        with(binding) {
            titleDialog.text = title
            descDialog.text = desc
            btn1Dialog.text = txtButton1
            imgDialog.isVisible = iconVisibility

            btn2Dialog.text = txtButton2
            if (txtLink.isEmpty()) {
                linkDialog.gone()
            } else {
                linkDialog.visible()
                linkDialog.text = txtLink
            }

            linkDialog.onClick {
                listenerLink?.invoke()
            }
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded)
            btn2Dialog.onClick {
                listenerBtn2?.invoke()
                dialog.dismiss()
            }
            btn1Dialog.setOnClickListener {
                listenerBtn1.invoke()
                dialog.dismiss()
            }
        }
    }
}