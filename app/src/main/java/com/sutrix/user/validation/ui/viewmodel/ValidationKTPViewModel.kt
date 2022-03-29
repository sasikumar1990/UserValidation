package com.sutrix.user.validation.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ValidationKTPViewModel : ViewModel() {

    private val _address = MutableStateFlow("")
    private val _houseType = MutableStateFlow("")
    private val _no = MutableStateFlow("")


    fun setAddress(address: String) {
        _address.value = address
    }

    fun setHouseType(type: String) {
        _houseType.value = type
    }

    fun setNo(no: String) {
        _no.value = no
    }

    // submit button enabled if all filed is true
    val isSubmitEnabled: Flow<Boolean> = combine(_address, _houseType, _no) { address, houseType, no ->
        val regexString = "[A-Za-z0-9'\\.\\-\\s\\,]+"
        val isAddressCorrect = address.matches(regexString.toRegex()) && address.length <= 100
        val isHouseTypeCorrect = houseType.isNotEmpty()
        val isNoCorrect =  no.isNotEmpty()
        return@combine isAddressCorrect and isHouseTypeCorrect and isNoCorrect
    }
}