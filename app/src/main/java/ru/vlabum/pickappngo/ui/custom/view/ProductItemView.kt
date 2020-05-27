package ru.vlabum.pickappngo.ui.custom.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.extensions.dpToIntPx
import ru.vlabum.pickappngo.extensions.dpToPx

class ProductItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val iv_like: CheckableImageView
    private val iv_basket: CheckableImageView


    lateinit var tv_itemName: TextView
    lateinit var tv_itemPrice: TextView
    lateinit var iv_image: ImageView
    lateinit var rview: View

    private val likeColor = context.getColor(R.color.like_red)

    val defaultPadding = context.dpToIntPx(8)
    val addPadding = context.dpToIntPx(4)
    val imageWidth = context.dpToIntPx(93)
    val imageHeight = context.dpToIntPx(57)
    val imageTop = context.dpToIntPx(42)
    val likeSize = context.dpToIntPx(22)
    val basketSize = context.dpToIntPx(34)
    val margin = context.dpToIntPx(12)
    val countRowForCaption = 2
    val cardWidth = context.dpToIntPx(156)
    val cardHeight = context.dpToIntPx(204)


    lateinit var p: Paint
    lateinit var pDraft: Paint

    init {
        setPadding(defaultPadding)

        elevation = context.dpToPx(9)

        pDraft = Paint()
        pDraft.color = Color.RED
        pDraft.style = Paint.Style.STROKE
        pDraft.strokeWidth = 2f

        p = Paint()
        p.color = ContextCompat.getColor(context, R.color.border_gray);
        p.style = Paint.Style.STROKE
        p.strokeWidth = 2f

        //layoutParams = LayoutParams(sizeWith, sizeHeight)
        iv_image = ImageView(context)
        addView(iv_image)

        tv_itemName = TextView(context)
        tv_itemName.textSize = 12f
        tv_itemName.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        tv_itemName.setText("Одуванчик полевой с очень длинным названием")
        //tv_itemName.setText("Одуванчик полевой")
        addView(tv_itemName)

        tv_itemPrice = TextView(context)
        tv_itemPrice.textSize = 14f
        tv_itemPrice.setText("1234,00 / 200 г")
        addView(tv_itemPrice)


        Glide.with(context)
            //.load(R.drawable.image_blueberry)
            .load(R.drawable.image_blueberry)
            .transform(FitCenter())
            .override(imageWidth, imageHeight)
            .into(iv_image)

        iv_like = CheckableImageView(context).apply {
            id = R.id.iv_like
            layoutParams = LayoutParams(likeSize, likeSize)
            imageTintList = ColorStateList.valueOf(likeColor)
            setImageResource(R.drawable.ic_pitm_like)
        }
        addView(iv_like)

        iv_basket = CheckableImageView(context).apply {
            id = R.id.iv_basket
            layoutParams = LayoutParams(basketSize, basketSize)
            imageTintList = ColorStateList.valueOf(likeColor)
            setImageResource(R.drawable.ic_bm_basket)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        addView(iv_basket)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawLine(
            (paddingLeft + addPadding).toFloat(),
            (measuredHeight - tv_itemPrice.measuredHeight - paddingBottom - margin).toFloat(),
            canvas.width.toFloat() - (paddingRight + addPadding).toFloat(),
            (measuredHeight - tv_itemPrice.measuredHeight - paddingBottom - margin).toFloat(),
            p
        )
//        canvas?.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), pDraft)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedHeight = paddingTop
        val width = cardWidth //getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        measureChild(iv_like, widthMeasureSpec, heightMeasureSpec)
        usedHeight += iv_image.measuredHeight + margin

        val iv_lp = iv_image.layoutParams
        iv_lp.width = imageWidth
        iv_lp.height = imageHeight
        iv_image.layoutParams = iv_lp
        measureChild(iv_image, widthMeasureSpec, heightMeasureSpec)
        usedHeight += iv_image.measuredHeight

        measureChild(tv_itemName, widthMeasureSpec, heightMeasureSpec)
        measureChild(tv_itemPrice, widthMeasureSpec, heightMeasureSpec)
        //под tv_itemName возьмем 3 строки. Карточку не хочу увеличивать.
        //tv_itemPrice = одна строка, т.к. цена
        usedHeight += (tv_itemPrice.measuredHeight * countRowForCaption) // учисываем наименование тована в 2 строки
        usedHeight += margin
        usedHeight += tv_itemPrice.measuredHeight

        measureChild(iv_basket, widthMeasureSpec, heightMeasureSpec)

        usedHeight += paddingBottom
        usedHeight = cardHeight
        setMeasuredDimension(width, usedHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val bodyWith = r - l - paddingLeft - paddingRight
        val left = paddingLeft
        val right = paddingRight + bodyWith
        var usedHeight = paddingTop + addPadding

        iv_like.layout(
            right - iv_like.measuredWidth - addPadding,
            usedHeight,
            right - addPadding,
            usedHeight + iv_like.measuredHeight
        )

        iv_image.layout(
            left, imageTop, right, imageTop + iv_image.measuredHeight
        )

        usedHeight += imageTop + iv_image.measuredHeight

        tv_itemName.layout(
            left,
            usedHeight,
            right,
            usedHeight + tv_itemName.measuredHeight
        )

        usedHeight += tv_itemPrice.measuredHeight * countRowForCaption + margin

        tv_itemPrice.layout(
            left,
            measuredHeight - tv_itemPrice.measuredHeight - paddingBottom,
            right,
            measuredHeight - paddingBottom
        )

        iv_basket.layout(
            measuredWidth - iv_basket.measuredWidth - paddingRight / 2,
            measuredHeight - iv_basket.measuredHeight - paddingBottom / 2,
            measuredWidth - paddingRight / 2,
            measuredHeight - paddingBottom / 2
        )

    }

    fun bind(item: ProductItemData, toggleBookmarkListener: (String, Boolean) -> Unit) {

        tv_itemName.text = item.title
        tv_itemPrice.text = item.price.toString()

        Glide.with(context)
            .load(item.imageId)
            .transform(CenterInside())
            .override(imageWidth, imageHeight)
            .into(iv_image)

    }
}
