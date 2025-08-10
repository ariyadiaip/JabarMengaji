package edu.ariyadi.jabarmengaji.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ariyadi.jabarmengaji.adapter.DuaAdapter
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.databinding.FragmentDuaBinding
import edu.ariyadi.jabarmengaji.viewmodel.DuaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DuaFragment : Fragment() {

    private var _binding: FragmentDuaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DuaViewModel by viewModels()
    private lateinit var duaAdapter: DuaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        duaAdapter = DuaAdapter { dua ->
            navigateToDetail(dua)
        }
        binding.rvDuaList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = duaAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearchDua.doAfterTextChanged { editable ->
            val query = editable.toString()
            viewModel.searchDua(query)
        }

        binding.etSearchDua.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearchDua.windowToken, 0)
                binding.etSearchDua.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.duaList.collectLatest { list ->
                duaAdapter.setData(list)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun navigateToDetail(dua: DuaEntity) {
        val mainActivity = requireActivity() as MainActivity
        val detailDuaFragment = DetailDuaFragment()

        val bundle = Bundle().apply {
            putInt("duaId", dua.id)
        }
        detailDuaFragment.arguments = bundle

        mainActivity.navigateToFragment(detailDuaFragment)
        mainActivity.hideBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}