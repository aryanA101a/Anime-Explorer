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
    private lateinit var adapter: AnimeTitlesAdapter

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
        binding.rvSubSectionTrending.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        adapter = AnimeTitlesAdapter()
        binding.rvSubSectionTrending.adapter = adapter
        adapter.submitList(viewModel.animeTitlesState.value.animeTitles)
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.animeTitlesState.collect { animeTitlesState ->
                    adapter.submitList(animeTitlesState.animeTitles)
                    binding.pbSubSectionTrending.visibility =
                        if (animeTitlesState.isLoading) View.VISIBLE else View.INVISIBLE
                }
            }
        }
    }


}