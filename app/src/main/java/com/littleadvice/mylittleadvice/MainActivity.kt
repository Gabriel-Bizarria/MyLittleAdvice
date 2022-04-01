package com.littleadvice.mylittleadvice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.littleadvice.mylittleadvice.databinding.ActivityMainBinding

const val MAIN_PHRASE = "MAIN_PHRASE"
const val DAY_SAVED = "DAY_SAVED"

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController = findNavController(R.id.container_fragments)
        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.dailyAdviceFragment,
                R.id.randomFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)
        binding.bottomNavigationView.setupWithNavController(navController)



    }
}