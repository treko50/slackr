package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class GroupChatActivity : AppCompatActivity() {
    private var db: FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef: DatabaseReference? = db!!.getReference("Notebooks")
    private var dataUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var usersDB: DatabaseReference? = db!!.getReference("Users")
    private lateinit var userGroupDB: DatabaseReference
    private lateinit var getNames: MutableList<User>
    private lateinit var listViewClasses: ListView
    internal lateinit var getClasses: MutableList<Classes>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_chat)
        val singleClass = intent.getSerializableExtra("singleClass") as Classes
        getNames = ArrayList()
        usersDB!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                var myUser: User? = null
                var myClasses: Classes? = null

                for (postSnap in dataSnapshot.children) {
                    val id = postSnap.key
                    userGroupDB = usersDB!!.child(id!!).child("joinedClasses")
                    userGroupDB.addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(snap: DataSnapshot) {
                            for (ds in snap.children) {
                                //Log.i("Slacker-App", "ds: $ds")
                                myClasses = ds.getValue(Classes::class.java)
                                //Log.i("Slacker-App", "my class: $myClasses")
                                if(myClasses.toString() == (singleClass.toString()))
                                {
                                    Log.i("Slacker-App", "inside if statement: $myClasses")
                                    usersDB!!.child(id).addListenerForSingleValueEvent(object: ValueEventListener {

                                        override fun onCancelled(p0: DatabaseError) {
                                            Log.e("cancel", p0.toString())
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {

                                            myUser = p0.getValue(User::class.java)
                                            if (notExist(myUser!!, getNames)) {
                                                getNames.add(myUser!!)
                                            }
                                            listViewClasses = findViewById(R.id.member_list)
                                            val classListAdapter = UserList(this@GroupChatActivity, getNames)
                                            listViewClasses.adapter = classListAdapter
                                        }
                                    })
                                }
                            }
                            Log.i("Slacker-App", "single class: $singleClass")
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            //Log.i("Slacker-App", "")
                        }
                    })
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("cancel", databaseError.toString())
            }
        })
//        listViewClasses = findViewById(R.id.member_list)
//        val classListAdapter = UserList(this@GroupChatActivity, getNames)
//        listViewClasses.adapter = classListAdapter

    }

    fun notExist(user: User, list: MutableList<User>):Boolean {
        for (u in list){
            if (u.fName == user.fName && u.email == user.email && u.lName == user.lName) {
                return false
            }
        }
        return true
    }
}