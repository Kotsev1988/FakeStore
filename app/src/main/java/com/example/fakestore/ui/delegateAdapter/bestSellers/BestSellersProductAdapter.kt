package com.example.fakestore.ui.delegateAdapter.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakestore.databinding.ItemProductsBinding

class BestSellersProductAdapter(private val presenter: IListProductPresenter): RecyclerView.Adapter<BestSellersProductAdapter.BestSellersProductViewHolder>() {

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

    inner class BestSellersProductViewHolder(val binding: ItemProductsBinding): RecyclerView.ViewHolder(binding.root), ProductItemView {
        override var pos: Int = -1

        override fun setTitle(title: String) {
            binding.techsNameModel.text = title
        }

        override fun setPrice(price: Int) {
            binding.price.text = price.toString()
        }

        override fun setProductImage(url: String) {

            Glide.with(binding.techImage.context)
                .load(url)
                .into(binding.techImage)
        }
    }

}