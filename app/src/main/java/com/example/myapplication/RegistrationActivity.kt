package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private var firstName :EditText? = null
    private var lastName : EditText? = null
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()
    private var universityTV : AutoCompleteTextView? = null
    private var user : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var userRef : DatabaseReference? = user!!.getReference("Users")
    private var dataUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser



    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)
        regBtn!!.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val first :String = firstName!!.text.toString()
        val last : String = lastName!!.text.toString()
        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()

        if(!validator.validName(first))
        {
            Toast.makeText(applicationContext, "Please enter your First Name!", Toast.LENGTH_LONG).show()
            return
        }
        if(!validator.validName(last))
        {
            Toast.makeText(applicationContext, "Please enter your Last Name!", Toast.LENGTH_LONG).show()
            return
        }
        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email...", Toast.LENGTH_LONG).show()
            return
        }
        if (!validator.validPassword(password)) {
            Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                    var newUser: User = User(email, first, last)
                    userRef!!.child(dataUser!!.uid).setValue(newUser)

                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE
                }
            }
    }
}
