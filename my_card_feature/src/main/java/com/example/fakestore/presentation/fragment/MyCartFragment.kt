package com.example.fakestore.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.di.DaggerMyCardComponent
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.navigation.BackPressedListener
import com.example.fakestore.presentation.adapter.MyCardAdapter
import com.example.fakestore.presentation.presenter.MyCartPresenter
import com.example.fakestore.presentation.view.MyCartView
import com.example.fakestore.utils.InjectUtils
import com.example.product_feature.databinding.FragmentMyCartBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MyCartFragment : MvpAppCompatFragment(), MyCartView{

    private var _binding: FragmentMyCartBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var router: Router

    @Inject
    lateinit var myCardProducts: IMyCardProducts

    private var myAdapter: com.example.fakestore.presentation.adapter.MyCardAdapter? = null

    val presenter: MyCartPresenter by moxyPresenter {
       MyCartPresenter( AndroidSchedulers.mainThread(), myCardProducts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

DaggerMyCardComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
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
            MyCartFragment()
    }



    override fun updateList() {
        myAdapter = MyCardAdapter(presenter.myCartListProducts).apply {
            DaggerMyCardComponent.builder().baseComponent(context?.let {
                InjectUtils.provideBaseComponent(it.applicationContext)
            }).build().inject(this)
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