package ru.vlabum.pickappngo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.models.CategoryItemData

class HomeCategoryAdapter(
    private val listener: (CategoryItemData) -> Unit
) :
    PagedListAdapter<CategoryItemData, CategoryVH>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryVH(view)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItemData>() {
    override fun areItemsTheSame(oldItem: CategoryItemData, newItem: CategoryItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryItemData, newItem: CategoryItemData): Boolean {
        return oldItem == newItem
    }
}

class CategoryVH(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(
        item: CategoryItemData?,
        listener: (CategoryItemData) -> Unit
    ) {

        item ?: return

        Glide.with(containerView.context)
            .load(item.imageUrl)
            .into(iv_category_item)

        tv_category_title.text = item.title

        itemView.setOnClickListener { listener(item) }
    }
}
