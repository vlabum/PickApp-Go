package ru.vlabum.pickappngo.ui.catalog

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_catalog.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.data.CategoryItemData
import ru.vlabum.pickappngo.extensions.dpToIntPx
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.Binding
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.ui.custom.CategoryMarginItemDecoration
import ru.vlabum.pickappngo.ui.delegates.RenderProp
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.catalog.CatalogViewModel
import ru.vlabum.pickappngo.viewmodels.catalog.CategoryState

class CategoryFragment : BaseFragment<CatalogViewModel>() {

    override val viewModel: CatalogViewModel by viewModels()
    override val layout: Int = R.layout.fragment_catalog
    override val binding: CategoryBinding by lazy { CategoryBinding() }

    private val categoryAdapter = CategoryAdapter {}

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setTitle(getResources().getString(ru.vlabum.pickappngo.R.string.app_name_title))
            .setSubtitle("SubTitle")
            .setLogo(ru.vlabum.pickappngo.R.drawable.logo)
            .addMenuItem(
                ru.vlabum.pickappngo.ui.base.MenuItemHolder(
                    "search",
                    R.id.action_search,
                    R.drawable.ic_search_black_24dp,
                    R.layout.search_view_layout
                )
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun setupViews() {
        rv_articles ?: return
        with(rv_articles!!) {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
            addItemDecoration(
                CategoryMarginItemDecoration(
                    cLeft = context.dpToIntPx(28),
                    cTop = context.dpToIntPx(20),
                    cRight = context.dpToIntPx(28),
                    cBottom = context.dpToIntPx(20)
                )
            )
        }
        //window resize options
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDestroyView() {
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }

    inner class CategoryBinding : Binding() {
        private var articles: List<CategoryItemData> by RenderProp(emptyList<CategoryItemData>()) {
            categoryAdapter.submitList(it)
        }

        override fun bind(data: IViewModelState) {
            data as CategoryState
            articles = data.categoryOld
        }
    }

}
