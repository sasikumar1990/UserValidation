package com.sutrix.user.validation.ui.validate

object ValidateForm1 {
    fun validateInput(nationalId: String, name: String, accountId: String, education: String, dob: String): Boolean {
        val regexString = "[a-zA-Z]+"

        if(nationalId.isEmpty() || accountId.isEmpty() || education.isEmpty() || dob.isEmpty()){
            return false
        }

        if(name.isNotEmpty()) {
            if (!name.matches(regexString.toRegex())) {
                return false
            }
        }
        if(nationalId.length != 16){
            return false
        }
        if(accountId.length < 8){
            return false
        }
        return true
    }
}