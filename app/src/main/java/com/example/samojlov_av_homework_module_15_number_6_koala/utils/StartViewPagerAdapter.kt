package com.example.samojlov_av_homework_module_15_number_6_koala.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.samojlov_av_homework_module_15_number_6_koala.fragments.StartFragment
import com.example.samojlov_av_homework_module_15_number_6_koala.models.StartPage
import com.google.gson.Gson
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class StartViewPagerAdapter(
    fragment: FragmentActivity,
    private val viewPagerList: MutableList<StartPage>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return viewPagerList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = StartFragment()
        val type = typeOf<StartPage>().javaType
        val gson = Gson().toJson(viewPagerList[position], type)
        fragment.arguments = bundleOf("startvp" to gson)
        return fragment
    }
}