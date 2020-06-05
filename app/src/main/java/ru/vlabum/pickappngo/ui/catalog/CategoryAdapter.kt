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
            .inflate(R.layout.item_product_catalog, parent, false)
        return CategoryVH(containerView)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(getItem(position), listener)
    }

}

class CategoryVH(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(itemOld: CategoryItemData, listener: (CategoryItemData) -> Unit) {

        Glide.with(containerView.context)
            .load(R.drawable.category_milk)
            .transform(CenterCrop())
            .into(iv_category_item)

        tv_category_title.text = itemOld.caption

        itemView.setOnClickListener { listener(itemOld) }
    }

//    fun getImage(imageName: String?): Int {
//        return containerView.getResources()
//            .getIdentifier(imageName, "drawable", null)
//    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItemData>() {
    override fun areItemsTheSame(oldItemOld: CategoryItemData, newItemOld: CategoryItemData): Boolean {
        return oldItemOld.id == newItemOld.id
    }

    override fun areContentsTheSame(oldItemOld: CategoryItemData, newItemOld: CategoryItemData): Boolean {
        return oldItemOld == newItemOld
    }
}