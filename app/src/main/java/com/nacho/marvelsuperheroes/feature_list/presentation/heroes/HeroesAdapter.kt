package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nacho.marvelsuperheroes.databinding.HeroItemBinding
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

class HeroesAdapter(private val values: List<Hero>) :
    RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HeroesAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = values.size



    inner class ViewHolder(val binding: HeroItemBinding) : RecyclerView.ViewHolder(binding.root)
}
