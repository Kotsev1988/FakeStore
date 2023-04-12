package com.example.fakestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.App
import com.example.fakestore.data.GetCategoriesImpl
import com.example.fakestore.data.GetProductsImpl
import com.example.fakestore.data.api.StoreAPI
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.room.CategoriesCache
import com.example.fakestore.data.room.cache.room.ProductsCache
import com.example.fakestore.databinding.FragmentStoreBinding
import com.example.fakestore.ui.BackPressedListener
import com.example.fakestore.ui.delegateAdapter.MainAdapter
import com.example.fakestore.ui.delegateAdapter.bestSellers.BestSellers
import com.example.fakestore.ui.delegateAdapter.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.ui.delegateAdapter.categories.Category
import com.example.fakestore.ui.delegateAdapter.categories.CategoryDelegateAdapter
import com.example.fakestore.ui.network.AndroidNetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class StoreFragment : MvpAppCompatFragment(), StoreView, BackPressedListener {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter by lazy {
        MainAdapter.Builder()
            .add(CategoryDelegateAdapter())
            .add(BestSellersDelegateAdapter())
            .build()
    }

    val presenter: StorePresenter by moxyPresenter {
        StorePresenter(

            GetCategoriesImpl(StoreAPI(),
            AndroidNetworkStatus(App.instance),
            CategoriesCache(
                Database.getInstance())),

            GetProductsImpl(StoreAPI(),
                AndroidNetworkStatus(App.instance),
                ProductsCache(Database.getInstance())),

            App.instance.router,

            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            StoreFragment()
    }

    override fun init() {

        binding.frameLoad.visibility = View.VISIBLE
        val categoryList = presenter.listCategory.categories
        val productList = presenter.listProduct.products

        binding.recyclerView.adapter = mainAdapter
        mainAdapter.submitList(listOf(Category(categoryList, presenter = presenter.listCategory),
            BestSellers(productList, presenter.listProduct)))

    }

    override fun onError(e: Throwable) {

    }

    override fun updateList() {
        binding.frameLoad.visibility = View.GONE
        mainAdapter.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean {
        return presenter.backClicked()
    }
}