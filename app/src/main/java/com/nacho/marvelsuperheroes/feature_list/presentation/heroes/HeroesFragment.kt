package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nacho.marvelsuperheroes.R
import com.nacho.marvelsuperheroes.databinding.FragmentHeroesBinding
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import com.nacho.marvelsuperheroes.feature_list.presentation.util.isScreenPortrait
import com.nacho.marvelsuperheroes.feature_list.presentation.util.showErrorMessage
import com.nacho.marvelsuperheroes.feature_list.presentation.util.getLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HeroesFragment : Fragment() {

    private val heroes = arrayListOf<Hero>()
    private val _adapter = HeroesAdapter(heroes)

    private val viewModel by viewModels<HeroesViewModel>()

    private lateinit var binding: FragmentHeroesBinding

    private lateinit var loadingDialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog = getLoadingDialog()
        binding.apply {
            heroListRecyclerView.apply {
                layoutManager =
                    if (isScreenPortrait()) LinearLayoutManager(context) else GridLayoutManager(
                        context,
                        2
                    )
                adapter = _adapter
            }
            mainSwipeLayout.apply {
                setOnRefreshListener {
                    this.isRefreshing = false
                    fetchHeroes()
                }
                setColorSchemeColors(
                    resources.getColor(
                        R.color.primary,
                        requireActivity().theme
                    )
                )
            }

        }

        if(_adapter.itemCount == 0) fetchHeroes()
    }

    private fun fetchHeroes() {
        viewModel.getHeroes()
        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                when (it) {
                    HeroesUiState.Loading -> loadingState()
                    is HeroesUiState.Error -> errorState(it.errorMsg)
                    is HeroesUiState.Success -> successState(it.heroes)
                }
            }
        }
    }

    private fun loadingState() {
        loadingDialog.show()
    }

    private fun errorState(message: String) {
        loadingDialog.dismiss()
        binding.apply {
            heroListRecyclerView.visibility = View.INVISIBLE
            errorTextView.visibility = View.VISIBLE
            noDataImageView.visibility = View.VISIBLE
        }

        showErrorMessage(message)
    }

    private fun successState(data: List<Hero>?) {
        loadingDialog.dismiss()
        binding.apply {
            heroListRecyclerView.visibility = View.VISIBLE
            errorTextView.visibility = View.INVISIBLE
            noDataImageView.visibility = View.INVISIBLE
        }
        heroes.clear()
        data?.forEach {
            heroes.add(it)
            _adapter.notifyItemInserted(data.lastIndex)
        }
    }

}