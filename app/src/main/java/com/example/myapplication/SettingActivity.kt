package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth


class SettingActivity :  Activity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signOut()
        finish()
        startActivity(Intent(this, MainActivity::class.java))

    }
}