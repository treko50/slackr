package com.example.myapplication.Fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.myapplication.*
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var db : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var notebookRef : DatabaseReference? = db!!.getReference("Notebooks")
    private var dataUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var usersDB : DatabaseReference? = db!!.getReference("Users")
    private lateinit var listViewClasses: ListView
    internal lateinit var getClasses: MutableList<Classes>
    private lateinit var ListViewAdapter: ArrayAdapter<String>
    private lateinit var getNames: MutableList<String>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view : View =  inflater.inflate(R.layout.fragment_home, container, false)
        listViewClasses = view.findViewById(R.id.list_groups)
        getClasses = ArrayList<Classes>()
        usersDB!!.child(dataUser!!.uid).child("joinedClasses").addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var myClasses: Classes? = null
                getClasses.clear()
                for (postSnap in dataSnapshot.children) {
                    try {

                        myClasses = postSnap.getValue(Classes::class.java)
                        getClasses.add(myClasses!!)

                    }  catch (e: java.lang.Exception){
                        Log.e(ContentValues.TAG, e.toString())
                    }
                }
                val groupAdapter = this@HomeFragment.activity?.let { ClassList(it, getClasses) }
                listViewClasses.adapter = groupAdapter
                listViewClasses.onItemClickListener = AdapterView.OnItemClickListener{
                    adapterView, view, i, l ->
                    val getClass = getClasses[i]
                    val intent = Intent(activity, GroupChatActivity::class.java)
                    intent.putExtra("singleClass", getClass)
                    startActivity(intent)

                }
            }

            override fun onCancelled(databaseError : DatabaseError) {
                Log.e("cancel", databaseError.toString())
            }
        })
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}