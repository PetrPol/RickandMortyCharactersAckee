package com.petrpol.rickandmortycharacters.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.petrpol.rickandmortycharacters.R
import java.lang.Exception

/** SnackBar support class
 *  Extends snackBar show functions */
class SnackBarHelper {

    companion object{
        /** Shows error snackBar with encoded exception
         * @param exception - Error exception
         * @param view - Target vide to show SnackBar*/
        fun showErrorSnack(exception: Exception, view: View){
            val snackBar = Snackbar.make(view, decodeException(exception,view.context), Snackbar.LENGTH_SHORT)
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(view.context,R.color.errorRed))
            snackBar.show()
        }

        /** Decodes exception to presentable String message to user */
        private fun decodeException(exception: Exception, context: Context): String{
            if (exception.message==null)
                return context.getString(R.string.unknown_message)

            if (exception.message.toString().contains(context.getString(R.string.timeout_code)))
                return context.getString(R.string.no_internet_message)

            if (exception.message.toString().contains(context.getString(R.string.no_found_code)))
                return context.getString(R.string.no_found_message)

            return exception.message.toString()

        }
    }
}