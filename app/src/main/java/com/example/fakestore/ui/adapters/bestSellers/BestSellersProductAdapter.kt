package com.example.fakestore.ui.adapters.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ItemProductsBinding
import com.example.fakestore.presenter.list.IListProductPresenter
import com.example.fakestore.domain.image.ILoadImage
import com.example.fakestore.domain.view.list.ProductItemView
import javax.inject.Inject

class BestSellersProductAdapter(private val presenter: IListProductPresenter): RecyclerView.Adapter<BestSellersProductAdapter.BestSellersProductViewHolder>() {


    @Inject
    lateinit var imageLoader: ILoadImage<ImageView>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BestSellersProductViewHolder {
        return BestSellersProductViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener {
                presenter.onItemClickListener?.invoke(this)
            }
        }
    }

    override fun onBindViewHolder(holder: BestSellersProductViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int  = presenter.getCount()

    inner class BestSellersProductViewHolder(val binding: ItemProductsBinding): RecyclerView.ViewHolder(binding.root),
        ProductItemView {
        override var pos: Int = -1

        override fun setTitle(title: String) {
            binding.techsNameModel.text = title
        }

        override fun setPrice(price: Int) {
            binding.price.text = price.toString()
        }

        override fun setProductImage(url: String) {

            imageLoader.loadImage(url, binding.techImage)
//            Glide.with(binding.techImage.context)
//                .load(url)
//                .into(binding.techImage)
        }
    }

}