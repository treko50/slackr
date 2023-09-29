package com.example.myapplication


import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ClassList(private val context: Activity, private var classes: List<Classes>): ArrayAdapter<Classes>(context,R.layout.layout_classes_list, classes){
    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_classes_list, null, true)
        val textViewName = listViewItem.findViewById<View>(R.id.textViewName) as TextView
        val textViewRating = listViewItem.findViewById<View>(R.id.textViewCountry) as TextView
        val single = classes[position]
        textViewName.text = single.className
        textViewRating.text = single.classSection

        return listViewItem
    }

}