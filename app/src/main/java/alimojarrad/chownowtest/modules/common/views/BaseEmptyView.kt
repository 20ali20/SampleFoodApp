package com.sidecarhealth.modules.common.views


import alimojarrad.chownowtest.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface BaseEmptyView<T> {
    fun bind(item: T, position: Int)
    fun getView(parent: ViewGroup): View
}

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class EmptyView<T> : BaseEmptyView<T>{
    override fun bind(item: T, position: Int) {
        //
    }

    override fun getView(parent: ViewGroup): View {
    return LayoutInflater.from(parent.context).inflate(R.layout.item_listview_empty, parent, false)
    }
}

