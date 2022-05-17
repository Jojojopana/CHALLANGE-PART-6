package com.binar.challange_part5.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.challange_part5.R
import com.binar.challange_part5.ViewModelFactory
import com.binar.challange_part5.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding?= null
    private val binding get() = _binding!!
    lateinit var viewModel : SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(), factory)[SplashViewModel::class.java]

        viewModel.loginCheck()
        navigate()
    }
    private fun navigate(){
        viewModel.loginCheck().observe(viewLifecycleOwner){
            if(it == "default"){
//                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
//                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        }
    }
}