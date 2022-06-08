package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.nacho.marvelsuperheroes.R
import com.nacho.marvelsuperheroes.databinding.FragmentHeroDetailBinding
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import com.nacho.marvelsuperheroes.feature_list.presentation.util.showErrorMessage
import com.nacho.marvelsuperheroes.feature_list.presentation.util.getLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {

    private val heroDetailViewModel by viewModels<HeroDetailViewModel>()

    private lateinit var binding: FragmentHeroDetailBinding

    private val args: HeroDetailFragmentArgs by navArgs()

    private lateinit var loadingDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        loadingDialog = getLoadingDialog()
        loadingState()
        getHero()
        binding.apply {
            tryAgainButton.setOnClickListener {
                this@HeroDetailFragment.getHero()
            }

        }
        return binding.root
    }


    private fun getHero() {
        lifecycleScope.launchWhenStarted {
            heroDetailViewModel.getHeroById(args.heroId)
            heroDetailViewModel.sharedFlow.collectLatest {
                when (it) {
                    HeroesUiState.Loading -> loadingState()
                    is HeroesUiState.Error -> errorState(it.errorMsg)
                    is HeroesUiState.Success -> successState(it.heroes)
                }
            }
        }
    }

    private fun addChip(text: String, chipType: ChipType) {
        val chip = Chip(this.context).apply {
            this.text = text
            chipBackgroundColor = this@HeroDetailFragment.context?.let { ColorStateList.valueOf(it.getColor(R.color.primary_variant)) }
        }

        when (chipType) {
            ChipType.COMICS -> binding.comicsChipGroup.addView(chip)
            ChipType.SERIES -> binding.seriesChipGroup.addView(chip)
            ChipType.STORIES -> binding.storiesChipGroup.addView(chip)
        }
    }

    private fun successState(heroes: List<Hero>?) {
        loadingDialog.dismiss()
        binding.apply {
            hero = heroes?.get(0)
            heroDescriptionTextView.visibility = View.VISIBLE
            seriesChipGroup.visibility = View.VISIBLE
            comicsChipGroup.visibility = View.VISIBLE
            storiesChipGroup.visibility = View.VISIBLE
            tryAgainButton.visibility = View.GONE
            hero?.comics?.items?.forEach {
                addChip(it.name, ChipType.COMICS)
                Log.d("HeroDetailFragment", it.toString())
            }
            hero?.series?.items?.forEach {
                addChip(it.name, ChipType.SERIES)
                Log.d("HeroDetailFragment", it.toString())
            }
            hero?.stories?.items?.forEach {
                addChip(it.name, ChipType.STORIES)
                Log.d("HeroDetailFragment", it.toString())
            }
        }
    }

    private fun errorState(errorMsg: String) {
        loadingDialog.dismiss()
        binding.apply {
            heroDescriptionTextView.visibility = View.GONE
            seriesChipGroup.visibility = View.GONE
            comicsChipGroup.visibility = View.GONE
            storiesChipGroup.visibility = View.GONE
            tryAgainButton.visibility = View.VISIBLE
        }
        showErrorMessage(errorMsg)
    }

    private fun loadingState() {
        loadingDialog.show()
    }

    enum class ChipType {
        SERIES, COMICS, STORIES
    }
}

