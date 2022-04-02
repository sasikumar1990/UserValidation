package com.sutrix.user.validation.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.sutrix.user.validation.R.*
import androidx.lifecycle.lifecycleScope
import com.sutrix.user.validation.ui.viewmodel.ValidationKTPViewModel
import kotlinx.android.synthetic.main.activity_form_validation.*
import kotlinx.android.synthetic.main.activity_ktp_form_validation.*
import kotlinx.android.synthetic.main.activity_ktp_form_validation.submit_button
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
       viewModel = ViewModelProviders.of(this).get(ValidationKTPViewModel::class.java)
    }

    private fun initListeners() {
        et_address.addTextChangedListener {
            viewModel.setAddress(it.toString())
            if(it.toString().isNotEmpty() && it.toString().length <= 100)
            {
                tv_error_address.setTextColor(resources.getColor(color.green))

            }else{
                tv_error_address.setTextColor(resources.getColor(color.red))

            }
        }

        et_no.addTextChangedListener {
            viewModel.setNo(it.toString())
            if(it.toString().isNotEmpty())
            {
                tv_error_no.setTextColor(resources.getColor(color.green))

            }else{
                tv_error_no.setTextColor(resources.getColor(color.red))

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
                            tv_error_house.setTextColor(resources.getColor(color.red))
                        }
                        else -> {
                            viewModel.setHouseType(parent?.selectedItem.toString())
                            houseType = parent?.selectedItem.toString()
                            tv_error_house.setTextColor(resources.getColor(color.green))
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
                    submit_button.setBackgroundColor(resources.getColor(color.colorPrimary))
                }else{
                    submit_button.setBackgroundColor(resources.getColor(color.gray))
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
            startActivity(intent)
        }
    }



}
