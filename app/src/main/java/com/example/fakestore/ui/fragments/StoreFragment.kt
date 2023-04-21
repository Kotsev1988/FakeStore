package com.example.fakestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fakestore.App
import com.example.fakestore.databinding.FragmentStoreBinding
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.domain.productsEntity.Products
import com.example.fakestore.presenter.StorePresenter
import com.example.fakestore.ui.activity.BackPressedListener
import com.example.fakestore.ui.adapters.MainAdapter
import com.example.fakestore.ui.adapters.bestSellers.BestSellers
import com.example.fakestore.ui.adapters.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.ui.adapters.categories.Category
import com.example.fakestore.ui.adapters.categories.CategoryDelegateAdapter
import com.example.fakestore.ui.adapters.header.Header
import com.example.fakestore.ui.adapters.header.HeaderDelegateAdapter
import com.example.fakestore.ui.adapters.search.Search
import com.example.fakestore.ui.adapters.search.SearchDelegateAdapter
import com.example.fakestore.domain.view.StoreView

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class StoreFragment : MvpAppCompatFragment(), StoreView, BackPressedListener {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!


    private val mainAdapter by lazy {
        MainAdapter.Builder()
            .add(HeaderDelegateAdapter())
            .add(CategoryDelegateAdapter())
            .add(SearchDelegateAdapter(presenter.searchClick))
            .add(HeaderDelegateAdapter())
            .add(BestSellersDelegateAdapter())
            .build()
    }

    private val headerCategory: Header = Header("Select Category")
    private val headerProduct: Header = Header("BestSellers")

    var listDelegates = listOf(headerProduct, Category(Categories(), null),
        Search(arrayListOf(), null),
        headerCategory,
        BestSellers(Products(), null)
    )


    @Inject
    lateinit var productList: IGetProducts

    @Inject
    lateinit var categoryList: IGetCategories

    @Inject
    lateinit var searchingData: IGetAllProducts

    @Inject
    lateinit var router: Router

    private val presenter: StorePresenter by moxyPresenter {
        StorePresenter(
            categoryList,
            productList,
            router,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            StoreFragment().apply {
                App.instance.appComponent.inject(this)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = mainAdapter
    }



    override fun init() {

        binding.frameLoad.visibility = View.VISIBLE

        listDelegates = listOf(headerProduct, Category(Categories(), presenter.listCategory),
            Search(arrayListOf(),  presenter.searchClick),
            headerCategory,
            BestSellers(Products(), presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)

    }

    override fun onError(e: Throwable) {
        Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
    }

    override fun updateList() {
        binding.frameLoad.visibility = View.GONE

        mainAdapter.notifyDataSetChanged()
    }



    override fun backPressed(): Boolean {
        return presenter.backClicked()
    }
}