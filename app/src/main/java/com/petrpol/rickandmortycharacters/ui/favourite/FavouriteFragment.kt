package com.petrpol.rickandmortycharacters.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.ui.adapters.AdapterCallback
import com.petrpol.rickandmortycharacters.ui.adapters.CharactersAdapter
import com.petrpol.rickandmortycharacters.ui.detail.DetailActivity
import com.petrpol.rickandmortycharacters.utils.DataState
import com.petrpol.rickandmortycharacters.utils.SnackBarHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*

/** Fragment to show list of favourite characters */
@AndroidEntryPoint
class FavouriteFragment : Fragment(), AdapterCallback {

    private val viewModel: FavouriteViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }


    private fun setupObservers() {
        //Update dataset of adapter
        viewModel.charactersList.observe(viewLifecycleOwner,{ characters ->
            charactersAdapter.setNewDataSet(characters)
        })

        //Show error
        viewModel.dataState.observe(viewLifecycleOwner,{ dataState ->
            if  (dataState is DataState.Error) SnackBarHelper.showErrorSnack(dataState.exception,FragmentFavoritesErrorLayout)
        })
    }

    /** Reloads data */
    override fun onResume() {
        super.onResume()
        viewModel.getFavouriteCharacters()
    }

    /** Setups Layout manager and adapter for recycler view */
    private fun setupRecyclerView() {
        FragmentFavoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        FragmentFavoritesRecyclerView.adapter = charactersAdapter
    }

    /** Handles list item selected. Shows detail activity with character id */
    override fun itemSelected(id: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(getString(R.string.argument_id), id)
        }
        startActivity(intent)
    }

    /** Not implemented function */
    override fun loadNextPage() {}
}
