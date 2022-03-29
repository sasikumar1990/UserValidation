package com.sutrix.user.validation.ui.validate

object ValidateForm2 {
    fun validateInput(address: String, houseType: String, no: String): Boolean {
        val regexString = "[A-Za-z0-9'\\.\\-\\s\\,]+"

        if (address.isEmpty() || houseType.isEmpty() || no.isEmpty()) {
            return false
        }
        if (!address.matches(regexString.toRegex())) {
            return false
        }

        if (address.length >= 100) {
            return false
        }

        return true
    }
}