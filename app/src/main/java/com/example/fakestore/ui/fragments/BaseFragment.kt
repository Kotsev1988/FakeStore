package com.example.fakestore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.App
import com.example.fakestore.R
import com.example.fakestore.data.GetCategoriesImpl
import com.example.fakestore.data.GetProductsImpl
import com.example.fakestore.data.api.StoreAPI
import com.example.fakestore.databinding.FragmentBaseBinding
import com.example.fakestore.ui.BackPressedListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class BaseFragment : MvpAppCompatFragment(), BaseView, BackPressedListener {

    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    val presenter: BasePresenter by moxyPresenter {
        BasePresenter( App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.explorer ->{

                    presenter.storeNavigate()
                   // navigateTo(StoreFragment())
                    true
                }

                R.id.myCart ->{

                    true
                }

                R.id.likes_Fragment ->{
                    true
                }

                R.id.myProfile ->{
                    true
                }

                else ->true
            }
        }
       // binding.mainBottomNavigationView.selectedItemId = R.id.explorer
    }

    private fun navigateTo(fragment: Fragment) {

        childFragmentManager.beginTransaction()
            .replace(R.id.mainContainerView, fragment)
            .addToBackStack("")
            .commit()
    }



    companion object {

        @JvmStatic
        fun newInstance() =
            BaseFragment()
    }

    override fun backPressed(): Boolean {
        return  presenter.backClicked()
    }


}