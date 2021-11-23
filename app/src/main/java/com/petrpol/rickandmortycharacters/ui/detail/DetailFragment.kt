package com.petrpol.rickandmortycharacters.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var characterId: Int = -1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate data binding
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterId = requireActivity().intent.getIntExtra(getString(R.string.argument_id),-1)
        if (characterId == -1)
            requireActivity().finish()
        viewModel.getCharacter(characterId)

        viewModel.character.observe(viewLifecycleOwner,{character -> requireActivity().title = character.name })
    }

}