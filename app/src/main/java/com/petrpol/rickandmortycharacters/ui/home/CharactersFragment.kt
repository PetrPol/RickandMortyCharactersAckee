package com.petrpol.rickandmortycharacters.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.ui.MainActivity
import com.petrpol.rickandmortycharacters.ui.adapters.AdapterCallback
import com.petrpol.rickandmortycharacters.ui.adapters.CharactersAdapter
import com.petrpol.rickandmortycharacters.ui.detail.DetailActivity
import com.petrpol.rickandmortycharacters.utils.DataState
import com.petrpol.rickandmortycharacters.utils.SearchViewHelper.Companion.onTextSubmit
import com.petrpol.rickandmortycharacters.utils.SnackBarHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_characters.*

@AndroidEntryPoint
class CharactersFragment : Fragment(), AdapterCallback {

    private val viewModel: CharactersViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupLayouts()
        setupSearchView()
    }

    private fun setupSearchView() {
        if (requireActivity() is MainActivity)
            (requireActivity() as MainActivity).searchedText.observe(viewLifecycleOwner,{ text -> viewModel.searchByName(text) })
    }

    private fun setupLayouts() {
        FragmentCharactersSwipeView.setOnRefreshListener {refresh()}
    }


    private fun setupObservers() {
        viewModel.charactersList.observe(viewLifecycleOwner,{ charactersList ->
            charactersAdapter.setNewDataSet(charactersList)
        })

        viewModel.dataStateNext.observe(viewLifecycleOwner,{ dataState ->
            when (dataState){
                is DataState.Loading -> {
                    FragmentCharactersSwipeView.isRefreshing = true
                }
                is DataState.Error -> {
                    FragmentCharactersSwipeView.isRefreshing = false
                    SnackBarHelper.showErrorSnack(dataState.exception,FragmentCharactersErrorLayout)
                }
                is DataState.Success -> {
                    FragmentCharactersSwipeView.isRefreshing = false
                }
            }

        })
    }

    private fun setupRecyclerView() {
        FragmentCharactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        FragmentCharactersRecyclerView.adapter = charactersAdapter
    }

    private fun refresh() {
        viewModel.refresh()
    }

    override fun itemSelected(id: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(getString(R.string.argument_id), id)
        }
        startActivity(intent)
    }

    override fun loadNextPage() {
        viewModel.loadNextPage()
    }
}