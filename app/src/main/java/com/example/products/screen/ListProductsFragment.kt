package com.example.products.screen

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.products.R
import com.example.products.databinding.FragmentListProductsBinding
import com.example.products.model.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListProductsFragment : Fragment(R.layout.fragment_list_products) {

    private val viewModel by viewModels<ListProductsViewModel>()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var binding: FragmentListProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("lol", "onCreate usersListFragment")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("lol", "onViewCreated userListFragment")

        binding = FragmentListProductsBinding.bind(view)

        setupAdapter()
        loadData()


    }

    private fun setupAdapter() {

        productAdapter = ProductAdapter()

        binding.list.apply {
            adapter = productAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }

        productAdapter.addLoadStateListener { state: CombinedLoadStates ->
            val refreshSTate = state.refresh
            binding.list.isVisible = refreshSTate != LoadState.Loading
            binding.progress.isVisible = refreshSTate == LoadState.Loading
            if (refreshSTate is LoadState.Error){
                Toast.makeText(context, refreshSTate.error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                d("lol", "loadData: $it")
                productAdapter.submitData(it)
                d("lol", "adapter ${productAdapter.itemCount}")
            }

        }
    }


    /*private fun openDetails(repoName: String){
        d("lol", "nn details - ${findNavController().currentDestination}")
        findNavController().navigate(
            R.id.action_repositoriesListFragment_to_detailsFragment,
            bundleOf(ARG_REPO_NAME to repoName)
        )
    }*/
}