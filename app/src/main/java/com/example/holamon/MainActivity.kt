package com.example.holamon

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.holamon.friends.FriendsFragment
import com.example.holamon.game.GameFragment
import com.example.holamon.stats.EstadisticasFragment
import com.google.android.material.navigation.NavigationView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        checkIfUser()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)

            when (menuItem.itemId){
                R.id.itemGame -> {
                    openGameFragment()
                        true
                    }

                R.id.itemStadistics -> {
                    openEstadisticasFragment()
                    true
                }

                R.id.itemmap -> {
                    openMapFragment()
                    true
                }

                R.id.itemfriend -> {
                    openFriendFragment()
                    true
                }

                R.id.itemLogout -> {
                    logout()
                    true
                }

                else -> {

                    openEstadisticasGlobalesFragment()
                    true
                }

            }
        }

        openGameFragment()
        navigationView.setCheckedItem(R.id.itemGame)
    }

    fun openGameFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            GameFragment()
        ).commit()
    }

    private fun openEstadisticasFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            EstadisticasFragment()
        ).commit()
    }

    private fun openEstadisticasGlobalesFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            EstadisticasGlobalesFragment()
        ).commit()
    }

    private fun openMapFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            //MapFragment()
            FullMapFragment()
        ).commit()
    }

    private fun openFriendFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            FriendsFragment()
        ).commit()
    }

    private fun logout() {
        EstadisticasClass.deleteUser()
        Toast.makeText(applicationContext,"Logout",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun finishMainActivity() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun checkIfUser() {
        if (EstadisticasClass.haveUser()){
            Toast.makeText(applicationContext,"Name: " + EstadisticasClass.getUserString(),Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}