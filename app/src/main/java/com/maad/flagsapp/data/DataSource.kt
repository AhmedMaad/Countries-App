    package com.maad.flagsapp.data

    import com.maad.flagsapp.R
    import com.maad.flagsapp.model.Country

    class DataSource {

        fun getCountriesData() =
            listOf(
                Country(R.string.palestine, R.drawable.palestinian_flag, 31.9522, 35.2332),
                Country(R.string.egypt, R.drawable.egyptian_flag, 26.8206, 30.8025),
                Country(R.string.russia, R.drawable.russian_flag, 61.5240, 105.3188),
                Country(R.string.madagascar, R.drawable.madagascar_flag, 18.7669, 46.8691)
            )

    }