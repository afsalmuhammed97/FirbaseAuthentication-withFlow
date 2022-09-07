package com.afsal.dev.firbaseligin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.afsal.dev.firbaseligin.databinding.ActivityMainBinding
import com.afsal.dev.firbaseligin.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel by viewModels <AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

     //     navController=findNavController(R.id.fragmentContainerView)

    supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

    }
}