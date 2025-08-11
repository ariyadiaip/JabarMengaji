package edu.ariyadi.jabarmengaji.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import edu.ariyadi.jabarmengaji.data.model.WalkthroughItem
import edu.ariyadi.jabarmengaji.databinding.SliderScreenBinding

class SliderAdapter(private val context: Context, private val walkthroughItems: List<WalkthroughItem>) : PagerAdapter() {

    override fun getCount(): Int {
        return walkthroughItems.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = SliderScreenBinding.inflate(LayoutInflater.from(context), container, false)

        val item = walkthroughItems[position]

        binding.sliderImage.setImageResource(item.image)
        binding.sliderTitle.setText(item.title)

        val descriptionText = context.getString(item.description)
        binding.sliderDesc.text = descriptionText

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}