package com.example.myapplication

import android.app.*
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class LoadClassActivity: Activity() {

    private var mUniversityText: AutoCompleteTextView? = null
    private var mSchoolDepartmentText: AutoCompleteTextView? = null
    private var mClassNameText: EditText? = null
    private var textViewData: TextView? = null
    private var db : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef : DatabaseReference? = db!!.getReference("Notebooks")
    internal lateinit var getClasses: MutableList<Classes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_class)
        mUniversityText = findViewById<View>(R.id.autocompleteUni2) as AutoCompleteTextView
        mSchoolDepartmentText = findViewById<View>(R.id.autocompleteDep2) as AutoCompleteTextView
        mClassNameText = findViewById<View>(R.id.title2) as EditText
        getClasses = ArrayList()
        val unis = resources.getStringArray(R.array.university)
        val uniAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unis)
        mUniversityText!!.setAdapter(uniAdapter)

        val depart = resources.getStringArray(R.array.departments)
        val departAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, depart)
        mSchoolDepartmentText!!.setAdapter(departAdapter)

        val cancelButton = findViewById<View>(R.id.cancelButton2) as Button
        cancelButton.setOnClickListener {
            Log.i(ContentValues.TAG, "Entered cancelButton.OnClickListener.onClick()")

            // TODO - Indicate result and finish
            setResult(Activity.RESULT_CANCELED);
            finish();
        }

        // TODO - Set up OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton2) as Button
        resetButton.setOnClickListener {
            Log.i(ContentValues.TAG, "Entered resetButton.OnClickListener.onClick()")
            mClassNameText!!.setText("")
            mSchoolDepartmentText!!.setText("")
            mUniversityText!!.setText("")
        }

        // Set up OnClickListener for the Submit Button
        val searchButton = findViewById<View>(R.id.searchButton) as Button
        searchButton.setOnClickListener {
            Log.i(ContentValues.TAG, "Entered searchButton.OnClickListener.onClick()")
            // TODO - gather ToDoItem data
            val classString: String = mClassNameText?.text.toString()
            val universityString: String = mUniversityText?.text.toString()
            val departmentString: String = mSchoolDepartmentText?.text.toString()
            notebookRef!!.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    getClasses.clear()
                    var single: Classes? = null
                    Log.e("SLackr", dataSnapshot.toString())
                    for(postSnap in dataSnapshot.children)
                    {
                        try {
                            single = postSnap.getValue(Classes::class.java)
                            Log.e("SLackr", single.toString() )
                            var group = String()
                            when(single!!.groupSize)
                            {
                                Classes.GroupSize.TWO ->
                                {
                                    group = 2.toString()
                                }
                                Classes.GroupSize.THREE ->
                                {
                                    group = 3.toString()
                                }
                                Classes.GroupSize.FOUR ->
                                {
                                    group = 4.toString()
                                }
                                Classes.GroupSize.FIVEORMORE ->
                                {
                                    group = 10.toString()
                                }
                            }
                            if(single!!.className == classString && single!!.university == universityString && single!!.schoolDepartment == departmentString && single!!.numStudents.toInt() < group.toInt() ){
                                getClasses.add(single!!)
                            }
                        }
                        catch (e: Exception){
                            Log.e(TAG, e.toString())
                        }
                    }
                    Log.e("getC",getClasses.toString())
                    if(getClasses.size > 0)
                    {
                        val intent = Intent(this@LoadClassActivity, ListClassActivity::class.java)
                        intent.putExtra("mylist", getClasses as ArrayList<Classes>)
                        startActivity(intent)
                    }
                    else{
                        val toast = Toast.makeText(applicationContext, "There are no Study Groups that match your Criteria", Toast.LENGTH_LONG)
                        toast.show()
                    }

                }

                override fun onCancelled(databaseError : DatabaseError) {
                    Log.e("cancel", databaseError.toString())
                }

            })
            finish()
        }

    }
}