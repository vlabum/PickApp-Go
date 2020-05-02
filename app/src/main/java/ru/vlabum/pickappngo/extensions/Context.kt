package ru.vlabum.pickappngo.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.dpToPx(dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics

    )
}

fun Context.dpToIntPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}

fun Context.attrValue(@AttrRes res: Int): Int {
    var value: Int? = null
    if (value == null) {
        val tv = TypedValue()
        if (this.theme.resolveAttribute(res, tv, true)) value = tv.data
        else throw Resources.NotFoundException("Resource with id $res not found")
    }
    return value
}