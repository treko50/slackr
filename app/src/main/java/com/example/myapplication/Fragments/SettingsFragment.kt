package com.example.myapplication.Fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.*
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_settings.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var db : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef : DatabaseReference? = db!!.getReference("Notebooks")
    private var dataUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var usersDB : DatabaseReference? = db!!.getReference("Users")

    internal lateinit var getUsers: MutableList<User>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
            // if the user is logged in, display their info on the screen
            email1.setText(firebaseUser.email)
        }

        usersDB!!.child(dataUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val first = dataSnapshot.child("fname").value.toString()
                val last = dataSnapshot.child("lname").value.toString()
                val fullname = "$first $last"
                fff.text = fullname
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("cancel", databaseError.toString())
            }
        })


        view.findViewById<Button>(R.id.button1).setOnClickListener {
            var firstName = view.findViewById<EditText>(R.id.fn)
            var lastName = view.findViewById<EditText>(R.id.ln)

            val first1 :String = firstName!!.text.toString()
            val last1 : String = lastName!!.text.toString()


            usersDB!!.child(dataUser!!.uid).child("fname").setValue(first1)
            usersDB!!.child(dataUser!!.uid).child("lname").setValue(last1)

        }


        view.findViewById<Button>(R.id.logOut).setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SettingsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
