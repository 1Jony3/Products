package com.example.products.screen

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.products.R
import com.example.products.databinding.FragmentListProductsBinding
import com.example.products.model.adapter.DefaultLoadStateAdapter
import com.example.products.model.adapter.OnClickProductListener
import com.example.products.model.adapter.ProductAdapter
import com.example.products.model.adapter.TryAgainAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListProductsFragment : Fragment(R.layout.fragment_list_products) {

    private val viewModel by viewModels<ListProductsViewModel>()
    private lateinit var adapter: ProductAdapter
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder
    private lateinit var binding: FragmentListProductsBinding

    companion object{
        const val ARG_RATING = "rating"
        const val ARG_ID = "id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListProductsBinding.bind(view)
        setupAdapter(view)
        setupSearchInput()
        setupSwipeToRefresh()
    }

    private fun setupAdapter(view: View) {

        adapter = ProductAdapter(object : OnClickProductListener {
            override fun onClick(rating: String, id: Int) {
                openDetails("★$rating", id)
            }
        })

        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.list.apply {
            adapter = adapterWithLoadState
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )
        observeLoadState()
        observeProducts()
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                adapter.submitData(it)
            }

        }
    }
    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener {
            viewModel.setSearchBy(it.toString())
        }
    }
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
    private fun openDetails(rating: String, id: Int){
        d("lol", "nn details - ${findNavController().currentDestination}")
        findNavController().navigate(
            R.id.action_listProductsFragment_to_detailsProductFragment,
            bundleOf(ARG_RATING to rating, ARG_ID to id))
    }
}