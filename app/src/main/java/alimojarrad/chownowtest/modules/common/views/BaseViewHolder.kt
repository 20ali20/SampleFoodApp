package com.sidecarhealth.modules.common.views


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sidecarhealth.modules.common.AdapterClickListener

abstract class BaseViewHolder<T>(itemView: View, listeners : AdapterClickListener?) : RecyclerView.ViewHolder(itemView), BaseViewHolderHelper{


//    abstract fun getViewHolder(itemView: View) : RecyclerView.ViewHolder
    abstract fun bind(item : T, position: Int)

    fun onDestroy(){
        itemView.setOnClickListener(null)
    }

}

interface BaseViewHolderHelper {
    fun getView(parent : ViewGroup) : View
}
