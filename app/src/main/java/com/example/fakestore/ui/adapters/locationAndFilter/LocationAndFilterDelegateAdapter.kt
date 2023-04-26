package com.example.fakestore.ui.adapters.locationAndFilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.LocationAndFilterBinding
import com.example.fakestore.domain.view.list.IFilterClickView
import com.example.fakestore.ui.adapters.DelegateAdapter
import com.example.fakestore.ui.adapters.DelegateAdapterItem

class LocationAndFilterDelegateAdapter() :
    DelegateAdapter<LocationAndFilter, LocationAndFilterDelegateAdapter.LocationAndFilterViewHolder>(
        LocationAndFilter::class.java) {


    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LocationAndFilterViewHolder(LocationAndFilterBinding.inflate(LayoutInflater.from(parent.context),
    parent,
    false))

    override fun bindViewHolder(
        model: LocationAndFilter,
        viewHolder: LocationAndFilterViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.bind(model)
    }

    inner class LocationAndFilterViewHolder(private val binding: LocationAndFilterBinding) :
        RecyclerView.ViewHolder(binding.root), IFilterClickView {
        fun bind(model: LocationAndFilter) {

            binding.filter.setOnClickListener(model.presenter?.itemClickListener)
        }
    }
}