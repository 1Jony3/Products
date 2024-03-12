package com.example.products.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.products.R
import com.example.products.databinding.ItemProductBinding
import com.example.products.model.data.entities.Product

class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.Holder>(ProductsDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val product = getItem(position) ?: return
        with (holder.binding) {
            titleTV.text = product.id.toString()
            descriptionTV.text = product.description
            loadUserPhoto(productIV, product.thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun loadUserPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.baseline_crop_original_24)
                .error(R.drawable.baseline_crop_original_24)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.baseline_crop_original_24)
                .into(imageView)
        }
    }

    class Holder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}