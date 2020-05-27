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

class HCustomerChoiceAdapter(
    private val listenerBasket: (ProductItemData) -> Unit,
    private val listenerLike: (String, Boolean) -> Unit
) :
    PagedListAdapter<ProductItemData, ArticleVH>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
//        val view = ProductItemView(parent.context)
        return ArticleVH(view)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(getItem(position), listenerBasket, listenerLike)
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ProductItemData>() {
    override fun areItemsTheSame(oldItem: ProductItemData, newItem: ProductItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductItemData, newItem: ProductItemData): Boolean {
        return oldItem == newItem
    }
}

class ArticleVH(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(
        item: ProductItemData?,
        listener: (ProductItemData) -> Unit,
        listenerBookmark: ((String, Boolean) -> Unit)
    ) {

        val view = containerView.findViewById<ProductItemView>(R.id.pitm1)

        //if use placeholder item may be null
        item ?: return
        //(containerView as ProductItemView)
        view.bind(item) { p1, p2 ->
            listenerBookmark(p1, p2)
        }
        itemView.setOnClickListener { listener(item) }

    }

}