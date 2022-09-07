package com.afsal.dev.firbaseligin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.afsal.dev.firbaseligin.R
import com.afsal.dev.firbaseligin.data.Resource
import com.afsal.dev.firbaseligin.data.uttils.CoreUtiles
import com.afsal.dev.firbaseligin.databinding.FragmentSignUpBinding
import com.afsal.dev.firbaseligin.ui.auth.AuthViewModel
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           viewModel =ViewModelProvider(requireActivity())[AuthViewModel::class.java]



        binding.signUpBt.setOnClickListener {
              //navigateToHome()

            signUp()
        }

        updateSignUp()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {

        _binding = null
        super.onDestroy()
    }


    private fun navigateToHome(){
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }

    private fun signUp(){
        val name=binding.editTextTextPersonName.text.toString()
        val email=binding.editTextTextEmailAddress.text.toString()
        val password=binding.editTextTextPassword.text.toString()
          Log.d("RRR","signUpdata $name $email ,$password")
        if (email.isNotEmpty()&& email.isNotEmpty()&& password.isNotEmpty()){

            viewModel.signUp(name,email,password)

            binding.apply {
                editTextTextPersonName.text=null
                editTextTextEmailAddress.text=null
                editTextTextPassword.text=null
            }
        }

    }

    private fun updateSignUp(){
        lifecycleScope.launch {
            viewModel.signUpFlow.collect{

                when(it){

                    is Resource.Loading ->{
                        binding.progressBar2.visibility=View.VISIBLE
                        Log.d("RRR","loading state")
                    }
                    is Resource.Success ->{
                        binding.progressBar2.visibility=View.GONE
                        CoreUtiles.showSnackBar(binding.root,"Registerd successfully")

                        navigateToHome()
                        Log.d("RRR","signUp success ${it.result.email}")
                    }
                    is Resource.Failure ->{
                        binding.progressBar2.visibility=View.GONE
                        Log.d("RRR","signUp failure ${it.exception.toString()}")
                        CoreUtiles.showSnackBar(binding.root,it.exception.message.toString())
                    }


                    else -> {}
                }
            }


        }
    }

}