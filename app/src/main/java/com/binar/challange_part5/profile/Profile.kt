package com.binar.challange_part5.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.challange_part5.User
import com.binar.challange_part5.databinding.FragmentProfileBinding


class Profile : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setUsername()

        binding.updateprofile.setOnClickListener{
            val input = User(
                userName = binding.profileusername.text.toString()
            )
            viewModel.updateUserData(input)
            findNavController().navigate(ProfileDirections.actionProfileToHomeFragment())
        }

        binding.logoutprofile.setOnClickListener{
            viewModel.logout()
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment())
        }
    }
}