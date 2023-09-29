package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ClassDetailsActivity: AppCompatActivity() {
    private lateinit var className: TextView
    private lateinit var depName: TextView
    private lateinit var uniName: TextView
    internal lateinit var section: TextView
    private lateinit var members: TextView
    private lateinit var totalMem: TextView
    private lateinit var joinClass: Button
    private var db : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef : DatabaseReference? = db!!.getReference("Notebooks")
    private var dataUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var usersDB : DatabaseReference? = db!!.getReference("Users")

    private lateinit var listViewClasses: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.class_details)
        val singleClass = intent.getSerializableExtra("singleClass") as Classes
        className = findViewById(R.id.display_name)
        depName = findViewById(R.id.display_dep)
        uniName = findViewById(R.id.display_uni)
        section = findViewById(R.id.display_section)
        totalMem = findViewById(R.id.display_size)
        joinClass = findViewById(R.id.join_class)
        val two = singleClass.numStudents.toString() + "/" + 2.toString()
        val three = singleClass.numStudents.toString() + "/" + 3.toString()
        val four = singleClass.numStudents.toString() + "/" + 4.toString()
        val five = singleClass.numStudents.toString() + "/" + 10.toString()

        when(singleClass.groupSize){
            Classes.GroupSize.TWO ->
                totalMem.text = two
            Classes.GroupSize.THREE ->
                totalMem.text = three
            Classes.GroupSize.FOUR ->
                totalMem.text = four
            Classes.GroupSize.FIVEORMORE ->
                totalMem.text = five
        }
        className.text = singleClass.className
        depName.text = singleClass.schoolDepartment
        uniName.text = singleClass.university
        section.text = singleClass.classSection

       joinClass.setOnClickListener{
           singleClass.numStudents = singleClass.numStudents+1
           usersDB!!.child(dataUser!!.uid).child("joinedClasses").push().setValue(singleClass)
           finish()
       }


    }
}