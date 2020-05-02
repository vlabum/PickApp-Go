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

        setupViews()
    }
}