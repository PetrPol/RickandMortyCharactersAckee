package com.petrpol.rickandmortycharacters.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.petrpol.rickandmortycharacters.R
import com.petrpol.rickandmortycharacters.utils.DataState
import com.petrpol.rickandmortycharacters.utils.SnackBarHelper
import com.squareup.picasso.Picasso

/** Bindings adapters for data binding */
object BindingAdapters {

    /** Binds text to textview. If is null sets text to "-" */
    @BindingAdapter("android:text")
    @JvmStatic fun bindText(view: TextView, text: String?){
        if (text==null || text.isEmpty())
            view.text = "-"
        else
            view.text = text
    }

    /** Gets image from url and sets it to view */
    @BindingAdapter("app:url")
    @JvmStatic fun bindImageUrl(view: ImageView, url: String?){
        if (url==null)
            view.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    R.drawable.character_image_placeholder
                )
            )
        else
            Picasso.get().load(url).into(view)
    }

    /** Creates SnackBar error in view if dataState is DataState.Error */
    @BindingAdapter("app:error")
    @JvmStatic fun bindError(view: View, dataState: DataState<Boolean>?){
        if (dataState is DataState.Error)
            SnackBarHelper.showErrorSnack(dataState.exception,view)
    }

    /** Shows loading icon if is DataState.Loading */
    @BindingAdapter("app:isRefreshing")
    @JvmStatic fun bindLoading(view: SwipeRefreshLayout, dataState: DataState<Boolean>?){
        view.isRefreshing = dataState is DataState.Loading

    }
}