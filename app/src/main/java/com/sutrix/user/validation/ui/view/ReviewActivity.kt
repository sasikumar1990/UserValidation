package com.sutrix.user.validation.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sutrix.user.validation.R.*
import kotlinx.android.synthetic.main.activity_review_screen.*

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_review_screen)
        setupData()
    }

    private fun setupData() {
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        //set data to textview  from intent
        tv_national_id.text = intent.getStringExtra("nationalId")
        tv_name.text = intent.getStringExtra("fullName")
        tv_account_no.text = intent.getStringExtra("accountNo")
        tv_education.text = intent.getStringExtra("education")
        tv_dob.text = intent.getStringExtra("dob")
        tv_address.text = intent.getStringExtra("address")
        tv_house_type.text = intent.getStringExtra("houseType")
        tv_no.text = intent.getStringExtra("no")

        done.setOnClickListener {
            val snackBar = Snackbar.make(
                it, "Successfully verified",
                Snackbar.LENGTH_LONG
            )
            snackBar.show()
        }

    }
}
