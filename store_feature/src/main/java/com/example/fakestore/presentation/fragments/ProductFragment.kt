package com.example.fakestore.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.navigation.BackPressedListener
import com.example.fakestore.presentation.presenter.ProductPresenter
import com.example.fakestore.presentation.view.ProductView
import com.example.store_feature.R
import com.example.store_feature.databinding.FragmentProductBinding
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


class ProductFragment : MvpAppCompatFragment(), ProductView{

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var router: Router
//
//    @Inject
//    lateinit var getProduct: IGetProductById

//    @Inject
//    lateinit var myCardProducts: IMyCardProducts


    val presenter: ProductPresenter by moxyPresenter {

        val id = arguments?.getString(PRODUCT_ID)
        println("ID PRODUCT "+id)
        ProductPresenter(
//            id, router, AndroidSchedulers.mainThread(),
//            getProduct,
           // myCardProducts
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("ProductFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addToCart.setOnClickListener{
           // presenter.addToCard()
        }

        binding.backfromdetails.setOnClickListener {
           // findNavController().popBackStack(R.id.storeFragment, true)
           // this.backPressed()
        }

    }

    override fun setTitle(text: String) {

        binding.modelName.text = text
    }

    override fun setImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.modelImage)
    }

    override fun setDescription(description: String) {
        binding.description.text = description
    }

    override fun setPrice(price: String) {
        binding.addToCart.text = getString(R.string.add_to_card) + " " + price + "$"
    }


    override fun onError(e: Throwable) {
        Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
    }

    override fun remove() {

       // App.instance.removeProductSubcomponent()
    }

    override fun addedToMyCard() {
        binding.addToCart.visibility = View.GONE
        binding.existInMyCard.visibility = View.VISIBLE
        Toast.makeText(requireActivity(), "Successfully added", Toast.LENGTH_SHORT).show()
    }

    override fun productInMyCard() {
        binding.addToCart.visibility = View.GONE
        binding.existInMyCard.visibility = View.VISIBLE
    }

    override fun turnOnAddButton() {
        binding.addToCart.visibility = View.VISIBLE
        binding.existInMyCard.visibility = View.GONE
    }



    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"

        @JvmStatic
        fun newInstance(id: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(PRODUCT_ID, id)
                }
                 //App.instance.initProductSubcomponent().inject(this)
            }
    }

}