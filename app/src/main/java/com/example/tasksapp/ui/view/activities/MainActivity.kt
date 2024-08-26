package com.example.tasksapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ActivityMainBinding
import com.example.tasksapp.utils.Constants
import com.example.tasksapp.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var userNameRef: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        mainActivityViewModel = MainActivityViewModel(application)

        userNameRef = binding.appBarMain.contentMain.userName
        mainActivityViewModel.setUserName()

        setListeners()
        observer()
        handleNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun observer() {
        mainActivityViewModel.userName.observe(this) {
            userNameRef.text = getString(R.string.greeting_message, it)
        }
    }

    fun handleNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_tasks, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun setListeners() {
        binding.appBarMain.fab.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when(view.id) {
            R.id.fab -> {
                val intent = Intent(this, NewTaskFormActivity::class.java)
                startActivity(intent)
            }
        }
    }
}