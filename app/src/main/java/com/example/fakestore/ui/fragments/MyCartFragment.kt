package com.example.fakestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.App
import com.example.fakestore.databinding.FragmentMyCartBinding
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.domain.view.MyCartView
import com.example.fakestore.presenter.MyCartPresenter
import com.example.fakestore.ui.activity.BackPressedListener
import com.example.fakestore.ui.adapters.MyCardAdapter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MyCartFragment : MvpAppCompatFragment(), MyCartView, BackPressedListener {

    private var _binding: FragmentMyCartBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var myCardProducts: IMyCardProducts

    private var myAdapter: MyCardAdapter? = null

    val presenter: MyCartPresenter by moxyPresenter {
       MyCartPresenter(router, AndroidSchedulers.mainThread(), myCardProducts)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyCartFragment().apply {
                App.instance.initProductSubcomponent().inject(this)
            }
    }

    override fun backPressed(): Boolean = presenter.backClicked()

    override fun updateList() {
        myAdapter = MyCardAdapter(presenter.myCartListProducts).apply {
            App.instance.productSubComponent?.inject(this)
        }

        binding.myShops.adapter = myAdapter
    }

    override fun setTotalPrice(totalPrice: String) {
        binding.totalPrice.text = totalPrice
    }

    override fun updatePrices() {
        myAdapter?.setData(presenter.myCartListProducts)
    }
}