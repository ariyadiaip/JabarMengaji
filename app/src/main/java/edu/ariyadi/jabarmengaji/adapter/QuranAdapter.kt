package edu.ariyadi.jabarmengaji.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ariyadi.jabarmengaji.data.model.Surah
import edu.ariyadi.jabarmengaji.databinding.ItemSurahBinding

class QuranAdapter(
    private val onItemClick: (Surah) -> Unit,
    private val onItemLongClick: (Surah) -> Unit
) : RecyclerView.Adapter<QuranAdapter.QuranViewHolder>() {

    private var surahList = emptyList<Surah>()

    inner class QuranViewHolder(private val binding: ItemSurahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(surah: Surah) {
            binding.tvSurahNumber.text = surah.nomor.toString()
            binding.tvSurahLatinName.text = surah.namaLatin
            binding.tvSurahPlaceMeaning.text = "${surah.tempatTurun} • ${surah.arti}"
            binding.tvSurahArabicName.text = surah.nama

            binding.root.setOnClickListener {
                onItemClick(surah)
            }
            binding.root.setOnLongClickListener {
                onItemLongClick(surah)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranViewHolder {
        val binding = ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuranViewHolder, position: Int) {
        val currentSurah = surahList[position]
        holder.bind(currentSurah)
    }

    override fun getItemCount() = surahList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Surah>) {
        surahList = newList
        notifyDataSetChanged()
    }
}