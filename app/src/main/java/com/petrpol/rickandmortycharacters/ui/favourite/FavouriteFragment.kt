package com.petrpol.rickandmortycharacters.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.ui.adapters.AdapterCallback
import com.petrpol.rickandmortycharacters.ui.adapters.CharactersAdapter
import com.petrpol.rickandmortycharacters.ui.detail.DetailActivity
import com.petrpol.rickandmortycharacters.utils.DataState
import com.petrpol.rickandmortycharacters.utils.SnackBarHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.android.synthetic.main.fragment_favorites.*

@AndroidEntryPoint
class FavouriteFragment : Fragment(), AdapterCallback {

    private val viewModel: FavouriteViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter(this)
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ viewModel.getFavouriteCharacters() }


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
        viewModel.charactersList.observe(viewLifecycleOwner,{
            characters ->
            charactersAdapter.setNewDataSet(characters)
        })

        viewModel.dataState.observe(viewLifecycleOwner,{
            dataState ->
            if  (dataState is DataState.Error) SnackBarHelper.showErrorSnack(dataState.exception,FragmentFavoritesErrorLayout)
        })
    }

    private fun setupRecyclerView() {
        FragmentFavoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        FragmentFavoritesRecyclerView.adapter = charactersAdapter
    }

    override fun itemSelected(id: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(getString(R.string.argument_id), id)
        }
        resultLauncher.launch(intent)
    }

    override fun loadNextPage() {}
}
