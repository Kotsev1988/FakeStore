package com.example.fakestore.presentation.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.SearchResultItemBinding
import com.example.fakestore.presentation.presenter.list.ILIstSearchingResultPresenter
import com.example.fakestore.domain.view.list.ISearchListView

class SearchingListAdapterInFragment(private var presenter: ILIstSearchingResultPresenter) :
    RecyclerView.Adapter<SearchingListAdapterInFragment.SearchingResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchingResultViewHolder =
        SearchingResultViewHolder(SearchResultItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: SearchingResultViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }

    inner class SearchingResultViewHolder(val binding: SearchResultItemBinding) :
        RecyclerView.ViewHolder(binding.root), ISearchListView {

        override fun setText(text: String) {
            binding.searchResult.text = text
        }

        override var pos: Int = -1
    }
}