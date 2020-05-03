package ru.vlabum.pickappngo.ui.catalog

import android.view.WindowManager
import androidx.fragment.app.viewModels
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.viewmodels.catalog.CatalogViewModel

class CategoryFragment : BaseFragment<CatalogViewModel>() {

    override val viewModel: CatalogViewModel by viewModels()

    override val layout: Int = R.layout.fragment_catalog

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


    override fun setupViews() {
        //window resize options
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDestroyView() {
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }
}
