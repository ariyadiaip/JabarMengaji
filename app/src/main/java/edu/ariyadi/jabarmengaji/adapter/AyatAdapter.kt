package edu.ariyadi.jabarmengaji.adapter

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ariyadi.jabarmengaji.R
import edu.ariyadi.jabarmengaji.data.model.Ayat
import edu.ariyadi.jabarmengaji.databinding.ItemAyatBinding

class AyatAdapter : RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {

    private var ayatList = emptyList<Ayat>()
    private var mediaPlayer: MediaPlayer? = null
    private var playingAyatPosition: Int = -1

    inner class AyatViewHolder(private val binding: ItemAyatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ayat: Ayat, position: Int) {
            binding.tvAyatNumber.text = ayat.nomorAyat.toString()
            binding.tvAyatArabic.text = ayat.teksArab
            binding.tvAyatLatin.text = ayat.teksLatin
            binding.tvAyatTranslation.text = ayat.teksIndonesia

            if (position == playingAyatPosition) {
                binding.ivPlayPause.setImageResource(R.drawable.ic_pause)
            } else {
                binding.ivPlayPause.setImageResource(R.drawable.ic_play)
            }

            binding.ivPlayPause.setOnClickListener {
                if (position == playingAyatPosition) {
                    pauseAudio()
                } else {
                    playAudio(ayat.audio["01"], position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val binding = ItemAyatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AyatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        val currentAyat = ayatList[position]
        holder.bind(currentAyat, position)
    }

    override fun getItemCount() = ayatList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Ayat>) {
        ayatList = newList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun playAudio(audioUrl: String?, position: Int) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
            }
        }

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                start()
                playingAyatPosition = position
                notifyDataSetChanged()
            }
            setOnCompletionListener {
                playingAyatPosition = -1
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun pauseAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        playingAyatPosition = -1
        notifyDataSetChanged()
    }
}