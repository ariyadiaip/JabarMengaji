package edu.ariyadi.jabarmengaji.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.derysudrajat.compassqibla.CompassQibla
import edu.ariyadi.jabarmengaji.databinding.FragmentQiblaBinding

@AndroidEntryPoint
class QiblaFragment : Fragment() {

    private var _binding: FragmentQiblaBinding? = null
    private val binding get() = _binding

    private var currentCompassDegree = 0f
    private var currentNeedleDegree = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQiblaBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBack?.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.showBottomNav()
            mainActivity.loadFragment(ScheduleFragment())
        }

        CompassQibla.Builder(requireActivity() as AppCompatActivity).onPermissionGranted { permission ->
            Toast.makeText(requireContext(), "onPermissionGranted $permission", Toast.LENGTH_SHORT).show()
        }.onPermissionDenied {
            Toast.makeText(requireContext(), "onPermissionDenied", Toast.LENGTH_SHORT).show()
        }.onGetLocationAddress { address ->
            binding?.let {
                it.tvLocation.text = buildString {
                    append(address.locality)
                    append(", ")
                    append(address.subAdminArea)
                }
            }
        }.onDirectionChangeListener { qiblaDirection ->
            binding?.let {
                it.tvDirection.text = if (qiblaDirection.isFacingQibla) "Anda Menghadap Kiblat"
                else "${qiblaDirection.needleAngle.toInt()}°"

                val rotateCompass = RotateAnimation(
                    currentCompassDegree, qiblaDirection.compassAngle, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    duration = 1000
                }
                currentCompassDegree = qiblaDirection.compassAngle

                it.ivCompass.startAnimation(rotateCompass)

                val rotateNeedle = RotateAnimation(
                    currentNeedleDegree, qiblaDirection.needleAngle, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ).apply {
                    duration = 1000
                }
                currentNeedleDegree = qiblaDirection.needleAngle

                it.ivNeedle.startAnimation(rotateNeedle)
            }
        }.build()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}