package com.example.mvvmretrofit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.ViewModel.HomeViewModelFactory
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.databinding.FragmentHomeBinding
import com.example.mvvmretrofit.db.MealDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
   val viewModel :HomeViewModel by lazy {
       val mealdatabase =MealDatabase.getInstance(this)
        val HomeViewModelProviderFactory=HomeViewModelFactory(mealdatabase)
       ViewModelProvider(this,HomeViewModelProviderFactory)[HomeViewModel::class.java]

   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)


      val bottomnav=findViewById<NavigationBarView>(R.id.bottom_navigation)

       val navcontroller1=supportFragmentManager.findFragmentById(R.id.frag_host)
        val navController=navcontroller1!!.findNavController()
        NavigationUI.setupWithNavController(bottomnav,navController)
    }
}