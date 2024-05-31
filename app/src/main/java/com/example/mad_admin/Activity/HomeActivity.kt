package com.example.mad_admin.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mad_admin.AddHomeWorkFragment
import com.example.mad_admin.ChatsFragment
import com.example.mad_admin.HomeFragment
import com.example.mad_admin.NotificationFragment
import com.example.mad_admin.R
import com.example.mad_admin.StudentsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {


    lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        loadFragment(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.addHomeWorkFragment -> {
                    loadFragment(AddHomeWorkFragment())
                    true
                }

                R.id.notificationFragment -> {
                    loadFragment(NotificationFragment())
                    true
                }

                R.id.studentsFragment -> {
                    loadFragment(StudentsFragment())
                    true
                }

                R.id.chatsFragment -> {
                    loadFragment(ChatsFragment())
                    true
                }

                else -> false
                }
            }
        }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    }

