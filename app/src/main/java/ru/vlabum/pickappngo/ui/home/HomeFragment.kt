package ru.vlabum.pickappngo.ui.home

import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.viewmodels.splash.SplashViewModel


/**
 * Домашний фрагмент
 */
class HomeFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override val layout: Int = R.layout.fragment_home

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setTitle(getResources().getString(R.string.app_name_title))
            .setSubtitle("SubTitle")
            .setLogo(R.drawable.logo)
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
