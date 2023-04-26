package com.example.fakestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.App
import com.example.fakestore.databinding.FragmentSearchingBinding
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.domain.productsEntity.Rating
import com.example.fakestore.presenter.SearchingPresenter
import com.example.fakestore.ui.activity.BackPressedListener
import com.example.fakestore.ui.adapters.MainAdapter
import com.example.fakestore.ui.adapters.search.SearchDelegateAdapterInFragment
import com.example.fakestore.ui.adapters.search.SearchInFragment
import com.example.fakestore.domain.view.SearchingView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class SearchingFragment : MvpAppCompatFragment(), SearchingView, BackPressedListener {

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchingData: IGetAllProducts


    @Inject
    lateinit var router: Router

    val presenter: SearchingPresenter by moxyPresenter {
        SearchingPresenter(searchingData, router, AndroidSchedulers.mainThread())
    }

    private val searchingAdapter by lazy {
        MainAdapter.Builder()
            .add(SearchDelegateAdapterInFragment())
            .build()
    }

    var listDelegateSearch = listOf(
        SearchInFragment(arrayListOf(),  null))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.searchingRecyclerFragment.adapter = searchingAdapter

    }

    override fun init() {

         listDelegateSearch = listOf(
            SearchInFragment(arrayListOf(),  presenter.searchingPresenter))
        searchingAdapter.submitList(listDelegateSearch)

    }

    override fun updateListOnSearching() {

        listDelegateSearch = listOf(
           SearchInFragment(arrayListOf(ProductsItem("", "", 0, "", 0.0, Rating(0,0.0), "" , 0)),  presenter.searchingPresenter),
        )

        searchingAdapter.submitList(listDelegateSearch)
    }

    override fun updateSearchingList() {

        listDelegateSearch = listOf(SearchInFragment(presenter.searchingPresenter.results,  presenter.searchingPresenter))

        searchingAdapter.submitList(listDelegateSearch)
    }

    override fun onError(it: Throwable) {
         TODO("Not yet implemented")
     }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchingFragment().apply {
                App.instance.appComponent.inject(this)
            }
    }

    override fun backPressed(): Boolean = presenter.backClicked()



}