package com.sutrix.user.validation.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sutrix.user.validation.data.api.ApiHelper
import com.sutrix.user.validation.data.model.GetProvinceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProvinsiRepository(private val apiHelper: ApiHelper) {

    val list = MutableLiveData<GetProvinceData>()

    fun getDataList(): MutableLiveData<GetProvinceData> {

        val call = apiHelper.getList()

        call.enqueue(object: Callback<GetProvinceData> {
            override fun onFailure(call: Call<GetProvinceData>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<GetProvinceData>,
                response: Response<GetProvinceData>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()
                list.value = data
            }
        })

        return list
    }

}