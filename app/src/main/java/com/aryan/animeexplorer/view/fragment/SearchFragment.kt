package com.aryan.animeexplorer.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.FragmentSearchBinding
import com.aryan.animeexplorer.presentation.SearchViewModel
import com.aryan.animeexplorer.view.adapter.AnimeTitlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var animeTitlesAdapter: AnimeTitlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }
        binding.tietSearch.doOnTextChanged { text, start, before, count ->
            text?.let { viewModel.executeSearch(it.toString()) }
        }
        initSearchResults()
        subscribeUi()
        return binding.root
    }

    private fun initSearchResults() {
        animeTitlesAdapter = AnimeTitlesAdapter(::onAnimeTitleClicked)
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            adapter = animeTitlesAdapter
        }
    }

    private fun subscribeUi() {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.searchedAnimeTitles.collect { animeTitles ->
                        animeTitlesAdapter.submitList(animeTitles)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect {
                       handleUiState(it)
                    }
                }
            }

    }

    private fun handleUiState(it: SearchViewModel.SearchUiStates) {
        when (it) {
            is SearchViewModel.SearchUiStates.Empty -> {
                binding.llException.isVisible = true
                binding.tvException.text = "No Data"
            }

            is SearchViewModel.SearchUiStates.Error -> {
                binding.llException.isVisible = true
                binding.tvException.text = "Something went wrong"
            }

            is SearchViewModel.SearchUiStates.Initial -> Unit
            is SearchViewModel.SearchUiStates.Success -> binding.llException.isVisible =
                false
        }
    }

    private fun onAnimeTitleClicked(id: Int, title: String) {
        val bundle = Bundle().apply {
            putString("id", id.toString())
            putString("title", title)
        }
        findNavController().navigate(R.id.action_searchFragment_to_animeDetailFragment, bundle)
    }

}