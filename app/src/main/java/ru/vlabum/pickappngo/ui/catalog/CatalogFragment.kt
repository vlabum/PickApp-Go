package ru.vlabum.pickappngo.ui.catalog

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_catalog.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.ui.custom.GridItemDecoration
import ru.vlabum.pickappngo.ui.home.HomeGoodsAdapter
import ru.vlabum.pickappngo.viewmodels.home.HomeViewModel

class CatalogFragment : BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layout: Int = R.layout.fragment_catalog



    private val goodsAdapter = CatalogGoodsAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/ },
        { item -> /*TODO add basket*/ },
        { item -> /*TODO insert into adore product*/ }
    )

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setVisibility(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun setupViews() {
        rv_articles ?: return
        with(rv_articles!!) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = goodsAdapter
            addItemDecoration(GridItemDecoration(24, 2))
        }
        viewModel.observerCustomerChoiceList(viewLifecycleOwner) {
            goodsAdapter.submitList(it)
            //window resize options
        }
    }

    override fun onDestroyView() {
//        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }


}
