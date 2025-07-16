package com.example.kotlindsaandcoroutines.coroutine_scopes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


//viewModel scope bound to viewModel lifetime
// and ViewModel lifetime will end when screen that is bound to
// is pop from back stack
class MainViewModel : ViewModel(){
    init {
        viewModelScope.launch {

        }
    }
}