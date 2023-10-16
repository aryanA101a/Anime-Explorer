package com.aryan.animeexplorer.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.aryan.animeexplorer.databinding.FragmentAnimeDetailsBinding
import com.aryan.animeexplorer.presentation.AnimeDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailsFragment : Fragment() {


    private lateinit var binding: FragmentAnimeDetailsBinding
    private val animeDetailsViewModel: AnimeDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = animeDetailsViewModel
            lifecycleOwner = viewLifecycleOwner

        }

        val id = arguments?.getString("id")
        val title = arguments?.getString("title")
        id?.let {
            animeDetailsViewModel.setAnimeDetails(it.toInt(), title)
            subscribeUi()
        }

        return binding.root
    }

    private fun subscribeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeDetailsViewModel.uiState.collect {
                    when (it) {
                        is AnimeDetailsViewModel.AnimeDetailsUiStates.Error -> {
                            Log.i("TAG", "subscribeUi: erros")
                            Snackbar.make(binding.cLayout, it.message, Snackbar.LENGTH_LONG).show()
                        }
                        is AnimeDetailsViewModel.AnimeDetailsUiStates.Initial -> Unit
                        is AnimeDetailsViewModel.AnimeDetailsUiStates.Success -> {
                            binding.apply {
                                tvHeadingDuration.isVisible = true
                                tvHeadingEpisodes.isVisible = true
                                tvHeadingMeanScore.isVisible = true
                                tvHeadingGenres.isVisible = true
                                tvHeadingStatus.isVisible = true
                                tvHeadingFormat.isVisible = true

                                ivBanner.load(
                                    it.bannerImageUrl
                                ) {
                                    crossfade(true)
                                }
                            }

                        }

                        is AnimeDetailsViewModel.AnimeDetailsUiStates.ShowIsFavourite ->
                            binding.btnFavourite.isVisible = true
                    }
                }
            }
        }
    }


}