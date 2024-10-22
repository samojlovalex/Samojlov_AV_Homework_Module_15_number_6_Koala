package com.example.samojlov_av_homework_module_15_number_6_koala

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_15_number_6_koala.databinding.ActivityWeatherBinding
import com.example.samojlov_av_homework_module_15_number_6_koala.models.weather.CurrentWeather
import com.example.samojlov_av_homework_module_15_number_6_koala.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var locationIconButtonIV: ImageView
    private lateinit var cityIconButtonIV: ImageView
    private lateinit var cityTV: TextView
    private lateinit var containerWeatherImageLL: LinearLayout
    private lateinit var weatherIV: ImageView
    private lateinit var containerTemperatureLL: LinearLayout
    private lateinit var temperatureGeneralTV: TextView
    private lateinit var temperatureFeelsTV: TextView
    private lateinit var temperatureMinTV: TextView
    private lateinit var temperatureMaxTV: TextView
    private lateinit var containerWindLL: LinearLayout
    private lateinit var windSpeedTV: TextView
    private lateinit var windGustTV: TextView
    private lateinit var containerVisibilityLL: LinearLayout
    private lateinit var visibilityTV: TextView
    private lateinit var containerCloudsLL: LinearLayout
    private lateinit var cloudsTV: TextView
    private lateinit var containerPressureLL: LinearLayout
    private lateinit var pressureTV: TextView
    private lateinit var containerHumidityLL: LinearLayout
    private lateinit var humidityTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_weather)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.toolbar_title)
        toolbar.subtitle = getString(R.string.toolbar_subtitle)

        locationIconButtonIV = binding.locationIconButtonIV
        cityIconButtonIV = binding.cityIconButtonIV
        cityTV = binding.cityTV
        containerWeatherImageLL = binding.containerWeatherImageLL
        weatherIV = binding.weatherIV
        containerTemperatureLL = binding.containerTemperatureLL
        temperatureGeneralTV = binding.temperatureLayout.temperatureGeneralTV
        temperatureFeelsTV = binding.temperatureLayout.temperatureFeelsTV
        temperatureMinTV = binding.temperatureLayout.temperatureMinTV
        temperatureMaxTV = binding.temperatureLayout.temperatureMaxTV
        containerWindLL = binding.containerWindLL
        windSpeedTV = binding.windLayout.windSpeedTV
        windGustTV = binding.windLayout.windGustTV
        containerVisibilityLL = binding.containerVisibilityLL
        visibilityTV = binding.visibilityLayout.visibilityTV
        containerCloudsLL = binding.containerCloudsLL
        cloudsTV = binding.cloudsLayout.cloudsTV
        containerPressureLL = binding.containerPressureLL
        pressureTV = binding.pressureLayout.pressureTV
        containerHumidityLL = binding.containerHumidityLL
        humidityTV = binding.humidityLayout.humidityTV


        locationIconButtonIV.setOnClickListener {
            setLocation()
        }

        cityIconButtonIV.setOnClickListener {
            setCity()
        }

        getCurrentWeather("", "", getString(R.string.startCityLoad))
    }

    private fun setLocation() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogValues = inflater.inflate(R.layout.location_update, null)
        dialogBuilder.setView(dialogValues)
        dialogBuilder.setTitle(getString(R.string.setLocationTitleAlertDialog))

        val latitudeET = dialogValues.findViewById<EditText>(R.id.latitudeET)
        val longitudeET = dialogValues.findViewById<EditText>(R.id.longitudeET)

        dialogBuilder.setPositiveButton(getString(R.string.AlertDialogPositiveButtonText)) { _, _ ->
            val setLatitudeET = latitudeET.text.toString().trim()
            val setLongitudeET = longitudeET.text.toString().trim()
            getCurrentWeather(setLatitudeET, setLongitudeET, "")
        }

        dialogBuilder.setNegativeButton(getString(R.string.AlertDialogNegativeButtonText), null)
        dialogBuilder.create().show()
    }

    private fun setCity() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogValues = inflater.inflate(R.layout.city_update, null)
        dialogBuilder.setView(dialogValues)
        dialogBuilder.setTitle(getString(R.string.setCityTitleAlertDialog))

        val city = dialogValues.findViewById<EditText>(R.id.cityET)

        dialogBuilder.setPositiveButton(getString(R.string.AlertDialogPositiveButtonText)) { _, _ ->
            val setCity = city.text.toString().trim()
            getCurrentWeather("", "", setCity)
        }

        dialogBuilder.setNegativeButton(getString(R.string.AlertDialogNegativeButtonText), null)
        dialogBuilder.create().show()
    }

    private fun getCurrentWeather(lat: String, lon: String, city: String) {
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            if (city != "") {
                val response =
                    try {
                        RetrofitInstance.api.getCurrentWeatherCity(
                            city,
                            getString(R.string.metricUnits),
                            getString(R.string.lang_RU),
                            applicationContext.getString(R.string.apiKey)
                        )
                    } catch (e: IOException) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.app_error_Toast, e.message), Toast.LENGTH_LONG
                        ).show()
                        return@launch
                    } catch (e: HttpException) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.http_error_Toast, e.message), Toast.LENGTH_LONG
                        )
                            .show()
                        return@launch
                    }
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        currentWeather(response)
                    }
                }
            } else{
                val response =
                    try {
                        RetrofitInstance.api.getCurrentWeatherLocation(
                            lat,
                            lon,
                            getString(R.string.metricUnits),
                            getString(R.string.lang_RU),
                            applicationContext.getString(R.string.apiKey)
                        )
                    } catch (e: IOException) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.app_error_Toast, e.message), Toast.LENGTH_LONG
                        ).show()
                        return@launch
                    } catch (e: HttpException) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.http_error_Toast, e.message), Toast.LENGTH_LONG
                        )
                            .show()
                        return@launch
                    }
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        currentWeather(response)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun currentWeather(response: Response<CurrentWeather>) {
        val data = response.body()

        cityTV.text = data!!.name
        temperatureGeneralTV.text = "${data.main.temp} ℃"
        temperatureFeelsTV.text = "${data.main.feels_like} ℃"
        temperatureMinTV.text = "${data.main.temp_min} ℃"
        temperatureMaxTV.text = "${data.main.temp_max} ℃"
        windSpeedTV.text = "${data.wind.speed} м/с"
        windGustTV.text = "${data.wind.gust} м/с"
        visibilityTV.text = "${data.visibility} м"
        cloudsTV.text = "${data.clouds.all} %"
        pressureTV.text = "${data.main.pressure / 10} кПа"
        humidityTV.text = "${data.main.humidity} %"

        val iconId = data.weather[0].icon
        val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
        Picasso.get().load(imageUrl).into(weatherIV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}