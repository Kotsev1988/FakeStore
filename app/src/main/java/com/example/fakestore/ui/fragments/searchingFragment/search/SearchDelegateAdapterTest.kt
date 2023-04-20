package com.example.fakestore.ui.fragments.searchingFragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ItemSearchingFragBinding
import com.example.fakestore.domain.productsEntity.ProductsItem
import com.example.fakestore.ui.delegateAdapter.DelegateAdapter
import com.example.fakestore.ui.delegateAdapter.DelegateAdapterItem

class SearchDelegateAdapterTest() :
    DelegateAdapter<SearchTest, SearchDelegateAdapterTest.SearchViewHolder>(SearchTest::class.java) {

    var adapter: SearchingListAdapter? = null
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SearchViewHolder(ItemSearchingFragBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun bindViewHolder(
        model: SearchTest,
        viewHolder: SearchViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {


        when (val payload = payloads.firstOrNull() as? SearchTest.ChangePayload) {

            is SearchTest.ChangePayload.SearchingChangedTest->
                viewHolder.bindCategoryChanged(payload.results)

            else -> viewHolder.bind(model)
        }
    }

    inner class SearchViewHolder(
        private val binding: ItemSearchingFragBinding,
    ) : RecyclerView.ViewHolder(binding.root), ISearchView {

        override var pos: Int  = -1
        fun bind(model: SearchTest) {

            adapter = model.presenter?.let { SearchingListAdapter(it) }

            binding.searchingRecyclerResults.adapter = adapter
            binding.searchingResults.setIconifiedByDefault(false)

            binding.searchingResults.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                   model.presenter?.listener?.onQueryTextSubmit(query)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    model.presenter?.listener?.onQueryTextChange(newText)
                    return true
                }
            })

            binding.searchingResults.setOnCloseListener(model.presenter?.listenerClose)

        }

        fun bindCategoryChanged(result: ArrayList<ProductsItem>) {

           println("CategoryChanged "+result.toString())

                adapter?.notifyDataSetChanged()

        }

    }
}