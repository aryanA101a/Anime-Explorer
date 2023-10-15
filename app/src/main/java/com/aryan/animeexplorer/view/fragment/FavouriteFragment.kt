package com.aryan.animeexplorer.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aryan.animeexplorer.R
import com.aryan.animeexplorer.databinding.FragmentAnimeDetailsBinding
import com.aryan.animeexplorer.databinding.FragmentFavouriteBinding
import com.aryan.animeexplorer.presentation.FavouriteViewModel
import com.aryan.animeexplorer.view.adapter.FavouriteAnimeTitlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var favouriteAnimeTitlesAdapter: FavouriteAnimeTitlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        initFavouriteAnimeTitles()
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        try {
            Log.i("TAG", "subscribeUi: ${favouriteViewModel.boo}")
            viewLifecycleOwner.lifecycleScope.launch{
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    favouriteViewModel.favourites.collect { favourites ->
                        Log.i("TAG", "subscribeUi: $favourites")
                        favouriteAnimeTitlesAdapter.submitList(favourites)
                    }
                }
            }
        }catch (e:Exception){
            Log.e("TAG", "subscribeUi: $e", )
        }
    }

    private fun initFavouriteAnimeTitles() {
        favouriteAnimeTitlesAdapter= FavouriteAnimeTitlesAdapter(::onAnimeTitleClicked)
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            adapter = favouriteAnimeTitlesAdapter
        }
    }

    private fun onAnimeTitleClicked(id: Int, title: String) {
        val bundle = Bundle().apply {
            putString("id", id.toString())
            putString("title", title)
        }
        findNavController().navigate(R.id.action_favouriteFragment_to_animeDetailFragment,bundle)
    }

}