package com.sidecarhealth.modules.common.customItemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecoration @JvmOverloads constructor(private val spanCount : Int,private val verticalSpacing: Int, private val horizontalSpacing:Int, private var displayMode: Int = -1) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager!!, position, itemCount)
    }



    private fun setSpacingForDirection(outRect: Rect,
                                       layoutManager: RecyclerView.LayoutManager,
                                       position: Int,
                                       itemCount: Int) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }


            when (displayMode) {
                HORIZONTAL -> {
                    outRect.left = if (position<spanCount) horizontalSpacing else horizontalSpacing / 2
                    outRect.right = if (position == itemCount || position == itemCount - 1) horizontalSpacing else horizontalSpacing / 2
                    outRect.top = if(spanCount!=0){if (position % spanCount == 0) verticalSpacing else verticalSpacing / 2} else verticalSpacing
                    outRect.bottom = if(spanCount!=0){if (position % spanCount == 0) verticalSpacing / 2 else verticalSpacing} else verticalSpacing
                }
                VERTICAL -> {
                    outRect.left = horizontalSpacing
                    outRect.right = horizontalSpacing
                    outRect.top = verticalSpacing
                    outRect.bottom = if (position == itemCount - 1) verticalSpacing else 0
                }
                GRID -> if (layoutManager is GridLayoutManager) {
                    val cols = layoutManager.spanCount
                    val rows = itemCount / cols

                    outRect.left = horizontalSpacing
                    outRect.right = if (position % cols == cols - 1) horizontalSpacing else 0
                    outRect.top = verticalSpacing
                    outRect.bottom = if (position / cols == rows - 1) verticalSpacing else 0
                }
            }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
        val GRID = 2
    }
}