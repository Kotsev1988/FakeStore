package com.example.fakestore.ui.fragments.storeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fakestore.App
import com.example.fakestore.databinding.FragmentStoreBinding
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.ui.BackPressedListener
import com.example.fakestore.ui.delegateAdapter.MainAdapter
import com.example.fakestore.ui.delegateAdapter.bestSellers.BestSellers
import com.example.fakestore.ui.delegateAdapter.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.ui.delegateAdapter.categories.Category
import com.example.fakestore.ui.delegateAdapter.categories.CategoryDelegateAdapter
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
            .add(CategoryDelegateAdapter())
            .add(BestSellersDelegateAdapter())
            .build()
    }

    @Inject
    lateinit var productList: IGetProducts

    @Inject
    lateinit var categoryList: IGetCategories

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

    override fun init() {

        binding.frameLoad.visibility = View.VISIBLE
        val categoryList = presenter.listCategory.categories
        val productList = presenter.listProduct.products

        binding.recyclerView.adapter = mainAdapter

        mainAdapter.submitList(listOf(Category(categoryList, presenter = presenter.listCategory),
            BestSellers(productList, presenter.listProduct)))

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