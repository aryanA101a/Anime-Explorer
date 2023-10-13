package com.aryan.animeexplorer.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryan.animeexplorer.databinding.FragmentHomeBinding
import com.aryan.animeexplorer.presentation.HomeViewModel
import com.aryan.animeexplorer.view.adapter.AnimeTitlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var trendingAnimeTitlesAdapter: AnimeTitlesAdapter
    private lateinit var popularAnimeTitlesAdapter: AnimeTitlesAdapter
    private lateinit var top100AnimeTitlesAdapter: AnimeTitlesAdapter


    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initSubsections()
        subscribeUi()
        return binding.root
    }

    private fun initSubsections() {
        trendingAnimeTitlesAdapter = AnimeTitlesAdapter()
        binding.rvSubSectionTrending.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = trendingAnimeTitlesAdapter
            setHasFixedSize(true)
        }

        popularAnimeTitlesAdapter = AnimeTitlesAdapter()
        binding.rvSubSectionAllTimePopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = popularAnimeTitlesAdapter
            setHasFixedSize(true)
        }

        top100AnimeTitlesAdapter = AnimeTitlesAdapter()
        binding.rvSubSectionTop100Anime.apply {
            layoutManager = GridLayoutManager(
                requireContext(),2
            )
            adapter = top100AnimeTitlesAdapter
        }
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingAnimeTitles.collect { animeTitle ->
                    trendingAnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularAnimeTitles.collect { animeTitle ->
                    popularAnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top100AnimeTitles.collect { animeTitle ->
                    top100AnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
                }
            }
        }
    }


}