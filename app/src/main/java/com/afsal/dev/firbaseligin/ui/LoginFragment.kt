package com.afsal.dev.firbaseligin.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.afsal.dev.firbaseligin.R
import com.afsal.dev.firbaseligin.data.Resource
import com.afsal.dev.firbaseligin.data.uttils.CoreUtiles
import com.afsal.dev.firbaseligin.databinding.FragmentLoginBinding
import com.afsal.dev.firbaseligin.ui.auth.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
private   var _binding: FragmentLoginBinding?=null
private val binding get() =_binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding=FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]


            loginUpdation()
       binding.loginButton.setOnClickListener {
           login()
           //navigateToHome()
       }

        binding.signUpText.setOnClickListener {
            navigateToSignUp()
        }
        super.onViewCreated(view, savedInstanceState)
    }



    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }
     private fun login(){
         val email=binding.editTextTextEmailAddress.text.toString()
          val password=binding.editTextTextPassword.text.toString()

         if (email.isNotEmpty() && password.isNotEmpty()){


             viewModel.login(email,password)

             binding.apply {
               //  editTextTextPassword.text=null
                // editTextTextEmailAddress.text=null
             }
         }


     }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

    }

    private fun navigateToSignUp(){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment )
    }


    private fun loginUpdation(){
        lifecycleScope.launch {
          viewModel.loginFlow.collect{

             when(it){

                 is Resource.Loading ->{
                     Log.d("RRR","loading state")
                     binding.progressBar.visibility=View.VISIBLE
                 }
                 is Resource.Success ->{

                     binding.progressBar.visibility=View.GONE

                     navigateToHome()
                     Log.d("RRR","success ${it.result.email}")
                 }
                 is Resource.Failure ->{
                     Log.d("RRR","failure")
                     binding.progressBar.visibility=View.GONE
                     CoreUtiles.showSnackBar(binding.rootLayout,it.exception.message.toString())
                 }


                 else -> {}
             }
         }


        }
    }
}