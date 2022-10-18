package com.bintangpoetra.sumbanginaja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bintangpoetra.sumbanginaja.databinding.ActivityMainBinding
import com.bintangpoetra.sumbanginaja.utils.ext.gone
import com.bintangpoetra.sumbanginaja.utils.ext.show

class MainActivity : AppCompatActivity() {

    private lateinit var _activityMainBinding: ActivityMainBinding
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding.root)

        initUI()
    }

    private fun initUI() {
        val navHostBottomBar = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        binding.btmNavigation.setupWithNavController(navControllerBottomBar)
        binding.btmNavigation.background = null
        navControllerBottomBar.addOnDestinationChangedListener { _, currentDestination, _ ->
            if (currentDestination.id == R.id.homeFragment || currentDestination.id == R.id.profileFragment) {
                binding.btmNavigation.show()
            } else {
                binding.btmNavigation.gone()
            }
        }
    }

}