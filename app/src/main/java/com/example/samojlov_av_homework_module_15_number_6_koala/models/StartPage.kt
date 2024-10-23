package com.example.samojlov_av_homework_module_15_number_6_koala.models

import com.example.samojlov_av_homework_module_15_number_6_koala.R

data class StartPage(
    val description: String,
    val image: Int,
    val imageTwo: Int,
    val check: Boolean
) {
    companion object {
        val startPageList = mutableListOf(
            StartPage(
                "Добро пожаловать в приложение прогноза погоды",
                R.drawable.one,
                R.drawable.city_icon,
                true
            ),
            StartPage(
                "В нашем приложении отображаются актуальные данные погоды по 200 000 городам мира",
                R.drawable.two,
                R.drawable.city_icon,
                true
            ),
            StartPage(
                "Для получения данных о погоде выберете текущее местонахождение или название города",
                R.drawable.three,
                R.drawable.city_icon,
                false
            )
        )
    }
}