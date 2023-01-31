package com.david.metropolitanmuseumofart.presentation_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.david.metropolitanmuseumofart.presentation_detail.databinding.ListImageSliderItemBinding

class ImageSliderAdapter(private var imageList: List<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ListImageSliderItemBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )
        binding.imageUrl = imageList[position]
        container.addView(binding.root)
        return binding.root
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

}