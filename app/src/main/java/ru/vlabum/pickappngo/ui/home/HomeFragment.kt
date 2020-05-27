package ru.vlabum.pickappngo.ui.home

import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.ui.custom.CategoryMarginItemDecoration
import ru.vlabum.pickappngo.viewmodels.home.HomeViewModel
import ru.vlabum.pickappngo.viewmodels.splash.SplashViewModel


/**
 * Домашний фрагмент
 */
class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val layout: Int = R.layout.fragment_home

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setVisibility(false)
    }

    open val customerChoiceAdapter = HCustomerChoiceAdapter(
        { item ->
            //val action =
            //viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))

        },
        { itemId, isLike ->

        }
    )

    override fun setupViews() {
        //window resize options
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        with(rv_h_customer_choice) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = customerChoiceAdapter
            addItemDecoration(CategoryMarginItemDecoration(cRight = 32))
            elevation = 12f
        }

        viewModel.observerList(viewLifecycleOwner) {
            customerChoiceAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }
}
