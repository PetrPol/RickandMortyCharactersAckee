package com.petrpol.rickandmortycharacters.ui.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

/** Fragment used to show detail info about character
 *  Uses data binding for character info */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var characterId: Int = -1
    private var favouriteMenuItem : MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate data binding
        setHasOptionsMenu(true)
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    /** Sets menu with favourite button */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        favouriteMenuItem = menu.findItem(R.id.detail_menu_favourite)
        setupObservers()
        setupLayouts()
        super.onCreateOptionsMenu(menu, inflater)
    }

    /** Setups Swipe refresh view */
    private fun setupLayouts() {
        FragmentDetailSwipeView.setOnRefreshListener { viewModel.getCharacter(characterId) }
    }

    /** Handles favourite button press */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.detail_menu_favourite) {
            viewModel.favouriteChange()
        }

        return super.onOptionsItemSelected(item)
    }

    /** Gets character id from bundle and calls get info function */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterId = requireActivity().intent.getIntExtra(getString(R.string.argument_id), -1)
        if (characterId == -1)
            requireActivity().finish()
        viewModel.getCharacter(characterId)
    }


    private fun setupObservers() {
        //Set title name
        viewModel.character.observe(viewLifecycleOwner,
            { character -> requireActivity().title = character.name })

        //Set favourite button view and result code
        viewModel.favourite.observe(viewLifecycleOwner, { favourite ->
            if (favourite) {
                favouriteMenuItem?.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic__24_favorites_active)
                requireActivity().setResult(resources.getInteger(R.integer.favourite_result_code))
            } else {
                favouriteMenuItem?.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_24_favorites_inactive)
                requireActivity().setResult(resources.getInteger(R.integer.favourite_no_result_code))
            }
        })
    }


}