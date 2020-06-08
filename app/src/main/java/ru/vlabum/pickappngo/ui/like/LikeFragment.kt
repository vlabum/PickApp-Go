package ru.vlabum.pickappngo.ui.like

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_catalog.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.Binding
import ru.vlabum.pickappngo.ui.base.MenuItemHolder
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.ui.catalog.CatalogGoodsAdapter
import ru.vlabum.pickappngo.ui.delegates.RenderProp
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.catalog.CatalogState
import ru.vlabum.pickappngo.viewmodels.catalog.CatalogViewModel
import ru.vlabum.pickappngo.viewmodels.like.LikeState
import ru.vlabum.pickappngo.viewmodels.like.LikeViewModel

class LikeFragment : BaseFragment<LikeViewModel>() {

    override val viewModel: LikeViewModel by viewModels()
    override val layout: Int = R.layout.fragment_catalog
    override val binding: CatalogBinding by lazy { CatalogBinding() }

    private val goodsAdapter = CatalogGoodsAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/ },
        { item -> /*TODO add basket*/ },
        { item -> viewModel.handleToggleLike(item) }
    )

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.addMenuItem(
            MenuItemHolder(
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
        rv_catalog ?: return
        with(rv_catalog!!) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            (layoutManager as StaggeredGridLayoutManager).gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            adapter = goodsAdapter
//            addItemDecoration(GridItemDecoration(24, 2))
        }
        viewModel.observerLikeGoods(viewLifecycleOwner) {
            goodsAdapter.submitList(it)
            //window resize options
        }
    }

    override fun onDestroyView() {
//        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = (menuItem?.actionView as SearchView)
        searchView.queryHint = getString(R.string.search_placeholder)

        //restore SearchView
        if (binding.isSearch) {
            menuItem.expandActionView()
            searchView.setQuery(binding.searchQuery, false)

            if (binding.isFocusedSearch) searchView?.requestFocus()
            else searchView?.clearFocus()
        }

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                viewModel.handleSearchMode(true)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.handleSearchMode(false)
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //viewModel.handleSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //viewModel.handleSearch(newText)
                return true
            }
        })
    }

    inner class CatalogBinding : Binding() {

        var isSearch: Boolean by RenderProp(false)
        var isFocusedSearch: Boolean = false
        var searchQuery: String? = null

        override fun bind(data: IViewModelState) {
            data as LikeState
            isSearch = data.isSearch
            searchQuery = data.searchQuery
        }
    }


}
