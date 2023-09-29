package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MyClassesActivity : AppCompatActivity(){
    private lateinit var listViewClasses: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_classes)
        listViewClasses = findViewById(R.id.list_class)
        val myList = intent.getSerializableExtra("mylist") as ArrayList<Classes>
        val classListAdapter = ClassList(this@MyClassesActivity, myList)
        listViewClasses.adapter = classListAdapter
        listViewClasses.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val getClass = myList[i]

        }
    }
}