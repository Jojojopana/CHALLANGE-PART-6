package com.binar.challange_part5.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binar.challange_part5.data.model.GetAllMovieResponse
import com.binar.challange_part5.databinding.FragmentDetailBinding
import com.binar.challange_part5.home.HomeFragmentDirections
import com.bumptech.glide.Glide

class MovieAdapter (private val item : List<GetAllMovieResponse>) : RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {
    class MainViewHolder(val binding: FragmentDetailBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(FragmentDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.judulDetail.text= item[position].title
        val url = "https://image.tmdb.org/t/p/original/${item[position].posterPath}"
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.binding.gambar)
        holder.binding.gambar.setOnClickListener {
            Log.d("jajaja","kakka")
            val movie = GetAllMovieResponse(
                posterPath = item[position].posterPath,
                title = item[position].title,
                overview = item[position].overview,
                releaseDate = item[position].releaseDate,
                adult = item[position].adult,
            )
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie))
        }
    }
    override fun getItemCount(): Int {
        return item.size
    }
}