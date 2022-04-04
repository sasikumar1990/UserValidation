package com.sutrix.user.validation.data.api

import com.sutrix.user.validation.data.model.GetProvinceData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("daerahindonesia/provinsi")
    fun getList(): Call<GetProvinceData>

}