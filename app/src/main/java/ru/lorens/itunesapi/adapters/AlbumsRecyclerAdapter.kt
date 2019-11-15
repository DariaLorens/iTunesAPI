package ru.lorens.itunesapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.lorens.itunesapi.R
import ru.lorens.itunesapi.rest.Album

class AlbumsRecyclerAdapter(var items: List<Album>, val callback: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumsRecyclerAdapter.AlbumsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlbumsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.album_element,
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class AlbumsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val songImage = itemView.findViewById<ImageView>(R.id.songImage)
        private val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        private val textDesc = itemView.findViewById<TextView>(R.id.textDesc)

        fun bind(item: Album) {

            textTitle.text = item.collectionName
            textDesc.text = item.artistName

            Glide.with(songImage.context)
                .load(item.artworkUrl60)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(songImage)

            itemView.setOnClickListener{
                if (adapterPosition != RecyclerView.NO_POSITION) callback(items[adapterPosition])
            }
        }
    }
}