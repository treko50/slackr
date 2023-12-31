package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity


class Validators : AppCompatActivity() {
    fun validEmail(email: String?) : Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }

        // General Email Regex (RFC 5322 Official Standard)
        val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
                "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x" +
                "5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z" +
                "0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4" +
                "][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z" +
                "0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
        return emailRegex.matches(email)
    }

    // TODO: Validate password
    // Passwords should be at least 4 characters with 1 letter and 1 number
    fun validPassword(password: String?) : Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }
        val passwordRegex = Regex("^.{4,8}\$")
        return passwordRegex.matches(password)

    }
    fun validName(name: String?) : Boolean {
        if (name.isNullOrEmpty()) {
            return false
        }
        return true
    }
    fun validUniversity(uni: String?) : Boolean {
        return if (uni.isNullOrEmpty()) {
            false
        } else resources.getStringArray(R.array.university).contains(uni)
    }

    fun validDepartment(depart: String?) : Boolean {
        if (depart.isNullOrEmpty() || !resources.getStringArray(R.array.departments).contains(depart)) {
            return false
        }
        return true
    }
}