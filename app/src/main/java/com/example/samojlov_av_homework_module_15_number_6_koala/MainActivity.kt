package com.example.samojlov_av_homework_module_15_number_6_koala

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.samojlov_av_homework_module_15_number_6_koala.databinding.ActivityMainBinding
import com.example.samojlov_av_homework_module_15_number_6_koala.models.StartPage
import com.example.samojlov_av_homework_module_15_number_6_koala.utils.StartViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StartViewPagerAdapter
    private lateinit var startPageViewPagerVP: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }
    private fun init(){
        startPageViewPagerVP = binding.startPageViewPagerVP
        adapter = StartViewPagerAdapter(this, StartPage.startPageList)
        startPageViewPagerVP.adapter = adapter
    }
}