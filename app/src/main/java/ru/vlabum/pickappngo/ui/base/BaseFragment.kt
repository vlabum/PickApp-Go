package ru.vlabum.pickappngo.ui.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.pickappngo.ui.MainActivity
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState

abstract class BaseFragment<T : BaseViewModel<out IViewModelState>> : Fragment() {
    val mainActivity: MainActivity
        get() = activity as MainActivity

    open val binding: Binding? = null
    protected abstract val viewModel: T
    protected abstract val layout: Int

    open val prepareToolbar: (ToolbarBuilder.() -> Unit)? = null
    open val prepareBottombar: (BottombarBuilder.() -> Unit)? = null

    val toolbar
        get() = mainActivity.toolbar
    val bottombar
        get() = mainActivity.nav_bm_view

    abstract fun setupViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepare toolbar
        mainActivity.toolbarBuilder
            .invalidate()
            .prepare(prepareToolbar)
            .build(mainActivity)

        mainActivity.bottombarBuilder
            .invalidate()
            .prepare(prepareBottombar)
            .build(mainActivity)

        //restore state
        viewModel.restoreState()
        binding?.restoreUi(savedInstanceState)

        // owner it is view
        viewModel.observeState(viewLifecycleOwner) { binding?.bind(it) }

        //bind default values if viewmodel not loaded data
        if (binding?.isInflated == false) binding?.onFinishInflate()

        //viewModel.observeNotifications(viewLifecycleOwner) { mainActivity.renderNotification(it) }
        viewModel.observeNavigation(viewLifecycleOwner) { mainActivity.viewModel.navigate(it) }
        setupViews()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (mainActivity.toolbarBuilder.items.isNotEmpty()) {
            for ((index, menuHolder) in mainActivity.toolbarBuilder.items.withIndex()) {
                val item = menu.add(0, menuHolder.menuId, index, menuHolder.title)
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
                    .setIcon(menuHolder.icon)
                    .setOnMenuItemClickListener {
                        menuHolder.clickListener?.invoke(it)?.let { true } ?: false
                    }

                if (menuHolder.actionViewLayout != null) item.setActionView(menuHolder.actionViewLayout)
            }
        } else menu.clear()
        super.onPrepareOptionsMenu(menu)
    }

}