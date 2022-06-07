package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.nacho.marvelsuperheroes.databinding.FragmentHeroDetailBinding
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import com.nacho.marvelsuperheroes.feature_list.presentation.util.showErrorMessage
import com.nacho.marvelsuperheroes.feature_list.presentation.util.getLoadingDialog
import kotlinx.coroutines.flow.collectLatest

class HeroDetailFragment : Fragment() {

    private val heroDetailViewModel by viewModels<HeroDetailViewModel>()

    private lateinit var binding: FragmentHeroDetailBinding

    private val args: HeroDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        getHero()
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            tryAgainButton.setOnClickListener {
                this@HeroDetailFragment.getHero()
            }
            hero?.comics?.items?.forEach {
                addChip(it.name, ChipType.COMICS)
            }
            hero?.series?.items?.forEach {
                addChip(it.name, ChipType.SERIES)
            }
            hero?.stories?.items?.forEach {
                addChip(it.name, ChipType.STORIES)
            }
        }
        super.onViewCreated(view, savedInstanceState)
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
        }

        when (chipType) {
            ChipType.COMICS -> binding.comicsChipGroup.addView(chip)
            ChipType.SERIES -> binding.seriesChipGroup.addView(chip)
            ChipType.STORIES -> binding.storiesChipGroup.addView(chip)
        }
    }

    private fun successState(heroes: List<Hero>?) {
        binding.apply {
            hero = heroes?.get(0)
            heroNameTextView.visibility = View.VISIBLE
            heroDescriptionTextView.visibility = View.VISIBLE
            seriesChipGroup.visibility = View.VISIBLE
            comicsChipGroup.visibility = View.VISIBLE
            storiesChipGroup.visibility = View.VISIBLE
            tryAgainButton.visibility = View.GONE
        }
    }

    private fun errorState(errorMsg: String) {
        binding.apply {
            heroNameTextView.visibility = View.GONE
            heroDescriptionTextView.visibility = View.GONE
            seriesChipGroup.visibility = View.GONE
            comicsChipGroup.visibility = View.GONE
            storiesChipGroup.visibility = View.GONE
            tryAgainButton.visibility = View.VISIBLE
        }
        showErrorMessage(errorMsg)
    }

    private fun loadingState() {
        getLoadingDialog()
    }

    enum class ChipType {
        SERIES, COMICS, STORIES
    }
}

