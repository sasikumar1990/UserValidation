package com.sutrix.user.validation.ui.validate

import com.google.common.truth.Truth
import org.junit.Test

class ValidateForm2Test {

    @Test
    fun `whenInputIsValid return true`(){
        val address = "Plot4,Rainbow homes,Madipakkam,Chennai-41"
        val houseType = "Kantor"
        val number = "1234"
        val result = ValidateForm2.validateInput(address, houseType, number)
        Truth.assertThat(result).isEqualTo(true)
    }

    @Test
    fun `address grater than 100 digit return false `() {
        val address = "Plot4,Rainbow homes,Madipakkam,Chennai-41 Plot4,Rainbow homes,Madipakkam,Chennai-41 , Plot4,Rainbow homes,Madipakkam,Chennai-41" // it return false
        val houseType = "Kantor"
        val number = "1234"
        val result = ValidateForm2.validateInput(address, houseType, number)
        Truth.assertThat(result).isEqualTo(false)
    }

    @Test
    fun `if anyone is empty return false `() {
        val address = "Plot4,Rainbow homes,Madipakkam,Chennai-41"
        val houseType = ""   // it return false
        val number = ""      // it return false
        val result = ValidateForm2.validateInput(address, houseType, number)
        Truth.assertThat(result).isEqualTo(false)
    }

}