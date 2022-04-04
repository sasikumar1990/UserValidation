package com.sutrix.user.validation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sutrix.user.validation.data.api.ApiHelper
import com.sutrix.user.validation.data.repository.ProvinsiRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ValidationKTPViewModel::class.java)) {
            return ValidationKTPViewModel(ProvinsiRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

