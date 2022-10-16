package com.bintangpoetra.sumbanginaja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bintangpoetra.sumbanginaja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding.root)
    }
}