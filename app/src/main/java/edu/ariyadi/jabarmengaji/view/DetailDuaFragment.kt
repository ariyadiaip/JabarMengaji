package edu.ariyadi.jabarmengaji.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.data.model.Dua
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.databinding.FragmentDetailDuaBinding
import edu.ariyadi.jabarmengaji.viewmodel.DetailDuaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailDuaFragment : Fragment() {

    private var _binding: FragmentDetailDuaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailDuaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            mainActivity.onBackPressed()
        }

        observeDuaDetail()
    }

    private fun observeDuaDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.duaDetail.collectLatest { dua ->
                dua?.let {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(dua: DuaEntity) {
        binding.tvDuaTitle.text = dua.nama
        binding.tvDuaArabic.text = dua.ar
        binding.tvDuaLatin.text = dua.tr
        binding.tvDuaTranslation.text = dua.idn
        binding.tvDuaAbout.text = dua.tentang
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}