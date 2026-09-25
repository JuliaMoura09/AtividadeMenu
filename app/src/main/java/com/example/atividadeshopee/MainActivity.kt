package com.example.atividadeshopee

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.MenuItem
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.atividadeshopee.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hide the status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).hide(WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        var toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.navOpen,
            R.string.navClose
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //supportActionBar?.title = ""
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomCart -> openFragment(CartFragment())
                R.id.bottomMenu -> openFragment(MenuFragment())
                R.id.bottomProfile -> openFragment(ProfileFragment())
                R.id.bottomHome -> openFragment(HomeFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Categorias", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this){
           if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
               binding.drawerLayout.closeDrawer(GravityCompat.START)
           }else{
               finish()
           }

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navPele -> openFragment(PeleFragment())
            R.id.navRoupas -> openFragment(RoupaFragment())
            R.id.navEletro -> openFragment(EletroFragment())
            R.id.navPapelaria -> openFragment(PaperFragment())

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

        }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}