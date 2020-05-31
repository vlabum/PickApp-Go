package ru.vlabum.pickappngo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.ProductItemData
import ru.vlabum.pickappngo.ui.custom.view.ProductItemView

class HomePitmAdapter(
    private val listener: (ProductItemData) -> Unit,
    private val listenerBasket: (ProductItemData) -> Unit,
    private val listenerLike: (ProductItemData) -> Unit
) :
    PagedListAdapter<ProductItemData, GoodsVH>(GoodsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return GoodsVH(view)
    }

    override fun onBindViewHolder(holder: GoodsVH, position: Int) {
        holder.bind(getItem(position), listener, listenerBasket, listenerLike)
    }
}

class GoodsDiffCallback : DiffUtil.ItemCallback<ProductItemData>() {
    override fun areItemsTheSame(oldItem: ProductItemData, newItem: ProductItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductItemData, newItem: ProductItemData): Boolean {
        return oldItem == newItem
    }
}

class GoodsVH(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(
        item: ProductItemData?,
        listener: (ProductItemData) -> Unit,
        listenerBasket: (ProductItemData) -> Unit,
        listenerLike: ((ProductItemData) -> Unit)
    ) {

        val view = containerView.findViewById<ProductItemView>(R.id.goods_item)

        //if use placeholder item may be null
        item ?: return
        //(containerView as ProductItemView)
        view.bind(
            item,
            { p1 -> listenerLike(p1) },
            { p1 -> listenerBasket(p1) }
        )
        itemView.setOnClickListener { listener(item) }

    }

}