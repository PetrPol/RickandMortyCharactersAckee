package com.petrpol.rickandmortycharacters.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.ui.adapters.AdapterCallback
import com.petrpol.rickandmortycharacters.ui.adapters.CharactersAdapter
import com.petrpol.rickandmortycharacters.ui.detail.DetailActivity
import com.petrpol.rickandmortycharacters.utils.DataState
import com.petrpol.rickandmortycharacters.utils.SearchViewHelper.Companion.onTextSubmit
import com.petrpol.rickandmortycharacters.utils.SnackBarHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_characters.*

/** Fragment to get search and show characters from server */
@AndroidEntryPoint
class CharactersFragment : Fragment(), AdapterCallback {

    private val viewModel: CharactersViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter(this)
    private var lastCalledId = -1

    //Launcher for Detail Activity to handle result
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result -> onResult(result)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupLayouts()
    }

    /** Inflates menu with search */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_activity_menu, menu)
        val searchView = menu.findItem(R.id.MainActivityMenuSearch)?.actionView as SearchView
        searchView.onTextSubmit {text -> viewModel.searchByName(text) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    /** Setups Swipe refresh view */
    private fun setupLayouts() {
        FragmentCharactersSwipeView.setOnRefreshListener {refresh()}
    }

    private fun setupObservers() {

        //Sets new dataset to adapter
        viewModel.charactersList.observe(viewLifecycleOwner, { charactersList ->
            charactersAdapter.setNewDataSet(charactersList)
        })

        //Data state handle
        viewModel.dataStateNext.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
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

    /** Setups Layout manager and adapter for recycler view */
    private fun setupRecyclerView() {
        FragmentCharactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        FragmentCharactersRecyclerView.adapter = charactersAdapter
    }

    /** Refreshes whole list */
    private fun refresh() {
        viewModel.refresh()
    }

    /** Handles list item selected. Shows detail activity with character id */
    override fun itemSelected(id: Int) {
        lastCalledId = id
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(getString(R.string.argument_id), id)
        }
        resultLauncher.launch(intent)
    }

    /** Handle favourite change in detail activity without reloading whole list */
    private fun onResult(result: ActivityResult) {
        if (result.resultCode == resources.getInteger(R.integer.favourite_result_code))
            viewModel.setFavourite(true,lastCalledId)
        else if (result.resultCode == resources.getInteger(R.integer.favourite_no_result_code))
            viewModel.setFavourite(false,lastCalledId)
    }

    /** Loads next page from Server */
    override fun loadNextPage() {
        viewModel.loadNextPage()
    }
}