package com.nacho.marvelsuperheroes.feature_list.presentation.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.isScreenPortrait() =
    this.activity?.applicationContext?.resources?.configuration?.orientation == Constants.SCREEN_ORIENTATION_PORTRAIT

fun Fragment.showErrorMessage(msg: String?) {
    Toast.makeText(requireContext(), msg ?: "Unknown error", Toast.LENGTH_LONG).show()
}