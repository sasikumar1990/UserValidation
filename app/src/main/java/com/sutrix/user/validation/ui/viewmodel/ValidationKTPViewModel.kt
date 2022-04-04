package com.sutrix.user.validation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sutrix.user.validation.data.model.GetProvinceData
import com.sutrix.user.validation.data.repository.ProvinsiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ValidationKTPViewModel(private val repository: ProvinsiRepository) : ViewModel() {

    private val _address = MutableStateFlow("")
    private val _houseType = MutableStateFlow("")
    private val _no = MutableStateFlow("")
    private val _province = MutableStateFlow("")

    var listLiveData: MutableLiveData<GetProvinceData>? = null

    // getting provisni data from API
    fun getDataList() : LiveData<GetProvinceData>? {
        listLiveData = repository.getDataList()
        return listLiveData
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setHouseType(type: String) {
        _houseType.value = type
    }

    fun setNo(no: String) {
        _no.value = no
    }

    fun setProvince(pro: String) {
        _province.value = pro
    }

    // submit button enabled if all filed is true
    val isSubmitEnabled: Flow<Boolean> = combine(_address, _houseType, _no, _province) { address, houseType, no, province ->
        val regexString = "[A-Za-z0-9'\\.\\-\\s\\,]+"
        val isAddressCorrect = address.matches(regexString.toRegex()) && address.length <= 100
        val isHouseTypeCorrect = houseType.isNotEmpty()
        val isNoCorrect =  no.isNotEmpty()
        val isProvince =  province.isNotEmpty()
        return@combine isAddressCorrect and isHouseTypeCorrect and isNoCorrect and isProvince
    }
}