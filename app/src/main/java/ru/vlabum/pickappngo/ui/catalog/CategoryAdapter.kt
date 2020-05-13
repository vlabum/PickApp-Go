package ru.vlabum.pickappngo.ui.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.CategoryItemData
import ru.vlabum.pickappngo.extensions.dpToIntPx

class CategoryAdapter(
    private val listener: (CategoryItemData) -> Unit
) : ListAdapter<CategoryItemData, CategoryVH>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryVH(containerView)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(getItem(position), listener)
    }

}

class CategoryVH(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: CategoryItemData, listener: (CategoryItemData) -> Unit) {
        val cornerRadius = containerView.context.dpToIntPx(8)

        Glide.with(containerView.context)
            .load(R.drawable.category_milk)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .into(iv_picture)

        tv_caption.text = item.caption

        itemView.setOnClickListener { listener(item) }
    }

//    fun getImage(imageName: String?): Int {
//        return containerView.getResources()
//            .getIdentifier(imageName, "drawable", null)
//    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItemData>() {
    override fun areItemsTheSame(oldItem: CategoryItemData, newItem: CategoryItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryItemData, newItem: CategoryItemData): Boolean {
        return oldItem == newItem
    }
}