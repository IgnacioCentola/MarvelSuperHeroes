package com.nacho.marvelsuperheroes.feature_list.presentation.util

import android.view.View
import androidx.navigation.findNavController
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.presentation.heroes.HeroesFragmentDirections

class NavigationHandlers {

    fun onGoToHeroDetail(view: View, heroId : Int) {
        val navController = view.findNavController()
        val action = HeroesFragmentDirections.actionHeroesFragmentToHeroDetailFragment(heroId)
        navController.navigate(action)
    }
}