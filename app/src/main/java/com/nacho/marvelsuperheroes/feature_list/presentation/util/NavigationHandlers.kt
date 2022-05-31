package com.nacho.marvelsuperheroes.feature_list.presentation.util

import android.view.View
import androidx.navigation.findNavController
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

class NavigationHandlers {

    fun onGoToHeroDetail(view: View , hero: Hero){
        val navController = view.findNavController()
    }
}