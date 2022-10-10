package ru.orlovegor.search_film_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovegor.search_film_app.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // hide top actionBar
        initBottomNavView()

    }
    private fun initBottomNavView(){
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            navController.graph
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavMenu.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{
            _, destination, _ -> if (destination.id == R.id.fullDescriptionFragment){
                binding.bottomNavMenu.visibility = View.GONE
            } else {
                binding.bottomNavMenu.visibility = View.VISIBLE }
            }
        }
    }
