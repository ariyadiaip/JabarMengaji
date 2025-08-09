package edu.ariyadi.jabarmengaji.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.data.model.Jadwal
import edu.ariyadi.jabarmengaji.databinding.FragmentScheduleBinding
import edu.ariyadi.jabarmengaji.utils.Constants
import edu.ariyadi.jabarmengaji.viewmodel.ScheduleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScheduleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        observeViewModel()

        binding.tvDate.text = viewModel.getFormattedDate()

        binding.btnQibla.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.hideBottomNav()
            mainActivity.loadFragment(QiblaFragment())
        }

    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_custom,
            Constants.JABAR_LOCATIONS.map { it.lokasi }
        )
        binding.spinKota.adapter = adapter

        binding.spinKota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLocation = Constants.JABAR_LOCATIONS[position]
                viewModel.fetchSholatSchedule(selectedLocation.id)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @SuppressLint("NewApi")
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedLocation.collectLatest { lokasi ->
                lokasi?.let {
                    val defaultPosition = Constants.JABAR_LOCATIONS.indexOfFirst { l -> l.id == it.id }
                    if (defaultPosition != -1) {
                        binding.spinKota.setSelection(defaultPosition, false)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.jadwal.collectLatest { jadwal ->
                jadwal?.let {
                    updateScheduleUI(it)
                    viewModel.calculateNextPrayerTime(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentNextPrayerInfo.collectLatest { info ->
                info?.let {
                    binding.tvCurrentNextPrayer.text = it.first
                    binding.tvCurrentNextTime.text = it.second
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nextPrayerName.collectLatest { prayerName ->
                prayerName?.let {
                    updatePrayerBackground(it)
                }
            }
        }
    }

    private fun updateScheduleUI(jadwal: Jadwal) {
        binding.tvTimeImsak.text = jadwal.imsak
        binding.tvTimeSubuh.text = jadwal.subuh
        binding.tvTimeTerbit.text = jadwal.terbit
        binding.tvTimeDzuhur.text = jadwal.dzuhur
        binding.tvTimeAshar.text = jadwal.ashar
        binding.tvTimeMaghrib.text = jadwal.maghrib
        binding.tvTimeIsya.text = jadwal.isya
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updatePrayerBackground(nextPrayer: String) {
        // Reset background
        binding.llImsak.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llSubuh.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llTerbit.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llDzuhur.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llAshar.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llMaghrib.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)
        binding.llIsya.background = resources.getDrawable(R.drawable.bg_schedule_normal, null)

        // Reset warna teks
        binding.tvImsak.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeImsak.setTextColor(resources.getColor(R.color.black, null))
        binding.tvSubuh.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeSubuh.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTerbit.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeTerbit.setTextColor(resources.getColor(R.color.black, null))
        binding.tvDzuhur.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeDzuhur.setTextColor(resources.getColor(R.color.black, null))
        binding.tvAshar.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeAshar.setTextColor(resources.getColor(R.color.black, null))
        binding.tvMaghrib.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeMaghrib.setTextColor(resources.getColor(R.color.black, null))
        binding.tvIsya.setTextColor(resources.getColor(R.color.black, null))
        binding.tvTimeIsya.setTextColor(resources.getColor(R.color.black, null))

        val activeBackground = resources.getDrawable(R.drawable.bg_schedule_active, null)
        val activeTextColor = resources.getColor(R.color.green_700, null)

        when (nextPrayer) {
            "Imsak" -> {
                binding.llImsak.background = activeBackground
                binding.tvImsak.setTextColor(activeTextColor)
                binding.tvTimeImsak.setTextColor(activeTextColor)
            }

            "Subuh" -> {
                binding.llSubuh.background = activeBackground
                binding.tvSubuh.setTextColor(activeTextColor)
                binding.tvTimeSubuh.setTextColor(activeTextColor)
            }

            "Terbit" -> {
                binding.llTerbit.background = activeBackground
                binding.tvTerbit.setTextColor(activeTextColor)
                binding.tvTimeTerbit.setTextColor(activeTextColor)
            }

            "Dzuhur" -> {
                binding.llDzuhur.background = activeBackground
                binding.tvDzuhur.setTextColor(activeTextColor)
                binding.tvTimeDzuhur.setTextColor(activeTextColor)
            }

            "Ashar" -> {
                binding.llAshar.background = activeBackground
                binding.tvAshar.setTextColor(activeTextColor)
                binding.tvTimeAshar.setTextColor(activeTextColor)
            }

            "Maghrib" -> {
                binding.llMaghrib.background = activeBackground
                binding.tvMaghrib.setTextColor(activeTextColor)
                binding.tvTimeMaghrib.setTextColor(activeTextColor)
            }

            "Isya" -> {
                binding.llIsya.background = activeBackground
                binding.tvIsya.setTextColor(activeTextColor)
                binding.tvTimeIsya.setTextColor(activeTextColor)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}