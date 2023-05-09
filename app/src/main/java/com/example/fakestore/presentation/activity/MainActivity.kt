package com.example.fakestore.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fakestore.App
import com.example.fakestore.R
import com.example.fakestore.databinding.ActivityMainBinding
import com.example.fakestore.navigation.BackPressedListener
import com.example.fakestore.navigation.IScreens
import com.example.fakestore.presentation.fragments.StoreFragment
import com.example.fakestore.presentation.presenter.MainPresenter
import com.example.fakestore.presentation.view.MainView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navigator = AppNavigator(this, R.id.container)

//    @Inject
//    lateinit var router: Router
//    @Inject
//    lateinit var screens: IScreens
//    @Inject
//    lateinit var navigatorHolder: NavigatorHolder

    private val presenter: MainPresenter by moxyPresenter {
        //MainPresenter(router, screens)
        MainPresenter()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent
            .inject(this)



        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        //navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        //navigatorHolder.removeNavigator()
    }

//    override fun onBackPressed() {
//        println("backActivity")
//        supportFragmentManager.fragments.forEach { it ->
//            if (it is BackPressedListener && it.backPressed()) {
//                it.childFragmentManager.popBackStack()
//                return
//            }
//        }
//
//        presenter.backClicked()
//    }
}