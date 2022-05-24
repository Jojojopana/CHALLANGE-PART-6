package com.binar.challange_part5.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challange_part5.adapter.MovieAdapter
import com.binar.challange_part5.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val ViewModel: MovieViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null

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


        catchUsername()
        fetchMovie()

//        viewModelUser.getUserData()
        binding.toprofile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfile())
        }
    }
    private fun catchUsername(){
        ViewModel.getUsername().observe(viewLifecycleOwner){
            binding.username.text = it.toString()
        }
    }

    private fun fetchMovie(){
        ViewModel.getMovieRecommendation().observe(viewLifecycleOwner){
            Log.d("CEKOBSERV",it.toString())
            val adapter = MovieAdapter(it)
            val layoutManager =  LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            binding.userRecyclerView.layoutManager = layoutManager
            binding.userRecyclerView.adapter = adapter
        }
    }
}