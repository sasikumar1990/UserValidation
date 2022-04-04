package com.sutrix.user.validation.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getList() = apiService.getList()
}