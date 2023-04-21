package com.example.fakestore.ui.adapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.App
import com.example.fakestore.databinding.ItemCategoryBinding
import com.example.fakestore.domain.productsEntity.Categories
import com.example.fakestore.ui.adapters.DelegateAdapter
import com.example.fakestore.ui.adapters.DelegateAdapterItem

class CategoryDelegateAdapter() :
    DelegateAdapter<Category, CategoryDelegateAdapter.CategoryViewHolder>(Category::class.java) {

    private var categoryHorizontalAdapter: CategoryHorizontalAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun bindViewHolder(
        model: Category,
        viewHolder: CategoryViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {

        when (val payload = payloads.firstOrNull() as? Category.ChangePayload) {

            is Category.ChangePayload.CategoryNameChanged ->
                viewHolder.bindCategoryChanged(payload.categoryName)

            else -> viewHolder.bind(model)
        }
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Category) {


            categoryHorizontalAdapter = model.presenter?.let {
                CategoryHorizontalAdapter(it).apply {
                    App.instance.appComponent.inject(this)
                }
            }
            binding.recyclerView2.adapter = categoryHorizontalAdapter

        }

        fun bindCategoryChanged(payloads: Categories) {

        }
    }


}