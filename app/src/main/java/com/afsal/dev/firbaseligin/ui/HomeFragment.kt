package com.afsal.dev.firbaseligin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afsal.dev.firbaseligin.R
import com.afsal.dev.firbaseligin.databinding.FragmentHomeBinding
import com.afsal.dev.firbaseligin.ui.auth.AuthViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
private  var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]


        binding.apply {
            emailText.text=viewModel.currentUser?.email

            nameText.text=viewModel.currentUser?.displayName

            button.setOnClickListener {
                viewModel.logOut()
                navigateToLogin()
            }
        }




        super.onViewCreated(view, savedInstanceState)
    }


    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }
}