package com.petrpol.rickandmortycharacters.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.model.CharacterObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_list_item.view.*
import kotlinx.coroutines.*
import java.util.*

/**
 * Recycler adapter for list of album on dashboard
 */
class CharactersAdapter (private val adapterCallback: AdapterCallback): RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var characters: List<CharacterObject> = ArrayList<CharacterObject>()
    private var loading = true

    /** View holder for album preview */
    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterName : TextView = itemView.findViewById(R.id.CharacterListItemName)
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
        Picasso.get().load(actualCharacter.image).into(viewHolder.characterImage)

        //On click listener
        viewHolder.itemView.setOnClickListener{adapterCallback.itemSelected(actualCharacter.id)}

        //Pagination test
        if ((!loading) && characters.size-position <= 10) {
            adapterCallback.loadNextPage()
            loading = true
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    /** Sets new data set and notify about change */
    fun setNewDataSet(newCharacterObjects: List<CharacterObject>){
        characters = newCharacterObjects
        notifyDataSetChanged()
        loading = false
    }
}