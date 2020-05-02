package ru.vlabum.pickappngo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.ui.base.BaseActivity
import ru.vlabum.pickappngo.viewmodels.base.IViewModelState
import ru.vlabum.pickappngo.viewmodels.base.NavigationCommand
import ru.vlabum.pickappngo.viewmodels.splash.SplashViewModel

class MainActivity : BaseActivity<SplashViewModel>() {

    override val layout: Int = R.layout.activity_main
    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appbarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_catalog
            )
        )

        setupActionBarWithNavController(navController, appbarConfiguration)
        nav_bm_view.setOnNavigationItemSelectedListener {
            //if click on bottom navigation item -> navigate to destination by item id
            viewModel.navigate(NavigationCommand.To(it.itemId))
            true
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //if destination change set select bottom navigation item
            //nav_view.selectDestination(destination)
        }
    }

    override fun subscribeOnState(state: IViewModelState) {
        return
    }
}
