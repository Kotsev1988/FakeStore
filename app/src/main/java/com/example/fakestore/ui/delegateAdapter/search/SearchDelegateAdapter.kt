package com.example.fakestore.ui.delegateAdapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.App
import com.example.fakestore.databinding.SearchingItemBinding
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.DelegateAdapter
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem
import com.example.fakestore.ui.delegateAdapter.categories.Category
import com.example.fakestore.ui.delegateAdapter.categories.IListCategoryPresenter

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
       // viewHolder.bind(model)

        when (val payload = payloads.firstOrNull() as? Search.ChangePayload) {

            is Search.ChangePayload.SearchingChanged->
                viewHolder.bindCategoryChanged(payload.results)

            else -> viewHolder.bind(model)
        }
    }

    inner class SearchViewHolder(
        private val binding: SearchingItemBinding
    ) : RecyclerView.ViewHolder(binding.root), iclickview{
        override var pos: Int =-1

        fun bind(model: Search) {

            adapter = model.presenter?.let { SearchingListAdapter(it) }

            binding.searchingRecycler.adapter = adapter


            binding.searching.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                   model.presenter?.listener?.onQueryTextSubmit(query)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    model.presenter?.listener?.onQueryTextChange(newText)
                    return true
                }

            })

            binding.searching.setOnCloseListener(model.presenter?.listenerClose)

            binding.searching.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

        fun bindCategoryChanged(result: ArrayList<ProductsItem>) {

            if (result.isNotEmpty()){
                adapter?.notifyDataSetChanged()
            }
        }

    }
}