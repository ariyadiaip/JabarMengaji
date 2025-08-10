package edu.ariyadi.jabarmengaji.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ariyadi.jabarmengaji.data.model.DuaEntity
import edu.ariyadi.jabarmengaji.databinding.ItemDuaBinding

class DuaAdapter(private val onItemClick: (DuaEntity) -> Unit) :
    RecyclerView.Adapter<DuaAdapter.DuaViewHolder>() {

    private var duaList = emptyList<DuaEntity>()

    inner class DuaViewHolder(private val binding: ItemDuaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dua: DuaEntity) {
            binding.tvDuaNumber.text = dua.id.toString()
            binding.tvDuaTitle.text = dua.nama
            binding.root.setOnClickListener {
                onItemClick(dua)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuaViewHolder {
        val binding = ItemDuaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DuaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DuaViewHolder, position: Int) {
        val currentDua = duaList[position]
        holder.bind(currentDua)
    }

    override fun getItemCount() = duaList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<DuaEntity>) {
        duaList = newList
        notifyDataSetChanged()
    }

}