package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import androidx.fragment.app.Fragment
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesFragment : Fragment(){

    private val heroes = arrayListOf<Hero>()

}