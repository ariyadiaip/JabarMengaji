package edu.ariyadi.jabarmengaji.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.data.model.Community
import edu.ariyadi.jabarmengaji.databinding.ItemCommunityBinding

class CommunityAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private var communityList = emptyList<Community>()

    inner class CommunityViewHolder(private val binding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(community: Community) {
            binding.tvCommunityName.text = community.name
            binding.tvCommunityAddress.text = community.address
            binding.tvCommunitySchedule.text = community.schedule

            //Mengonversi Base64 ke Bitmap untuk ImageView
            try {
                val decodedBytes = Base64.decode(community.base64Image, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                binding.ivCommunityImage.setImageBitmap(bitmap)
            } catch (e: IllegalArgumentException) {
                binding.ivCommunityImage.setImageResource(R.drawable.ic_launcher_foreground)
            }

            binding.root.setOnClickListener {
                onItemClick(community.urlMaps)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val currentCommunity = communityList[position]
        holder.bind(currentCommunity)
    }

    override fun getItemCount() = communityList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Community>) {
        communityList = newList
        notifyDataSetChanged()
    }

}