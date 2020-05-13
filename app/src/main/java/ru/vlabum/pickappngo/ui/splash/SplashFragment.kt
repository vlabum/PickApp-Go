package ru.vlabum.pickappngo.ui.splash

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_splash.*
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlabum.pickappngo.R
import ru.vlabum.pickappngo.extensions.dpToIntPx
import ru.vlabum.pickappngo.ui.base.BaseFragment
import ru.vlabum.pickappngo.ui.base.BottombarBuilder
import ru.vlabum.pickappngo.ui.base.ToolbarBuilder
import ru.vlabum.pickappngo.viewmodels.base.ViewModelFactory
import ru.vlabum.pickappngo.viewmodels.splash.SplashViewModel


/**
 * Splash. В зависимости от сохраненных логин/пароль открывает либо вход, либо Home
 */
class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override val layout: Int = R.layout.fragment_splash

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {
        this.setVisibility(false)
    }

    override val prepareBottombar: (BottombarBuilder.() -> Unit)? = {
        this.setVisibility(false)
    }


    override fun setupViews() {
        //window resize options
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        //init
        val imageSize = mainActivity.dpToIntPx(160)

        Glide.with(mainActivity)
            .load(R.drawable.logo)
            .apply(RequestOptions.circleCropTransform())
            .override(imageSize)
            .into(iv_logo)

        iv_logo.setOnClickListener {
            activity?.let {
                Navigation.findNavController(it, R.id.nav_host_fragment)
                    .navigate(R.id.action_nav_splash_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDestroyView()
    }

}
