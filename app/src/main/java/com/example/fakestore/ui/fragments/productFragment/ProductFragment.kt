package com.example.fakestore.ui.fragments.productFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.fakestore.App
import com.example.fakestore.R
import com.example.fakestore.data.room.Database
import com.example.fakestore.databinding.FragmentProductBinding
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.ui.BackPressedListener
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


class ProductFragment : MvpAppCompatFragment(), ProductView, BackPressedListener {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var database: Database
    @Inject
    lateinit var getProduct: IGetProductById


    val presenter: ProductPresenter by moxyPresenter {

        val id = arguments?.getString(PRODUCT_ID)
        ProductPresenter(id, router, AndroidSchedulers.mainThread(),
            getProduct
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

        binding.backfromdetails.setOnClickListener {
            this.backPressed()
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

    override fun backPressed(): Boolean = presenter.backClicked()

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"

        @JvmStatic
        fun newInstance(id: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(PRODUCT_ID, id)
                }
                App.instance.appComponent.inject(this)
            }
    }
}