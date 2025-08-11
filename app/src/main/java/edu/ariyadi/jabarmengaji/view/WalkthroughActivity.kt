package edu.ariyadi.jabarmengaji.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.adapter.SliderAdapter
import edu.ariyadi.jabarmengaji.data.model.WalkthroughItem
import edu.ariyadi.jabarmengaji.databinding.ActivityWalkthroughBinding

class WalkthroughActivity : AppCompatActivity() {

    private var _binding: ActivityWalkthroughBinding? = null
    private val binding get() = _binding!!
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var walkthroughItems: List<WalkthroughItem>

    private var dotImages: Array<ImageView?> = arrayOfNulls(0)

    var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWalkthroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walkthroughItems = listOf(
            WalkthroughItem(
                R.drawable.img_mosque,
                R.string.title_walkthrough_1,
                R.string.desc_walkthrough_1,
                R.color.walkthrough_1
            ),
            WalkthroughItem(
                R.drawable.img_compass,
                R.string.title_walkthrough_2,
                R.string.desc_walkthrough_2,
                R.color.walkthrough_2
            ),
            WalkthroughItem(
                R.drawable.img_quran,
                R.string.title_walkthrough_3,
                R.string.desc_walkthrough_3,
                R.color.walkthrough_3
            )
        )

        sliderAdapter = SliderAdapter(this, walkthroughItems)
        binding.slideViewPager.adapter = sliderAdapter

        addDotsIndicator(0)
        binding.mainWalkthrough.setBackgroundColor(ContextCompat.getColor(this, walkthroughItems[0].backgroundColor))

        binding.slideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Not used for this implementation
            }

            override fun onPageSelected(position: Int) {
                addDotsIndicator(position)
                currentPosition = position

                val backgroundColor = ContextCompat.getColor(this@WalkthroughActivity, walkthroughItems[position].backgroundColor)
                binding.mainWalkthrough.setBackgroundColor(backgroundColor)

                if (position == 0) {
                    binding.backButton.visibility = View.INVISIBLE
                } else {
                    binding.backButton.visibility = View.VISIBLE
                }

                if (position == walkthroughItems.size - 1) {
                    binding.nextButton.text = getString(R.string.finish)
                    binding.skipButton.visibility = View.INVISIBLE
                } else {
                    binding.nextButton.text = resources.getString(R.string.next)
                    binding.skipButton.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not used for this implementation
            }
        })

        binding.nextButton.setOnClickListener {
            if (currentPosition < walkthroughItems.size - 1) {
                binding.slideViewPager.currentItem = currentPosition + 1
            } else {
                navigateToMainActivity()
            }
        }

        binding.backButton.setOnClickListener {
            binding.slideViewPager.currentItem = currentPosition - 1
        }

        binding.skipButton.setOnClickListener {
            navigateToMainActivity()
        }

    }

    private fun addDotsIndicator(position: Int) {
        binding.dotIndicator.removeAllViews()
        dotImages = arrayOfNulls(walkthroughItems.size)

        for (i in dotImages.indices) {
            dotImages[i] = ImageView(this).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, 0, 8, 0)
                layoutParams = params

                if (i == position) {
                    setImageResource(R.drawable.ic_dot_active)
                } else {
                    setImageResource(R.drawable.ic_dot_inactive)
                }
            }
            binding.dotIndicator.addView(dotImages[i])
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}