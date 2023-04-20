package com.example.fakestore.ui.delegateAdapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.SearchingItemBinding
import com.example.fakestore.ui.delegateAdapter.DelegateAdapter
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.fragments.searchingFragment.search.SearchingListAdapter

class SearchDelegateAdapter(private val presenter: IListSearchClick) :
    DelegateAdapter<Search, SearchDelegateAdapter.SearchViewHolder>(Search::class.java) {

    var adapter: SearchingListAdapter? = null
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SearchViewHolder(SearchingItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)).apply {
                itemView.setOnClickListener {
                    presenter.itemClickListener?.invoke(this)
                }
        }
    }

    override fun bindViewHolder(
        model: Search,
        viewHolder: SearchViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {

        viewHolder.bind(model)

//        when (val payload = payloads.firstOrNull() as? SearchTest.ChangePayload) {
//
//            is SearchTest.ChangePayload.SearchingChanged->
//                viewHolder.bindCategoryChanged(payload.results)
//
//            else -> viewHolder.bind(model)
//        }
    }

    inner class SearchViewHolder(
        private val binding: SearchingItemBinding
    ) : RecyclerView.ViewHolder(binding.root), IClickView{

        fun bind(model: Search) {
            println("StoreFragment "+model.results.toString())

            binding.searching.setOnQueryTextFocusChangeListener(presenter.listenerFocusChanged)
        }
    }
}