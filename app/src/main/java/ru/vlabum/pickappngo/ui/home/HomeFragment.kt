package ru.vlabum.pickappngo.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.extensions.hideKeyboard
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.ui.custom.MarginItemDecoratorLeft
import ru.vlabum.pickappngo.viewmodels.home.HomeViewModel


/**
 * Домашний фрагмент
 */
class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val layout: Int = R.layout.fragment_home

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setVisibility(false)
    }

    val customerChoiceAdapter = HomePitmAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/  },
        { item -> /*TODO add basket*/},
        { item -> /*TODO insert into adore product*/}
    )

    val newsOfTheWeekAdapter = HomePitmAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/  },
        { item -> /*TODO add basket*/},
        { item -> /*TODO insert into adore product*/}
    )

    val goodsOfTheWeekAdapter = HomePitmAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/  },
        { item -> /*TODO add basket*/},
        { item -> /*TODO insert into adore product*/}
    )

    val categoriesAdapter = HomeCategoryAdapter(
        { item -> /*val action = //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))*/  }
    )

    override fun setupViews() {
        //window resize options
//        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        with(rv_h_category) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
            addItemDecoration(MarginItemDecoratorLeft(resources.getDimension(R.dimen.pitm_margin_hrv).toInt()))
        }
        viewModel.observerCategoriesList(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
        }

        with(rv_h_customer_choice) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = customerChoiceAdapter
            addItemDecoration(MarginItemDecoratorLeft(resources.getDimension(R.dimen.pitm_margin_hrv).toInt()))
        }
        viewModel.observerCustomerChoiceList(viewLifecycleOwner) {
            customerChoiceAdapter.submitList(it)
        }

        with(rv_h_news_of_the_week) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsOfTheWeekAdapter
            addItemDecoration(MarginItemDecoratorLeft(resources.getDimension(R.dimen.pitm_margin_hrv).toInt()))
        }
        viewModel.observerNewsOfWeekList(viewLifecycleOwner) {
            newsOfTheWeekAdapter.submitList(it)
        }

        with(rv_h_goods_of_the_week) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = goodsOfTheWeekAdapter
            addItemDecoration(MarginItemDecoratorLeft(resources.getDimension(R.dimen.pitm_margin_hrv).toInt()))
        }
        viewModel.observerGoodsOfWeekList(viewLifecycleOwner) {
            goodsOfTheWeekAdapter.submitList(it)
        }

        et_home_search.setOnEditorActionListener { view, _, _ ->
            mainActivity.hideKeyboard(view)
            view.clearFocus()
            true
        }

    }

    override fun onDestroyView() {
//        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }
}
