package com.example.booklibraryapp.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.booklibraryapp.Fragment.*
import com.example.booklibraryapp.Notification
import com.example.booklibraryapp.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var actionBarToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel()
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        val navigationView : NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, AboutUsFragment()).commit()
        }
    }

    private fun createChannel()
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            val name = "New Book"
            val descriptionText = "New Book Created"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Notification.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
// Register the channel with the system
            val notificationManager: NotificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        when(item.itemId){
            R.id.books -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BooksFragment()).commit()
            R.id.createBook -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, CreateBookFragment()).commit()
            R.id.location -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, MapsFragment()).commit()
            R.id.camera -> startCamera()
            R.id.aboutUs -> supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, AboutUsFragment()).commit()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        if(actionBarToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun startCamera() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1222)
        }else{
            startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
        }
    }
}