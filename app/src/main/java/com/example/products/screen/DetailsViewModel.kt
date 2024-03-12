package com.example.products.screen

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import com.example.products.model.data.entities.Product
import com.example.products.model.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Loaded(val product: Product) : State
    }

    fun getProduct(id: Int) {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            flow {
                emit(repository.getProduct(id.toString()))
            }.flowOn(Dispatchers.IO).catch { e ->
                d("lol", " error ${e.printStackTrace()}")
                _state.postValue(State.Error(e.message.toString()))
                e.printStackTrace()
            }.collect {
                d("lol", "$it")
                _product.value = it
                _state.postValue(State.Loaded(it))
            }
        }
    }
}