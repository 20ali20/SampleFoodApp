package com.sidecarhealth.modules.common.adapters.nonPaginated

import alimojarrad.chownowtest.MyApplication
import alimojarrad.chownowtest.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.sidecarhealth.modules.common.AdapterClickListener
import com.sidecarhealth.modules.common.Utils
import com.sidecarhealth.modules.common.views.BaseViewHolder
import com.sidecarhealth.modules.common.views.EmptyViewHolder
import com.sidecarhealth.modules.common.views.HeaderViewHolder
import timber.log.Timber


/**
 * Created by amojarrad on 4/3/18.
 */


class NonPaginatedAdapterRecylerview<T>(
    val context: Context,
    val layout: Int,
    val viewHolderClass: Class<out BaseViewHolder<T>>,
    val itemListener: AdapterClickListener?,
    val addPadingToLeft: Boolean? = false,
    val elevation : Float?=null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     var list: List<T> = ArrayList()
    private var isAList = false
    private var emptyLayout: Int? = null
    var headerView: View? = null
    private var headerViewType = 999
    var footerView: View? = null
    private var footerViewType = 888
    private var lastPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        return when (viewType) {
            emptyLayout ?: R.layout.item_listview_empty -> {
                if (view == null) {
                    view = LayoutInflater.from(parent.context).inflate(emptyLayout
                            ?: R.layout.item_listview_empty, parent, false)
                }
                EmptyViewHolder(view!!)
            }
            layout -> {
                view = LayoutInflater.from(parent.context).inflate(layout, parent, false)


                if (addPadingToLeft!!) {
                    view.setPadding(Utils.conversion.getDpFromInt(context, 20), view.paddingTop, view.paddingRight, view.paddingBottom)
                    view.background = context.resources.getDrawable(R.drawable.s_round_5dp_white, null)
                    view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    if (view.measuredHeight > 0) {
                        view.layoutParams.height = view.measuredHeight
                    }
                    view.elevation = 20f
                }

                elevation?.let {
                    view?.elevation = it
                }

                (viewHolderClass.constructors.first().newInstance(view, itemListener) as? BaseViewHolder<T>)!!
            }
            headerViewType -> {
                HeaderViewHolder(headerView!!)
            }
            footerViewType -> {
                HeaderViewHolder(footerView!!)
            }
            else -> {
                if (view == null) {
                    view = LayoutInflater.from(parent.context).inflate(R.layout.item_listview_empty, parent, false)
                }
                EmptyViewHolder(view!!)
            }
        }


    }


    override fun getItemCount(): Int {
        var value = list.size
        headerView?.let { value++ }
        footerView?.let { value++ }
        if (list.isEmpty() && isAList) value++
        return value
    }

    fun getItem(position: Int): T? {
        var pos = position
        headerView?.let { pos-- }
        footerView?.let { pos-- }
        return if (list.size > pos) list[pos] else null
    }

    fun updateList(list: List<T>) {
        if (!isAList) {
            isAList = true
        }
        this.list = list
        notifyDataSetChanged()
        lastPosition = -1
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            headerView != null && position == 0 -> headerViewType
            footerView != null && position == itemCount + 1 -> footerViewType
            list.isNotEmpty() -> layout
            else -> R.layout.item_listview_empty
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? BaseViewHolder<T>)?.onDestroy()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            when (getItemViewType(position)) {

                layout -> {
                    getItem(position)?.let {
                        (holder as? BaseViewHolder<T>)?.bind(it, position)
                        setAnimation((holder as? BaseViewHolder<T>)?.itemView!!, position)

                    }
                }
            }
        } catch (e: NullPointerException) {
            Timber.e(e)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(MyApplication.appContext, android.R.anim.fade_in)
            animation.duration = 500
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


}





