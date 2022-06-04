package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nacho.marvelsuperheroes.databinding.HeroItemBinding
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.presentation.util.NavigationHandlers

class HeroesAdapter(private val values: List<Hero>) :
    RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesAdapter.ViewHolder {
        val binding = HeroItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.handlers = NavigationHandlers()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroesAdapter.ViewHolder, position: Int) {
        holder.binding.hero = values[position]
    }

    override fun getItemCount(): Int = values.size


    inner class ViewHolder(val binding: HeroItemBinding) : RecyclerView.ViewHolder(binding.root)
}
