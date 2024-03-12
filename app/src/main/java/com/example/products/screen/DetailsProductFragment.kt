package com.example.products.screen

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.products.R
import com.example.products.databinding.FragmentDetailsProductBinding
import com.example.products.model.adapter.DetailsProductHolder
import com.example.products.screen.ListProductsFragment.Companion.ARG_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsProductFragment : Fragment(R.layout.fragment_details_product) {


    private lateinit var binding: FragmentDetailsProductBinding
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsProductBinding.inflate(inflater, container, false)
        val idProduct = requireArguments().getInt(ARG_ID)
        viewModel.getProduct(idProduct)
        val holder = DetailsProductHolder(binding)

        viewModel.state.observe(viewLifecycleOwner){
            binding.progressBar.visibility =
                if (it == DetailsViewModel.State.Loading) View.VISIBLE else View.GONE

            binding.containerLL.visibility =
                if (it is DetailsViewModel.State.Loaded) View.VISIBLE else View.GONE

            if (it is DetailsViewModel.State.Error)
                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
            if (it is DetailsViewModel.State.Loaded) {
                holder.bind(it.product)
            }
        }
        return binding.root
    }
}
