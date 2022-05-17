package com.binar.challange_part5.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challange_part5.MyApplication
import com.binar.challange_part5.ViewModelFactory
import com.binar.challange_part5.adapter.MovieAdapter
import com.binar.challange_part5.dao.userDB
import com.binar.challange_part5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    //    private val viewModelRecommendation :   MovieRecommendationViewModel by activityViewModels()
    //    private val viewModelUser :             UserViewModel by activityViewModels()
    lateinit var viewModelUser              : UserHomeViewModel
    val viewModelMovieRecommendation by viewModels<MovieViewModel>{
        HomeViewModelFactory((activity?.application as MyApplication).repository)
    }
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModelUser = ViewModelProvider(requireActivity(),factory)[UserHomeViewModel::class.java]


        catchUsername()
        fetchMovie()

//        viewModelUser.getUserData()
        binding.toprofile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfile())
        }
    }
    private fun catchUsername(){
        viewModelUser.getUsername().observe(viewLifecycleOwner){
            binding.username.text = it.toString()
        }
    }

    private fun fetchMovie(){
        viewModelMovieRecommendation.getMovieRecommendation().observe(viewLifecycleOwner){
            Log.d("CEKOBSERV",it.toString())
            val adapter = MovieAdapter(it)
            val layoutManager =  LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            binding.userRecyclerView.layoutManager = layoutManager
            binding.userRecyclerView.adapter = adapter
        }
    }
}