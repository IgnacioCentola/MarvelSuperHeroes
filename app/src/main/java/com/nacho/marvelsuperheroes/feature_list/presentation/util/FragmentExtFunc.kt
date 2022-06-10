package com.nacho.marvelsuperheroes.feature_list.presentation.util

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nacho.marvelsuperheroes.R

fun Fragment.isScreenPortrait() =
    this.activity?.applicationContext?.resources?.configuration?.orientation == Constants.SCREEN_ORIENTATION_PORTRAIT

fun Fragment.showErrorMessage(msg: String?) {
    Toast.makeText(requireContext(), msg ?: getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
}

fun Fragment.getLoadingDialog(): AlertDialog {
    val builder = AlertDialog.Builder(this.context)
    val inflater = requireActivity().layoutInflater

    builder.setView(inflater.inflate(R.layout.loading_dialog, null))

   return builder.create()
}