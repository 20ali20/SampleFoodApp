package com.sidecarhealth.modules.common

import android.view.View
import io.reactivex.disposables.CompositeDisposable

interface ClickListeners{
    fun onClick(v: View, position: Int)
    fun onSecondaryClick(v : View, position: Int){}
    fun onTertiaryClick(v : View, position: Int){}
}

interface  AdapterClickListener : ClickListeners {
    fun getDisposable() : CompositeDisposable?{
        return null
    }
}

interface EmptyViewClickListener : ClickListeners

interface HeaderViewClickListener

interface FooterViewClickListener