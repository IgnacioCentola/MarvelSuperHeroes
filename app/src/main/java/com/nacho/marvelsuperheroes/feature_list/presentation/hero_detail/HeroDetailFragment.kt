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
import com.nacho.marvelsuperheroes.feature_list.presentation.util.showErrorMessage
import kotlinx.coroutines.flow.collectLatest

class HeroDetailFragment : Fragment() {

    private val heroDetailViewModel by viewModels<HeroDetailViewModel>()

    private lateinit var binding: FragmentHeroDetailBinding

    private val args: HeroDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenStarted {
            heroDetailViewModel.getHeroById(args.heroId)
            heroDetailViewModel.sharedFlow.collectLatest {
                if (it.heroes != null) {
                    binding.hero = it.heroes[0]
                } else {
                    showErrorMessage(it.message)
                }
            }
        }
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
        binding.hero.comics.items.forEach {
            addChip(it.name, ChipType.COMICS)
        }
        binding.hero.series.items.forEach {
            addChip(it.name, ChipType.SERIES)
        }
        binding.hero.stories.items.forEach {
            addChip(it.name, ChipType.STORIES)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addChip(text: String, chipType: ChipType) {
        val chip = Chip(this.context).apply {
            this.text = text
        }
        when(chipType){
            ChipType.COMICS -> binding.comicsChipGroup.addView(chip)
            ChipType.SERIES -> binding.seriesChipGroup.addView(chip)
            ChipType.STORIES -> binding.storiesChipGroup.addView(chip)
        }
    }

    enum class ChipType {
        SERIES, COMICS, STORIES
    }
}

