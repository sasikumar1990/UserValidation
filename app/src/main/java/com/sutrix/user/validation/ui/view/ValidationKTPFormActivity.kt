package com.sutrix.user.validation.ui.view

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.sutrix.user.validation.R.*
import androidx.lifecycle.lifecycleScope
import com.sutrix.user.validation.data.api.ApiHelper
import com.sutrix.user.validation.data.api.RetrofitBuilder
import com.sutrix.user.validation.data.model.Province
import com.sutrix.user.validation.ui.viewmodel.ValidationKTPViewModel
import com.sutrix.user.validation.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_form_validation.*
import kotlinx.android.synthetic.main.activity_ktp_form_validation.*
import kotlinx.android.synthetic.main.activity_ktp_form_validation.submit_button
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

class ValidationKTPFormActivity : AppCompatActivity() {

    private lateinit var viewModel: ValidationKTPViewModel
    var nationalId: String? = ""
    var fullName: String? = ""
    var accountNo: String? = ""
    var education: String? = ""
    var dob: String? = ""
    var address: String? = ""
    var houseType: String? = ""
    var no: String? = ""
    var province: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_ktp_form_validation)
        getIntentData()
        setupViewModel()
        initListeners()
        initObserver()
    }

    private fun getIntentData() {
        //retrieve data from intent
        nationalId = intent.getStringExtra("nationalId")
        fullName = intent.getStringExtra("fullName")
        accountNo = intent.getStringExtra("accountNo")
        education = intent.getStringExtra("education")
        dob = intent.getStringExtra("dob")
    }

    private fun setupViewModel() {
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ValidationKTPViewModel::class.java)
    }

    // retrieved province data
    private fun retrieveList(data: List<Province>) {

        val spinnerArray: ArrayList<String> = ArrayList()
        spinnerArray.add("Select Your Province")

        data.forEach {
            spinnerArray.add(it.nama)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            applicationContext,
            R.layout.simple_spinner_dropdown_item,
            spinnerArray
        )
        spinner_province.adapter = adapter

    }

    private fun initListeners() {

        viewModel.getDataList()?.observe(this, {
            progressBar.visibility = View.GONE
            retrieveList(it.provinsi)
        })

        et_address.addTextChangedListener {
            viewModel.setAddress(it.toString())
            if(it.toString().isNotEmpty() && it.toString().length <= 100)
            {
                tv_error_address.setTextColor(ContextCompat.getColor(applicationContext,color.green))

            }else{
                tv_error_address.setTextColor(ContextCompat.getColor(applicationContext,color.red))

            }
        }

        et_no.addTextChangedListener {
            viewModel.setNo(it.toString())
            if(it.toString().isNotEmpty())
            {
                tv_error_no.setTextColor(ContextCompat.getColor(applicationContext,color.green))

            }else{
                tv_error_no.setTextColor(ContextCompat.getColor(applicationContext,color.red))

            }
        }

        spinner_house_type.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    when (position) {
                        0 -> {
                            viewModel.setHouseType("")
                            tv_error_house.setTextColor(ContextCompat.getColor(applicationContext,color.red))
                        }
                        else -> {
                            viewModel.setHouseType(parent?.selectedItem.toString())
                            houseType = parent?.selectedItem.toString()
                            tv_error_house.setTextColor(ContextCompat.getColor(applicationContext,color.green))
                        }
                    }
                }
            }

        spinner_province.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    when (position) {
                        0 -> {
                            viewModel.setProvince("")
                            tv_error_province.setTextColor(ContextCompat.getColor(applicationContext,color.red))
                        }
                        else -> {
                            viewModel.setProvince(parent?.selectedItem.toString())
                            province = parent?.selectedItem.toString()
                            tv_error_province.setTextColor(ContextCompat.getColor(applicationContext,color.green))
                        }
                    }
                }
            }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                submit_button.isEnabled = value   // button will enabled if all validation is true
               if(value) {
                    submit_button.setBackgroundColor(ContextCompat.getColor(applicationContext,color.colorPrimary))
                }else{
                    submit_button.setBackgroundColor(ContextCompat.getColor(applicationContext,color.gray))
               }
            }
        }
        submit_button.setOnClickListener {
            address = et_address.text.toString().trim()
            no = et_no.text.toString().trim()
            //send data via intent
            var intent = Intent(this@ValidationKTPFormActivity, ReviewActivity::class.java)
            intent.putExtra("nationalId",nationalId)
            intent.putExtra("fullName",fullName)
            intent.putExtra("accountNo",accountNo)
            intent.putExtra("education",education)
            intent.putExtra("dob",dob)
            intent.putExtra("address",address)
            intent.putExtra("houseType",houseType)
            intent.putExtra("no",no)
            intent.putExtra("province",province)
            startActivity(intent)
        }
    }
}
