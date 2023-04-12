package com.example.fakestore.ui.delegateAdapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.databinding.ItemCategoryNameBinding

class CategoryHorizontalAdapter(private val presenter: IListCategoryPresenter): RecyclerView.Adapter<CategoryHorizontalAdapter.CategoryHorizontalViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryHorizontalViewHolder {
        return CategoryHorizontalViewHolder(ItemCategoryNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryHorizontalViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class CategoryHorizontalViewHolder(private val binding: ItemCategoryNameBinding): RecyclerView.ViewHolder(binding.root), CategoryItemView{
        override fun clickButton() {
            binding.Phones.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

        override fun setText(text: String) {
            binding.phonetext.text = text
        }

        override fun loadAvatar(url: String) {
            binding.Phones.setImageResource(R.drawable.ic_launcher_foreground)
        }

        override var pos: Int = -1
    }
}