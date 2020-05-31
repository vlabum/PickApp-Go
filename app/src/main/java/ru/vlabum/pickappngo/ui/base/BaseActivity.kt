package ru.vlabum.pickappngo.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.marginRight

import androidx.navigation.findNavController
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior

import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.extensions.dpToIntPx
import ru.vlabum.pickappngo.viewmodels.base.BaseViewModel
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.base.NavigationCommand

abstract class BaseActivity<T : BaseViewModel<out IViewModelState>> : AppCompatActivity() {

    abstract val viewModel: T
    protected abstract val layout: Int
    lateinit var navController: NavController

    val toolbarBuilder = ToolbarBuilder()
    val bottombarBuilder = BottombarBuilder()

    abstract fun subscribeOnState(state: IViewModelState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)
        viewModel.observeState(this) { subscribeOnState(it) }
        viewModel.observeNavigation(this) { subscribeOnNavigation(it) }
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun subscribeOnNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> {
                navController.navigate(
                    command.destination,
                    command.args,
                    command.options,
                    command.extras
                )
            }

            is NavigationCommand.CatalogPage -> {
                navController.navigate(R.id.nav_catalog)
            }

//            is NavigationCommand.FinishLogin -> {
//                navController.navigate(R.id.finish_login)
//                if (command.privateDestination != null) navController.navigate(command.privateDestination)
//            }
//
//            is NavigationCommand.StartLogin -> {
//                navController.navigate(
//                    R.id.start_login,
//                    bundleOf("private_destination" to (command.privateDestination ?: -1))
//                )
//            }
        }
    }

}


data class MenuItemHolder(
    val title: String,
    val menuId: Int,
    val icon: Int,
    val actionViewLayout: Int? = null,
    val clickListener: ((MenuItem) -> Unit)? = null
)


class ToolbarBuilder() {
    var title: String? = null
    var subtitle: String? = null
    var logo: Int? = null
    var visibility: Boolean = true
    val items: MutableList<MenuItemHolder> = mutableListOf()

    fun setTitle(title: String): ToolbarBuilder {
        this.title = title
        return this
    }

    fun setSubtitle(subtitle: String): ToolbarBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setLogo(logo: Int): ToolbarBuilder {
        this.logo = logo
        return this
    }

    fun setVisibility(isVisible: Boolean): ToolbarBuilder {
        this.visibility = isVisible
        return this
    }

    fun addMenuItem(item: MenuItemHolder): ToolbarBuilder {
        this.items.add(item)
        return this
    }

    fun invalidate(): ToolbarBuilder {
        this.title = null
        this.subtitle = null
        this.logo = null
        this.visibility = true
        this.items.clear()
        return this
    }

    fun prepare(prepareFn: (ToolbarBuilder.() -> Unit)?): ToolbarBuilder {
        prepareFn?.invoke(this)
        return this
    }

    fun build(context: FragmentActivity) {

        //show appbar if hidden due to scroll behavior
        context.appbar.setExpanded(true, true)

        with(context.toolbar) {

            this.visibility = if (this@ToolbarBuilder.visibility) View.VISIBLE else View.GONE

            if (this@ToolbarBuilder.title != null)
                title = this@ToolbarBuilder.title

            if (this@ToolbarBuilder.subtitle != null)
                subtitle = this@ToolbarBuilder.subtitle

            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

            if (this@ToolbarBuilder.logo != null) {
                val logoPlaceholder = getDrawable(
                    context,
                    R.drawable.logo_placeholder
                )
                logo = logoPlaceholder
            } else {
                logo = null
            }

            children.forEach { view ->
                when (view) {
                    is ImageButton -> {
                        val navButton = children.last() as? ImageButton
                        navButton?.let { navbuttonSettings(context, navButton) }
                    }
                    is ImageView -> {
                        val logo = children.last() as? ImageView
                        logo?.let { logoSettings(context, logo) }
                    }
                }
            }

            this.setTitleMarginStart(
                context.dpToIntPx(1)
            )
        }
    }

    private fun navbuttonSettings(context: FragmentActivity, navButton: ImageButton) {
        (navButton.layoutParams as? Toolbar.LayoutParams)?.let {
            it.marginStart = context.dpToIntPx(8)
            it.marginEnd = context.dpToIntPx(8)
            it.width = context.dpToIntPx(40)
            it.height = context.dpToIntPx(40)
            navButton.layoutParams = it
        }
    }

    private fun logoSettings(context: FragmentActivity, logo: ImageView) {
        val logoSize = context.dpToIntPx(40)
        val logoMargin = context.dpToIntPx(16)
        logo.scaleType = ImageView.ScaleType.CENTER_CROP
        (logo.layoutParams as? Toolbar.LayoutParams)?.let {
            it.width = logoSize
            it.height = logoSize
            it.leftMargin = context.dpToIntPx(1)
            it.marginStart = context.dpToIntPx(1)
            it.marginEnd = logoMargin
            logo.layoutParams = it
        }

        Glide.with(context)
            .load(this@ToolbarBuilder.logo)
            .apply(circleCropTransform())
            .override(logoSize)
            .into(logo)
    }
}

class BottombarBuilder() {
    private var visible: Boolean = true
    private val views = mutableListOf<Int>()
    private val tempViews = mutableListOf<Int>()

    fun addView(layoutId: Int): BottombarBuilder {
        views.add(layoutId)
        return this
    }

    fun setVisibility(isVisible: Boolean): BottombarBuilder {
        visible = isVisible
        return this
    }

    fun prepare(prepareFn: (BottombarBuilder.() -> Unit)?): BottombarBuilder {
        prepareFn?.invoke(this)
        return this
    }

    fun invalidate(): BottombarBuilder {
        visible = true
        views.clear()
        return this
    }

    fun build(context: FragmentActivity) {

        //remove temp views
        if (tempViews.isNotEmpty()) {
            tempViews.forEach {
                val view = context.container.findViewById<View>(it)
                context.container.removeView(view)
            }
            tempViews.clear()
        }

        //add new bottom bar views
        if (views.isNotEmpty()) {
            val inflater = LayoutInflater.from(context)
            views.forEach {
                val view = inflater.inflate(it, context.container, false)
                context.container.addView(view)
                tempViews.add(view.id)
            }
        }

        with(context.nav_bm_view) {
            isVisible = visible
            //show bottombar if hidden due to scroll behavior
//            ((layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBottomViewOnScrollBehavior)
//                .slideUp(this)
        }
    }

}