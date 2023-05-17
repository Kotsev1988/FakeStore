package com.example.fakestore.presentation.presenter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.presentation.adapters.locationAndFilter.FilterClick
import com.example.fakestore.presentation.adapters.search.SearchClick
import com.example.fakestore.presentation.view.StoreView
import com.example.fakestore.productsEntity.Category
import com.example.fakestore.productsEntity.Products
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.io.IOException
import java.util.*

private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class StorePresenter(
    private val categoryList: IGetCategories,
    private val productList: IGetProducts,
    private val favoriteList: IFavoritesCache,
    private val uiScheduler: Scheduler,


    ) : MvpPresenter<StoreView>() {

    val listCategory = CategoryListPresenter()
    var listProduct = ProductsListPresenter()

    val searchClick = SearchClick()

    val filterClick = FilterClick()

    var productsFilter = Products()


    @SuppressLint("CheckResult")
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCategories()
        viewState.init()


        listCategory.itemClickListener = {

            val index = it.pos

            listCategory.categories[index].select = true
            listCategory.categories.indices.forEach {
                if (it != index) {
                    listCategory.categories[it].select = false
                }
            }

            loadProductByClick(listCategory.categories[index].name)
        }

        listProduct.onItemClickListener = {
            val id = listProduct.products[it.pos].id
            viewState.goToProduct(id)
        }

        listProduct.onItemClickLikeListener = {

            favoriteList.insertProductsToDB(listProduct.products[it.pos].id, true)
                .observeOn(uiScheduler).doOnComplete {
                    favoriteList.getProductsFromCache().observeOn(uiScheduler).subscribe(
                        {
                            listProduct.productsLikes.clear()
                            listProduct.productsLikes.addAll(it)
                            viewState.updateLikesView(it)
                        },
                        {
                            viewState.onError(it)
                        }
                    )
                }.subscribe()



        }

        searchClick.listenerFocusChanged = object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {

                    viewState.navigateToSearchFragment()
                    // router.navigateTo(AndroidScreens().search())
                } else {
                    return
                }
            }
        }

        filterClick.itemClickListener = {
            viewState.showBottomDialog()
        }
    }

    private fun loadCategories() {
        categoryList.getCategories().observeOn(uiScheduler).subscribe(

            { categories ->
                listCategory.categories.clear()
                listCategory.categories.addAll(categories as List<Category>)
                viewState.updateList()
                loadProducts(listCategory.categories[0].name)
            },
            {
                viewState.onError(it)
            }
        )
    }

    private fun loadProducts(category: String) {

        productList.getProductByCategory(category).observeOn(uiScheduler).subscribe(

            { products ->
                listProduct.products.clear()
                listProduct.products.addAll(products)
                productsFilter.addAll(products)


                favoriteList.getProductsFromCache().observeOn(uiScheduler).subscribe(
                    {
                        listProduct.productsLikes.addAll(it)
                        viewState.updateList()
                    },
                    {

                    }
                )
            },
            {
                viewState.onError(it)
            }
        )


    }

    private fun loadProductByClick(category: String) {

        productList.getProductByCategory(category).observeOn(uiScheduler).subscribe(

            { products ->
                listProduct.products.clear()
                listProduct.products.addAll(products)
                productsFilter.addAll(products)
                viewState.setProducts(products as Products)
            },
            {
                viewState.onError(it)
            }
        )
    }


    fun filter(brand: String?, price: String?, result: String?) {

        val getInts = Scanner(price).useDelimiter("[^\\d]+")
        val firstPrice: Int = getInts.nextInt()
        val secondPrice: Int = getInts.nextInt()

        if (brand != null) {
            if (price != null) {
                if (result != null) {
                    Observable.fromIterable(productsFilter)
                        .filter {
                            it.title == brand

                                    && it.price >= firstPrice
                                    && it.price <= secondPrice
                            //&& it.rating.rate.toString() == result
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(uiScheduler)
                        .toList()
                        .subscribe({
                            listProduct.products.clear()
                            listProduct.products.addAll(it)
                            viewState.filter()
                        }, {
                            viewState.onError(it)
                        })
                }
            }
        }
    }

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: android.location.Location) {


            // getAddressAsync(App.instance.getAppContext(), location)

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }
    }

    fun getLocation(context: Context) {

        context.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {

                val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
                        as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    val provider =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) /*.getProvider(LocationManager.GPS_PROVIDER)*/
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        //liveDataLocation.value = AppStateLocation.EmptyData("Empty")
                    } else {
                        getAddressAsync(context, location)
                    }
                }
            } else {
                //liveDataLocation.value = AppStateLocation.ShowRationalDialog("show")
            }
        }
    }

    private fun getAddressAsync(context: Context, location: android.location.Location) {

        val geoCoder = Geocoder(context)
        var city = ""

        Completable.fromCallable {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                city = addresses?.get(0)?.getAddressLine(0).toString()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(uiScheduler)
            .subscribe({

                viewState.setLocation(city)

            }, {
                viewState.onError(Throwable("Can't find location"))
            })

        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )

               val city = addresses?.get(0)?.getAddressLine(0)
                println("LOCATION "+city.toString())
                if (city != null) {
                    viewState.setLocation(city)
                    println("LOCATION "+city.toString())
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}