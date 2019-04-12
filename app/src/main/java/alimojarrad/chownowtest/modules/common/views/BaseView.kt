package com.sidecarhealth.modules.common.views

import android.view.View
import android.view.ViewGroup
import com.sidecarhealth.modules.common.AdapterClickListener

abstract class BaseView<T>(listeners: AdapterClickListener?) {


    //    abstract fun getViewHolder(itemView: View) : RecyclerView.ViewHolder
    abstract fun bind(itemView : View, item: T, position: Int)

    abstract fun getView(parent: ViewGroup): View

    fun onDestroy(itemView : View){
        itemView.setOnClickListener(null)
    }


}
