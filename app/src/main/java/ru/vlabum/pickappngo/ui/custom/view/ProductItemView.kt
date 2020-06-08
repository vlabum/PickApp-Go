package ru.vlabum.pickappngo.ui.custom.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.extensions.dpToIntPx


class ProductItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val iv_like: CheckableImageView
    private val iv_basket: CheckableImageView


    var tv_itemName: TextView
    var tv_itemPrice: TextView
    var iv_image: ImageView
    lateinit var rview: View

    private val likeColor = context.getColor(R.color.like_red)

    val defaultPadding = context.dpToIntPx(0)
    val paddingWide = context.dpToIntPx(12)
    val padding = context.dpToIntPx(8)
    val imageWidth = context.dpToIntPx(93)
    val imageHeight = context.dpToIntPx(57)
    var imageTop = context.dpToIntPx(42)
    val likeSize = context.dpToIntPx(48)
    val basketSize = context.dpToIntPx(48)
    val margin = context.dpToIntPx(12)
    var marginTopName = context.dpToIntPx(110)
    val countRowForCaption = 2

    var typeFace: Typeface?
    var typeFacePrice1: Typeface?
    var typeFacePrice2: Typeface?

    var p: Paint

    init {
        setPadding(defaultPadding)

        p = Paint()
        p.color = ContextCompat.getColor(context, R.color.border_gray);
        p.style = Paint.Style.STROKE
        p.strokeWidth = 4f

        iv_image = ImageView(context)
        addView(iv_image)

        typeFace = ResourcesCompat.getFont(context, R.font.comfortaa)
        typeFacePrice1 = ResourcesCompat.getFont(context, R.font.comfortaa_bold)
        typeFacePrice2 = ResourcesCompat.getFont(context, R.font.comfortaa)

        tv_itemName = TextView(context)
        tv_itemName.textSize = 12f
        tv_itemName.setPadding(padding, 0, padding, 0)
        tv_itemName.typeface = typeFace
        tv_itemName.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        tv_itemName.setTextColor(ContextCompat.getColor(context, R.color.text))
        tv_itemName.gravity = Gravity.CENTER_HORIZONTAL
        addView(tv_itemName)

        tv_itemPrice = TextView(context)
        tv_itemPrice.setPadding(paddingWide, 0, paddingWide, 0)
        tv_itemPrice.typeface = typeFacePrice1
        tv_itemPrice.textSize = 9f
        addView(tv_itemPrice)


        iv_like = CheckableImageView(context).apply {
            id = R.id.iv_like
            layoutParams = LayoutParams(likeSize, likeSize)
            imageTintList = ColorStateList.valueOf(likeColor)
            setImageResource(R.drawable.pitm_like_states)
        }
        addView(iv_like)

        iv_basket = CheckableImageView(context).apply {
            id = R.id.iv_basket
            layoutParams = LayoutParams(basketSize, basketSize)
            setImageResource(R.drawable.pitm_basket_states)
        }
        addView(iv_basket)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawLine(
            (padding).toFloat(),
            (measuredHeight - iv_basket.measuredHeight - defaultPadding).toFloat(),
            canvas.width.toFloat() - (padding).toFloat(),
            (measuredHeight - iv_basket.measuredHeight - defaultPadding).toFloat(),
            p
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChild(iv_like, widthMeasureSpec, heightMeasureSpec)

        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        val iv_lp = iv_image.layoutParams
        imageTop = (height.toFloat() * 0.49).toInt() - imageHeight
        marginTopName = (height.toFloat() * 0.5392).toInt()
        iv_lp.width = imageWidth
        iv_lp.height = imageHeight
        iv_image.layoutParams = iv_lp
        measureChild(iv_image, widthMeasureSpec, heightMeasureSpec)

        measureChild(tv_itemName, widthMeasureSpec, heightMeasureSpec)
        measureChild(tv_itemPrice, widthMeasureSpec, heightMeasureSpec)
        measureChild(iv_basket, widthMeasureSpec, heightMeasureSpec)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val bodyWith = r - l - paddingLeft - paddingRight
        val left = paddingLeft
        val right = paddingRight + bodyWith
        var usedHeight = 0

        iv_like.layout(
            measuredWidth - iv_like.measuredWidth,
            0,
            measuredWidth,
            iv_like.measuredHeight
        )

        iv_image.layout(
            left, imageTop, right, imageTop + iv_image.measuredHeight
        )

        usedHeight += imageTop + iv_image.measuredHeight

        tv_itemName.layout(
            left,
            marginTopName,
            right,
            marginTopName + tv_itemName.measuredHeight
        )

        usedHeight += tv_itemPrice.measuredHeight * countRowForCaption + margin

        tv_itemPrice.layout(
            left,
            measuredHeight - tv_itemPrice.measuredHeight - iv_basket.measuredWidth / 2 + tv_itemPrice.measuredHeight / 2,
            right,
            measuredHeight - iv_basket.measuredWidth / 2 + tv_itemPrice.measuredHeight / 2
        )

        iv_basket.layout(
            measuredWidth - iv_basket.measuredWidth,
            measuredHeight - iv_basket.measuredHeight,
            measuredWidth,
            measuredHeight
        )

    }

    fun setLike(isLike: Boolean) {
        iv_like.isChecked = isLike
    }

    fun bind(item: ProductItemData,
             toggleLikeListener: ((ProductItemData) -> Unit)? = null,
             clickBasketListener: ((ProductItemData) -> Unit)? = null
    ) {

        typeFacePrice2 ?: return

        val pennies = item.price % 100
        val units = (item.price / 100)

        val price = "$units.$pennies"
        val fullPrice = SpannableString("$price / ${item.dimension}")

        val ss1 = SpannableString(fullPrice)
        ss1.setSpan(
            RelativeSizeSpan(1.6f),
            0,
            price.length - 3,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // set size
        ss1.setSpan(CustomTypefaceSpan(typeFacePrice2), price.length - 3, fullPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // set color

        tv_itemPrice.text = ss1
        tv_itemName.text = item.title
        Glide.with(context)
            .load(item.imageId)
            .transform(CenterInside())
            .override(imageWidth, imageHeight)
            .into(iv_image)

        iv_basket.setOnClickListener {
            clickBasketListener?.invoke(item)
        }
        iv_like.setOnClickListener {
            iv_like.toggle()
            toggleLikeListener?.invoke(item)
        }
    }
}


class CustomTypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}
