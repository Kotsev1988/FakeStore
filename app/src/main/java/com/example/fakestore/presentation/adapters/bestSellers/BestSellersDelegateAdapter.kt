package com.example.fakestore.presentation.adapters.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.App
import com.example.fakestore.databinding.ItemBestSellersBinding
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem

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

    inner class BestSellersViewHolder(private val binding: ItemBestSellersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: BestSellers) {

            bestSellersProductAdapter = model.presenter?.let {
                BestSellersProductAdapter(it).apply {
                    App.instance.appComponent.inject(this)
                }
            }
            binding.hotsalesRecycle.adapter = bestSellersProductAdapter
        }
    }
}