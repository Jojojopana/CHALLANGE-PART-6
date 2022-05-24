package com.binar.challange_part5.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.challange_part5.databinding.FragmentLoginBinding
import com.binar.challange_part5.home.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tombolLogin.setOnClickListener{
           val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"Empty Fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.authLogin(username,password)
            }
        }
        binding.toregister.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        navigateUi()
    }

    fun navigateUi() {
        viewModel.result().observe(viewLifecycleOwner){
            if(it==true){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                Log.d("login","berhasil $it")
                viewModel.reset()
            } else {
                Log.d("login","test $it")
            }
        }
    }
}