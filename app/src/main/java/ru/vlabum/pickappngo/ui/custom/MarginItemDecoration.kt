package ru.vlabum.pickappngo.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoryMarginItemDecoration(
    private val cLeft: Int = -1,
    private val cTop: Int = -1,
    private val cRight: Int = -1,
    private val cBottom: Int = -1
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0 && cTop >= 0) {
                top = cTop
            }
            if (cLeft >= 0) left = cLeft
            if (cRight >= 0) right = cRight
            if (cBottom >= 0) bottom = cBottom
        }
    }
}