package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nacho.marvelsuperheroes.databinding.FragmentHeroDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {
    private lateinit var binding : FragmentHeroDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}