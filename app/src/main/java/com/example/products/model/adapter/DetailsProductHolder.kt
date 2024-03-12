package com.example.products.model.adapter

import com.bumptech.glide.Glide
import com.example.products.R
import com.example.products.databinding.FragmentDetailsProductBinding
import com.example.products.model.data.entities.Product


class DetailsProductHolder(private val binding: FragmentDetailsProductBinding) {

    fun bind(product: Product) {
        with(binding){
            categoryTV.text =  product.category
            titleTV.text = "★ ${product.brand} ${product.title}"
            priceTV.text = product.price.toString()
            descriptionTV.text = product.description
            if (product.thumbnail.isNotBlank()) {
                Glide.with(productIV.context)
                    .load(product.thumbnail)
                    .placeholder(R.drawable.baseline_crop_original_24)
                    .error(R.drawable.baseline_crop_original_24)
                    .into(productIV)
            } else {
                Glide.with(productIV.context)
                    .load(R.drawable.baseline_crop_original_24)
                    .into(productIV)
            }
        }
    }
}