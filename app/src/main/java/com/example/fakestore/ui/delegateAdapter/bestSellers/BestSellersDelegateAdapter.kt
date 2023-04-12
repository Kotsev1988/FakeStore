package com.example.fakestore.ui.delegateAdapter.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ItemBestSellersBinding
import com.example.fakestore.ui.delegateAdapter.DelegateAdapter
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.delegateAdapter.categories.CategoryHorizontalAdapter

class BestSellersDelegateAdapter :
    DelegateAdapter<BestSellers, BestSellersDelegateAdapter.BestSellersViewHolder>(BestSellers::class.java) {

    private var bestSellersProductAdapter: BestSellersProductAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        BestSellersViewHolder(ItemBestSellersBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun bindViewHolder(
        model: BestSellers,
        viewHolder: BestSellersViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.bind(model)
    }

    inner class BestSellersViewHolder(val binding: ItemBestSellersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: BestSellers) {

            bestSellersProductAdapter = BestSellersProductAdapter(model.presenter)
            binding.hotsalesRecycle.adapter = bestSellersProductAdapter
        }
    }
}