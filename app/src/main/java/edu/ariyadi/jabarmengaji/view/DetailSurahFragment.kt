package edu.ariyadi.jabarmengaji.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.adapter.AyatAdapter
import edu.ariyadi.jabarmengaji.data.model.DetailSurah
import edu.ariyadi.jabarmengaji.databinding.FragmentDetailSurahBinding
import edu.ariyadi.jabarmengaji.viewmodel.DetailSurahViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailSurahFragment : Fragment() {

    private var _binding: FragmentDetailSurahBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailSurahViewModel by viewModels()
    private lateinit var ayatAdapter: AyatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailSurahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.btnBack.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.onBackPressed()
        }

        binding.tvTitleSurah.text = arguments?.getString("surahName")
    }

    private fun setupRecyclerView() {
        ayatAdapter = AyatAdapter()
        binding.rvAyatList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ayatAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.surahDetail.collectLatest { detail ->
                detail?.let {
                    updateUI(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun updateUI(detail: DetailSurah) {
        binding.tvLatinName.text = detail.namaLatin
        binding.tvMeaning.text = detail.arti
        binding.tvNumberOfAyatPlace.text = "${detail.jumlahAyat} Ayat | ${detail.tempatTurun}"
        binding.tvArabicName.text = detail.nama

        //Tampilkan Bismillah jika bukan Al-Fatihah atau At-Taubah
        if (detail.nomor != 1 && detail.nomor != 9) {
            binding.tvBismillah.visibility = View.VISIBLE
        } else {
            binding.tvBismillah.visibility = View.GONE
        }

        ayatAdapter.setData(detail.ayat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}