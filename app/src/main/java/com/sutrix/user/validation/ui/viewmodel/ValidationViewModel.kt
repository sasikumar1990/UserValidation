package com.sutrix.user.validation.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ValidationViewModel : ViewModel() {

    private val _nationalId = MutableStateFlow("")
    private val _fullName = MutableStateFlow("")
    private val _accountNo = MutableStateFlow("")
    private val _dob = MutableStateFlow("")
    private val _education = MutableStateFlow("")

    fun setNationalId(id: String) {
        _nationalId.value = id
    }

    fun setFullName(name: String) {
        _fullName.value = name
    }

    fun setAccountNo(account: String) {
        _accountNo.value = account
    }

    fun setEducation(edu: String) {
        _education.value = edu
    }

    fun setDOB(dob: String) {
        _dob.value = dob
    }

    // submit button enabled if all filed is true
    val isSubmitEnabled: Flow<Boolean> = combine(_nationalId, _fullName, _accountNo, _education, _dob) { nationalId, fullName, accountNo, education, dob ->
        val regexString = "[a-zA-Z]+"
        val isNationalIdCorrect = nationalId.length == 16
        val isNameCorrect = fullName.matches(regexString.toRegex()) || fullName.isEmpty()
        val isAccountNoCorrect = accountNo.length >= 8
        val isEducationCorrect = education.isNotEmpty()
        val isDobCorrect = dob.isNotEmpty()
        return@combine isNationalIdCorrect and isNameCorrect and isAccountNoCorrect and isEducationCorrect and isDobCorrect
    }

}