package com.binar.challange_part5.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.challange_part5.User
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.databinding.FragmentRegisterBinding
import com.binar.challange_part5.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener{
            val confPassword = binding.usernameRegister.text.toString()
            val objectUser = User(
                userName = binding.usernameRegister.text.toString(),
                password = binding.passwordRegister.text.toString()
            )
            viewModel.addUser(objectUser,confPassword)
        }
        navigateUi()
    }
    fun navigateUi() {
        viewModel.result().observe(viewLifecycleOwner){
            if(it == true){
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                Log.d("register","berhasil $it")
            } else {
                Log.d("register","test $it")
            }
        }
    }
}