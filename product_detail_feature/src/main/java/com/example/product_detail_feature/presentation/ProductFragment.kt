package com.example.product_detail_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.utils.InjectUtils
import com.example.product_detail_feature.R
import com.example.product_detail_feature.databinding.FragmentProductBinding
import com.example.product_detail_feature.di.DaggerProductComponent
import com.example.product_detail_feature.presentation.view.ProductView
import com.example.product_detail_feature.presenter.ProductPresenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class ProductFragment : MvpAppCompatFragment(), ProductView {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var getProduct: IGetProductById

    @Inject
    lateinit var myCardProducts: IMyCardProducts


    private val presenter: ProductPresenter by moxyPresenter {

        val id = arguments?.let {
            it.getString("metadataFileSyncFilter")

        }
        ProductPresenter(
            id,
            AndroidSchedulers.mainThread(),
            getProduct,
            myCardProducts
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerProductComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .build().inject(this)

        super.onCreate(savedInstanceState)

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
            presenter.addToCard()
        }

        binding.backfromdetails.setOnClickListener {
            //findNavController().popBackStack()
            Navigation.findNavController(it).popBackStack()
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

            }
    }

}