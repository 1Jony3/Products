package com.example.products.screen

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.products.model.data.entities.Product
import com.example.products.model.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@HiltViewModel
class ListProductsViewModel @Inject constructor(repository: Repository) : ViewModel(){

    var listData: Flow<PagingData<Product>>
    private val searchBy = MutableLiveData("")

    init {
        d("lol", "initListFrVM")
        listData = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                repository.getPagedProducts()
            }
            .cachedIn(viewModelScope)

    }

}