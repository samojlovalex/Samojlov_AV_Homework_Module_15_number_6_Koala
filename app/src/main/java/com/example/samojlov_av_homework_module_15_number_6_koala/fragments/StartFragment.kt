package com.example.samojlov_av_homework_module_15_number_6_koala.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.samojlov_av_homework_module_15_number_6_koala.WeatherActivity
import com.example.samojlov_av_homework_module_15_number_6_koala.databinding.FragmentStartBinding
import com.example.samojlov_av_homework_module_15_number_6_koala.models.StartPage
import com.google.gson.Gson
import kotlin.reflect.javaType

import kotlin.reflect.typeOf


@OptIn(ExperimentalStdlibApi::class)
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private lateinit var startInfoTextViewTV: TextView
    private lateinit var startImageIV: ImageView
    private lateinit var startImageTwoIV: ImageView
    private lateinit var startButtonBT: Button
    private var check = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        startInfoTextViewTV = binding.startInfoTextViewTV
        startImageIV = binding.startImageIV
        startImageTwoIV = binding.startImageTwoIV
        startButtonBT = binding.startButtonBT

        val gson: String? = arguments?.getString("startvp")
        val type = typeOf<StartPage>().javaType
        val viewPagerItem = Gson().fromJson<StartPage>(gson, type)

        startInfoTextViewTV.text = viewPagerItem.description
        startImageIV.setImageResource(viewPagerItem.image)
        startImageTwoIV.setImageResource(viewPagerItem.imageTwo)
        check = viewPagerItem.check

        if (!check) {
            startButtonBT.visibility = View.VISIBLE
            startImageTwoIV.visibility = View.VISIBLE
            startButtonBT.setOnClickListener {
                startActivity(Intent(activity, WeatherActivity::class.java))
            }
        }
    }
}