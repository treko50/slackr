package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.Fragments.ClassFragment
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.Fragments.SettingsFragment

class HomePageActivity : AppCompatActivity() {
    private var nav: com.google.android.material.bottomnavigation.BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val classFragment = ClassFragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(homeFragment)

        nav = findViewById(R.id.bottom_navigation)
        nav!!.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_classes -> makeCurrentFragment(classFragment)
                R.id.ic_settings -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    }


}