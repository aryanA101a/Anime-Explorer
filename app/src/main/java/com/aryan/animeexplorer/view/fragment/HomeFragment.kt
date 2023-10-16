package com.aryan.animeexplorer.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.FragmentHomeBinding
import com.aryan.animeexplorer.domain.model.AnimeTitleType
import com.aryan.animeexplorer.presentation.HomeViewModel
import com.aryan.animeexplorer.view.adapter.AnimeTitlesAdapter
import com.aryan.animeexplorer.view.adapter.Orientation
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
        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.btnFavourites.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favouriteFragment)
        }
        initSubsections()
        subscribeUi()
        return binding.root
    }

    private fun initSubsections() {
        trendingAnimeTitlesAdapter = AnimeTitlesAdapter(
            onViewItemClicked = ::onAnimeTitleClicked,
            orientation = Orientation.H
        )
        binding.rvSubSectionTrending.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = trendingAnimeTitlesAdapter
        }

        popularAnimeTitlesAdapter = AnimeTitlesAdapter(
            onViewItemClicked = ::onAnimeTitleClicked,
            orientation = Orientation.H
        )
        binding.rvSubSectionAllTimePopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = popularAnimeTitlesAdapter
        }

        top100AnimeTitlesAdapter = AnimeTitlesAdapter(AnimeTitleType.TOP100, ::onAnimeTitleClicked)
        binding.rvSubSectionTop100Anime.apply {
            layoutManager = GridLayoutManager(
                requireContext(), 2
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

    private fun onAnimeTitleClicked(id: Int, title: String) {
        val bundle = Bundle().apply {
            putString("id", id.toString())
            putString("title", title)
        }
        findNavController().navigate(R.id.action_homeFragment_to_animeDetailFragment, bundle)
    }


}

