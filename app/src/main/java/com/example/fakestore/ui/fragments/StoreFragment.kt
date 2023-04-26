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
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.productsEntity.Rating
import com.example.fakestore.domain.view.StoreView
import com.example.fakestore.presenter.StorePresenter
import com.example.fakestore.ui.activity.BackPressedListener
import com.example.fakestore.ui.adapters.MainAdapter
import com.example.fakestore.ui.adapters.bestSellers.BestSellers
import com.example.fakestore.ui.adapters.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.ui.adapters.categories.Category
import com.example.fakestore.ui.adapters.categories.CategoryDelegateAdapter
import com.example.fakestore.ui.adapters.header.Header
import com.example.fakestore.ui.adapters.header.HeaderDelegateAdapter
import com.example.fakestore.ui.adapters.locationAndFilter.LocationAndFilter
import com.example.fakestore.ui.adapters.locationAndFilter.LocationAndFilterDelegateAdapter
import com.example.fakestore.ui.adapters.search.Search
import com.example.fakestore.ui.adapters.search.SearchDelegateAdapter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

const val KAY_PARENT = "key_parent"

class StoreFragment : MvpAppCompatFragment(), StoreView, BackPressedListener {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!


    private val mainAdapter by lazy {
        MainAdapter.Builder()
            .add(LocationAndFilterDelegateAdapter())
            .add(HeaderDelegateAdapter())
            .add(CategoryDelegateAdapter())
            .add(SearchDelegateAdapter(presenter.searchClick))
            .add(HeaderDelegateAdapter())
            .add(BestSellersDelegateAdapter())
            .build()
    }

    private val headerCategory: Header = Header("Select Category")
    private val headerProduct: Header = Header("BestSellers")

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

    var listDelegates =
        listOf(LocationAndFilter("Baksan", null), headerProduct, Category(Categories(), null),
            Search(arrayListOf(), null),
            headerCategory,
            BestSellers(Products(), null)
        )

    var productsFilter = Products()

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

        listDelegates = listOf(LocationAndFilter("Baksan", presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
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
        productsFilter.clear()
        productsFilter.addAll(presenter.listProduct.products)

        mainAdapter.notifyDataSetChanged()
    }

    lateinit var bottomDialog: BottomFragment

    override fun showBottomDialog() {


        bottomDialog = BottomFragment.newInstance(productsFilter)
        bottomDialog.show(this.childFragmentManager, "tag")

        childFragmentManager.setFragmentResultListener(KAY_PARENT, this) { _, result ->
            presenter.filter(result.getString("brand"),
                result.getString("price"),
                result.getString("size"))
            bottomDialog.dismiss()
        }
    }

    override fun filter() {

        val filterProductsResult = Products()
        filterProductsResult.addAll(presenter.listProduct.products)

        if (filterProductsResult.size == 0) {
            filterProductsResult.add(ProductsItem("", "", 0, "", 0.0, Rating(0, 0.0), "", 0))
        }

        listDelegates = listOf(LocationAndFilter("Baksan", presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
            headerCategory,
            BestSellers(filterProductsResult, presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)

    }


    override fun backPressed(): Boolean {
        return presenter.backClicked()
    }


}