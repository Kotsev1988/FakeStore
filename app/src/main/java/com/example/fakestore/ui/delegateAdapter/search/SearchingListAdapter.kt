package com.example.fakestore.ui.delegateAdapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.SearchResultItemBinding

class SearchingListAdapter(private var presenter: ILIstSearchingResultPresenter) :
    RecyclerView.Adapter<SearchingListAdapter.SearchingResultViewHolder>() {

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