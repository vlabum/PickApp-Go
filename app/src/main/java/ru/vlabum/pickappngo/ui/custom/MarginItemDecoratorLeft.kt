package ru.vlabum.pickappngo.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Только для первого элемента добавляет поле слева
 * и для всех элементов всегда добавляет поле справа
 */
class MarginItemDecoratorLeft(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = spaceHeight
            }
            right = spaceHeight
        }
    }
}