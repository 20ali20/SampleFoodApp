package com.sidecarhealth.modules.common

import alimojarrad.chownowtest.R
import android.app.Dialog
import android.content.Context
import kotlinx.android.synthetic.main.popup_message.*



object PopupMessage {
    fun createPopupMessage(context: Context, message: String
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_message)
        dialog.popup_message.text = message
        dialog.show()

    }



}


