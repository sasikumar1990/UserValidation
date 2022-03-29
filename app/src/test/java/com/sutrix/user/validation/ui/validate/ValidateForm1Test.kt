package com.sutrix.user.validation.ui.validate
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidateForm1Test {

    @Test
    fun `whenInputIsValid return true`(){
        val nationalId = "1234567891234567"
        val name = "Sasikumar"
        val accountNo = "123456789"
        val education = "SD"
        val dob = "29-03-2022"
        val result = ValidateForm1.validateInput(nationalId, name, accountNo, education, dob)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `national id != 16 digit return false `() {
        val nationalId = "1234567891" // it return false
        val name = "Sasikumar"  // it can be empty
        val accountNo = "123456789"
        val education = "SD"
        val dob = "29-03-2022"
        val result = ValidateForm1.validateInput(nationalId, name, accountNo, education, dob)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `name not matches with regex string return false `() {
        val nationalId = "1234567891234567"
        val name = "Sasi123" // it return false
        val accountNo = "123456789"
        val education = "SD"
        val dob = "29-03-2022"
        val result = ValidateForm1.validateInput(nationalId, name, accountNo, education, dob)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `account number less than 8 digits return false`(){
        val nationalId = "1234567891234567"
        val name = "Sasikumar"
        val accountNo = "12345"  // it return false
        val education = "SD"
        val dob = "29-03-2022"
        val result = ValidateForm1.validateInput(nationalId, name, accountNo, education, dob)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `If any filed is empty return false`(){
        val nationalId = "1234567891234567"
        val name = "Sasikumar"
        val accountNo = "12345"
        val education = ""   // it return false
        val dob = ""         // it return false
        val result = ValidateForm1.validateInput(nationalId, name, accountNo, education, dob)
        assertThat(result).isEqualTo(false)
    }
}