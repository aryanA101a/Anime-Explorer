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
import com.aryan.animeexplorer.view.adapter.PagedAnimeTitlesAdapter
import com.aryan.animeexplorer.view.adapter.Orientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var trendingPagedAnimeTitlesAdapter: PagedAnimeTitlesAdapter
    private lateinit var popularPagedAnimeTitlesAdapter: PagedAnimeTitlesAdapter
    private lateinit var top100PagedAnimeTitlesAdapter: PagedAnimeTitlesAdapter


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
        trendingPagedAnimeTitlesAdapter = PagedAnimeTitlesAdapter(
            onViewItemClicked = ::onAnimeTitleClicked,
            orientation = Orientation.H
        )
        binding.rvSubSectionTrending.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = trendingPagedAnimeTitlesAdapter
        }

        popularPagedAnimeTitlesAdapter = PagedAnimeTitlesAdapter(
            onViewItemClicked = ::onAnimeTitleClicked,
            orientation = Orientation.H
        )
        binding.rvSubSectionAllTimePopular.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.HORIZONTAL, false
            )
            adapter = popularPagedAnimeTitlesAdapter
        }

        top100PagedAnimeTitlesAdapter = PagedAnimeTitlesAdapter(AnimeTitleType.TOP100, ::onAnimeTitleClicked)
        binding.rvSubSectionTop100Anime.apply {
            layoutManager = GridLayoutManager(
                requireContext(), 2
            )
            adapter = top100PagedAnimeTitlesAdapter

        }
    }

    private fun subscribeUi() {

        binding.srlHome.setOnRefreshListener { refresh() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingAnimeTitles.collect { animeTitle ->
                    trendingPagedAnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularAnimeTitles.collect { animeTitle ->
                    popularPagedAnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top100AnimeTitles.collect { animeTitle ->

                    top100PagedAnimeTitlesAdapter.submitData(viewLifecycleOwner.lifecycle, animeTitle)
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

    private fun refresh() {
        binding.srlHome.isRefreshing = true

        trendingPagedAnimeTitlesAdapter.refresh()
        popularPagedAnimeTitlesAdapter.refresh()
        top100PagedAnimeTitlesAdapter.refresh()

        binding.srlHome.isRefreshing = false


    }
}

