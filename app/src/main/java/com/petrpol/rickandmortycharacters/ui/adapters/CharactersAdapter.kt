package com.petrpol.rickandmortycharacters.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.squareup.picasso.Picasso

/**
 * Recycler adapter for list of characters on dashboard
 */
class CharactersAdapter (private val adapterCallback: AdapterCallback): RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var characters: ArrayList<CharacterObject> = ArrayList()
    private var loading = true

    /** View holder for character preview */
    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterName : TextView = itemView.findViewById(R.id.CharacterListItemName)
        val characterFavourite : ImageView = itemView.findViewById(R.id.CharacterListItemFavourite)
        val characterStatus : TextView = itemView.findViewById(R.id.CharacterListItemStatus)
        val characterImage : ImageView = itemView.findViewById(R.id.CharacterListItemImage)
    }

    /** Creates layout */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(view)
    }

    /** Binds object and listener to item view  */
    override fun onBindViewHolder(viewHolder: CharacterViewHolder, position: Int) {
        val actualCharacter = characters[position]

        viewHolder.characterName.text = actualCharacter.name
        viewHolder.characterStatus.text = actualCharacter.status
        if (actualCharacter.favourite)
            viewHolder.characterFavourite.visibility = View.VISIBLE
        else
            viewHolder.characterFavourite.visibility = View.GONE

        Picasso.get().load(actualCharacter.image).into(viewHolder.characterImage)

        //On click listener
        viewHolder.itemView.setOnClickListener{adapterCallback.itemSelected(actualCharacter.id)}

        //Pagination test
        if ((!loading) && characters.size-position <= 10) {
            adapterCallback.loadNextPage()
            loading = true
        }
    }

    /** Returns actual number of items */
    override fun getItemCount(): Int {
        return characters.size
    }

    /** Sets new data set and notify about change */
    @SuppressLint("NotifyDataSetChanged")
    fun setNewDataSet(newCharacterObjects: ArrayList<CharacterObject>){
        characters = newCharacterObjects
        notifyDataSetChanged()
        loading = false
    }
}