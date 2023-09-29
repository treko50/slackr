package com.example.myapplication

import java.io.Serializable

class User: Serializable {


    var email = String()
    var fName = String()
    var lName = String()


    internal constructor(email: String, fName: String, lName: String)
    {
        this.email = email
        this.fName = fName
        this.lName = lName
    }
    internal constructor()
    {

    }
}