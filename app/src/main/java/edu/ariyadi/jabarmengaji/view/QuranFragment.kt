package edu.ariyadi.jabarmengaji.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.adapter.QuranAdapter
import edu.ariyadi.jabarmengaji.data.model.Surah
import edu.ariyadi.jabarmengaji.databinding.FragmentQuranBinding
import edu.ariyadi.jabarmengaji.viewmodel.QuranViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuranFragment : Fragment() {

    private var _binding: FragmentQuranBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuranViewModel by viewModels()
    private lateinit var quranAdapter: QuranAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        quranAdapter = QuranAdapter(
            onItemClick = { surah ->
                navigateToDetail(surah)
            },
            onItemLongClick = { surah ->
                showSurahDescriptionDialog(surah.namaLatin, surah.deskripsi)
            }
        )
        binding.rvSurahList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quranAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearchSurah.doAfterTextChanged { editable ->
            val query = editable.toString()
            viewModel.searchSurah(query)
        }

        binding.etSearchSurah.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearchSurah.windowToken, 0)
                binding.etSearchSurah.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.surahList.collectLatest { list ->
                quranAdapter.setData(list)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showSurahDescriptionDialog(surahTitle: String, description: String) {

        val formattedDescription = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(description)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(surahTitle)
            .setMessage(formattedDescription)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun navigateToDetail(surah: Surah) {
        val mainActivity = requireActivity() as MainActivity
        val detailSurahFragment = DetailSurahFragment()

        val bundle = Bundle().apply {
            putInt("surahNumber", surah.nomor)
            putString("surahName", surah.namaLatin)
        }
        detailSurahFragment.arguments = bundle
        mainActivity.navigateToFragment(detailSurahFragment)
        mainActivity.hideBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}