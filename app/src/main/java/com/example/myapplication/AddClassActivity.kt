package com.example.myapplication

import android.app.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Classes.GroupSize
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.ref.Reference
import java.util.*
import kotlin.collections.ArrayList

class AddClassActivity: Activity() {

    private var mGroupSizeRadioGroup: RadioGroup? = null
    private var mUniversityText: AutoCompleteTextView? = null
    private var mClassSectionText: EditText? = null
    private var mSchoolDepartmentText: AutoCompleteTextView? = null
    private var mClassNameText: EditText? = null
    private var mDefaultGroupButton: RadioButton? = null
    private var db : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef : DatabaseReference? = db!!.getReference("Notebooks")
    private var dataUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var usersDB :DatabaseReference? = db!!.getReference("Users")

    private val groupSize : GroupSize
        get() {
            return when (mGroupSizeRadioGroup!!.checkedRadioButtonId) {
                R.id.two -> {
                    GroupSize.TWO
                }
                R.id.three -> {
                    GroupSize.THREE
                }
                R.id.four -> {
                    GroupSize.FOUR
                }
                else ->{
                    GroupSize.FIVEORMORE
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_class)
        mGroupSizeRadioGroup = findViewById<View>(R.id.memberGroup) as RadioGroup
        mUniversityText = findViewById<View>(R.id.autocompleteUni) as AutoCompleteTextView
        mClassSectionText = findViewById<View>(R.id.classSection) as EditText
        mSchoolDepartmentText = findViewById<View>(R.id.autocompleteDep) as AutoCompleteTextView
        mClassNameText = findViewById<View>(R.id.title) as EditText
        mDefaultGroupButton= findViewById<View>(R.id.two) as RadioButton

        val unis = resources.getStringArray(R.array.university)
        val uniAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unis)
        mUniversityText!!.setAdapter(uniAdapter)

        val depart = resources.getStringArray(R.array.departments)
        val departAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, depart)
        mSchoolDepartmentText!!.setAdapter(departAdapter)


        val cancelButton = findViewById<View>(R.id.cancelButton) as Button
        cancelButton.setOnClickListener { Log.i(TAG, "Entered cancelButton.OnClickListener.onClick()")

            // TODO - Indicate result and finish
            setResult(RESULT_CANCELED);
            finish();
        }

        // TODO - Set up OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton) as Button
        resetButton.setOnClickListener {
            Log.i(TAG, "Entered resetButton.OnClickListener.onClick()")
            mClassNameText!!.setText("")
            mSchoolDepartmentText!!.setText("")
            mUniversityText!!.setText("")
            mClassSectionText!!.setText("")
            mGroupSizeRadioGroup!!.check(mDefaultGroupButton!!.id)
            mGroupSizeRadioGroup = findViewById<RadioGroup>(R.id.memberGroup)
        }

        // Set up OnClickListener for the Submit Button
        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            if (!resources.getStringArray(R.array.university).contains(mUniversityText!!.text.toString())
                    || !resources.getStringArray(R.array.departments).contains(mSchoolDepartmentText!!.text.toString())
                    || mClassSectionText!!.text.toString().isEmpty() || mClassNameText!!.text.isEmpty()) {
                Toast.makeText(
                        applicationContext,
                        "Invalid/missing informantion.",
                        Toast.LENGTH_LONG
                ).show()
            } else {
                Log.i(TAG, "Entered submitButton.OnClickListener.onClick()")
                // TODO - gather ToDoItem data
                val groupSize: GroupSize =
                        if (mGroupSizeRadioGroup!!.checkedRadioButtonId == R.id.two) {
                            GroupSize.TWO
                        } else if (mGroupSizeRadioGroup!!.checkedRadioButtonId == R.id.three) {
                            GroupSize.THREE
                        } else if (mGroupSizeRadioGroup!!.checkedRadioButtonId == R.id.four) {
                            GroupSize.FOUR
                        } else {
                            GroupSize.FIVEORMORE
                        }

                val classString: String = mClassNameText?.text.toString()
                val universityString: String = mUniversityText?.text.toString()
                val sectionString: String = mClassSectionText?.text.toString()
                val departmentString: String = mSchoolDepartmentText?.text.toString()
                val newClass = Classes(classString, universityString, groupSize, departmentString, sectionString, 1)
                notebookRef!!.push().setValue(newClass)
                usersDB!!.child(dataUser!!.uid).child("joinedClasses").push().setValue(newClass)

                finish()
            }
        }
    }


}