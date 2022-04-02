package com.sutrix.user.validation.ui.view

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.sutrix.user.validation.R.*
import androidx.lifecycle.lifecycleScope
import com.sutrix.user.validation.ui.viewmodel.ValidationViewModel
import kotlinx.android.synthetic.main.activity_form_validation.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ValidationFormActivity : AppCompatActivity() {

    private lateinit var viewModel: ValidationViewModel
    private var cal = Calendar.getInstance()

    var nationalId: String? = ""
    var fullName: String? = ""
    var accountNo: String? = ""
    var education: String? = ""
    var dob: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_form_validation)
        setupViewModel()
        initListeners()
        initObserver()
    }

    private fun setupViewModel() {
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
       viewModel = ViewModelProviders.of(this).get(ValidationViewModel::class.java)
    }

    private fun initListeners() {
        et_national_id.addTextChangedListener {
            viewModel.setNationalId(it.toString())
            if(it.toString().isNotEmpty() && it.toString().length == 16)
            {
                tv_national_error.setTextColor(resources.getColor(color.green))
            }else{
                tv_national_error.setTextColor(resources.getColor(color.red))
            }

        }
        et_full_name.addTextChangedListener {
            viewModel.setFullName(it.toString())
        }
        et_account_no.addTextChangedListener {
            viewModel.setAccountNo(it.toString())
            if(it.toString().isNotEmpty() && it.toString().length >= 8)
            {
                tv_account_error.setTextColor(resources.getColor(color.green))
            }else{
                tv_account_error.setTextColor(resources.getColor(color.red))
            }
        }

        spinner_education.onItemSelectedListener =
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
                            viewModel.setEducation("")
                            tv_filed.setTextColor(resources.getColor(color.red))

                        }
                        else -> {
                            viewModel.setEducation(parent?.selectedItem.toString())
                            education = parent?.selectedItem.toString()
                            tv_filed.setTextColor(resources.getColor(color.green))

                        }
                    }
                }
            }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                et_date_of_birth.setText(sdf.format(cal.time).toString())
                viewModel.setDOB(sdf.format(cal.time).toString())
                tv_dob_error.setTextColor(resources.getColor(color.green))
            }

        et_date_of_birth.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
               submit_button.isEnabled = value  // submit button will enabled if all validation is true
                if(value) {
                    submit_button.setBackgroundColor(resources.getColor(color.colorPrimary))
                }else{
                    submit_button.setBackgroundColor(resources.getColor(color.gray))
                }
            }
        }
        submit_button.setOnClickListener {
            nationalId = et_national_id.text.toString().trim()
            fullName = et_full_name.text.toString().trim()
            accountNo = et_account_no.text.toString().trim()
            dob = et_date_of_birth.text.toString().trim()
            //send data via intent
            var intent = Intent(this@ValidationFormActivity, ValidationKTPFormActivity::class.java)
            intent.putExtra("nationalId",nationalId)
            intent.putExtra("fullName",fullName)
            intent.putExtra("accountNo",accountNo)
            intent.putExtra("education",education)
            intent.putExtra("dob",dob)
            startActivity(intent)
        }
    }

}
