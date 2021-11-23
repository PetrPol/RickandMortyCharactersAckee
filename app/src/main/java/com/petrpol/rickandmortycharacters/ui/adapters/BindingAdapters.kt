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

object BindingAdapters {

    @BindingAdapter("android:text")
    @JvmStatic fun bindText(view: TextView, text: String?){
        if (text==null || text.isEmpty())
            view.text = "-"
        else
            view.text = text
    }

    @BindingAdapter("app:url")
    @JvmStatic fun bindImageUrl(view: ImageView, url: String?){
        if (url==null)
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_account_circle_24))
        else
            Picasso.get().load(url).into(view)
    }

    @BindingAdapter("app:error")
    @JvmStatic fun bindError(view: View, dataState: DataState<Boolean>?){
        if (dataState is DataState.Error)
            SnackBarHelper.showErrorSnack(dataState.exception,view)
    }

    @BindingAdapter("app:isRefreshing")
    @JvmStatic fun bindLoading(view: SwipeRefreshLayout, dataState: DataState<Boolean>?){
        view.isRefreshing = dataState is DataState.Loading

    }
}