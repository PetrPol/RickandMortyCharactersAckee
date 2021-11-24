package com.petrpol.rickandmortycharacters.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.petrpol.rickandmortycharacters.R
import dagger.hilt.android.AndroidEntryPoint

/** Activity to show detail about character
 *  Contains FragmentViewContainer to show fragment */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /** Finish activity when back button selected */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            this.finish()
            return true
        }

        return super.onContextItemSelected(item)
    }
}