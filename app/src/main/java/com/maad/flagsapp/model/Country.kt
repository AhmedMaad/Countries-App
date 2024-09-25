package com.maad.flagsapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Country(
    @StringRes val name: Int,
    @DrawableRes val pic: Int,
    val lat: Double,
    val long: Double
)





