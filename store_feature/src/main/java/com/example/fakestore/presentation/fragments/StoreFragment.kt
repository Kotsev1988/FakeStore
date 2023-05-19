package com.example.fakestore.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.di.DaggerStoreComponent
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.presentation.adapters.MainAdapter
import com.example.fakestore.presentation.adapters.bestSellers.BestSellers
import com.example.fakestore.presentation.adapters.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.presentation.adapters.categories.Category
import com.example.fakestore.presentation.adapters.categories.CategoryDelegateAdapter
import com.example.fakestore.presentation.adapters.header.Header
import com.example.fakestore.presentation.adapters.header.HeaderDelegateAdapter
import com.example.fakestore.presentation.adapters.locationAndFilter.LocationAndFilter
import com.example.fakestore.presentation.adapters.locationAndFilter.LocationAndFilterDelegateAdapter
import com.example.fakestore.presentation.adapters.search.Search
import com.example.fakestore.presentation.adapters.search.SearchDelegateAdapter
import com.example.fakestore.presentation.presenter.StorePresenter
import com.example.fakestore.presentation.view.StoreView
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsItem
import com.example.fakestore.productsEntity.ProductsLike
import com.example.fakestore.productsEntity.Rating
import com.example.fakestore.utils.InjectUtils
import com.example.store_feature.R
import com.example.store_feature.databinding.FragmentStoreBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

const val KAY_PARENT = "key_parent"
const val REQUEST_CODE = 30

class StoreFragment : MvpAppCompatFragment(), StoreView {

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

    private var nav: NavController? = null

    @Inject
    lateinit var productList: IGetProducts

    @Inject
    lateinit var categoryList: IGetCategories

    @Inject
    lateinit var favoriteList: IFavoritesCache


    private val presenter: StorePresenter by moxyPresenter {
        StorePresenter(
            categoryList,
            productList,
            favoriteList,
            AndroidSchedulers.mainThread()
        )
    }

    private var listDelegates =
        listOf(
            LocationAndFilter("", null), headerProduct, Category(Categories(), null),
            Search(arrayListOf(), null),
            headerCategory,
            BestSellers(Products(), ProductsLike(), null)
        )

    var productsFilter = Products()
     private var location : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStoreComponent.factory()
            .create(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .inject(this)
        super.onCreate(savedInstanceState)
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
            StoreFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nav = findNavController()
        binding.recyclerView.adapter = mainAdapter
    }

    override fun init() {

        binding.frameLoad.visibility = View.VISIBLE

        listDelegates = listOf(
            LocationAndFilter("", presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
            headerCategory,
            BestSellers(Products(), ProductsLike(), presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)
        checkPermission()

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

        bottomDialog = BottomFragment.newInstance(presenter.listProduct.products)
        bottomDialog.show(this.childFragmentManager, "tag")

        childFragmentManager.setFragmentResultListener(KAY_PARENT, this) { _, result ->
            presenter.filter(
                result.getString("brand"),
                result.getString("price"),
                result.getString("size")
            )
            bottomDialog.dismiss()
        }
    }

    override fun filter() {

        val filterProductsResult = Products()
        filterProductsResult.addAll(presenter.listProduct.products)

        if (filterProductsResult.size == 0) {
            filterProductsResult.add(ProductsItem("", "", 0, "", 0.0, Rating(0, 0.0), "", 0))
        }



        listDelegates = listOf(
            LocationAndFilter(location, presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
            headerCategory,
            BestSellers(filterProductsResult, ProductsLike(), presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)

    }

    override fun setLocation(city: String) {
        location = city
        println("location")
        mainAdapter.submitList(
            listOf(
                LocationAndFilter(city, presenter.filterClick),
                headerProduct,
                Category(Categories(), presenter.listCategory),
                Search(arrayListOf(), presenter.searchClick),
                headerCategory,
                BestSellers(Products(), ProductsLike(), presenter.listProduct)
            )
        )
    }

    override fun goToProduct(id: Int) {
        val bundle = Bundle()
        bundle.putString("PRODUCT_ID", id.toString())


        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://fakestore.app/productFragment/$id".toUri())
            .build()
        findNavController().navigate(request)


    }

    override fun setProducts(products: Products) {

        listDelegates = listOf(
            LocationAndFilter(location, presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
            headerCategory,
            BestSellers(products, ProductsLike(), presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)

    }

    override fun updateLikesView(productsLikes: ProductsLike) {

        listDelegates = listOf(
            LocationAndFilter(location, presenter.filterClick),
            headerProduct,
            Category(Categories(), presenter.listCategory),
            Search(arrayListOf(), presenter.searchClick),
            headerCategory,
            BestSellers(presenter.listProduct.products, productsLikes, presenter.listProduct)
        )

        mainAdapter.submitList(listDelegates)

    }

    override fun navigateToSearchFragment() {
        findNavController().navigate(R.id.search_navigation)

    }

    private fun checkPermission() {
        requireActivity().let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED -> {
                     presenter.getLocation(requireActivity())
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }

                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermission = 0;
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermission++
                        }
                    }
                    if (grantResults.size == grantedPermission) {
                        // presenter.getLocation(requireActivity())
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
            }
        }
    }

    private fun showDialog(tile: String, message: String) {

        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(tile)
                .setMessage(message)
                .setNegativeButton(R.string.dialog_button_close) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun showRationaleDialog() {
        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(R.string.dialog_rationale_title)
                .setMessage(R.string.dialog_rationale_meaasge)
                .setPositiveButton(it.getString(R.string.dialog_rationale_give_access))
                { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(R.string.dialog_rationale_decline) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}