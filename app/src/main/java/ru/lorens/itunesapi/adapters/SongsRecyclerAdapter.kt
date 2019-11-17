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
import ru.lorens.itunesapi.rest.Song


class SongsRecyclerAdapter(var album: Album, var items: List<Song>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_ALBUM -> AlbumHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.album_information_preview, parent, false)
        )
        TYPE_SONG -> SongsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.song_element, parent, false)
        )
        else -> throw IllegalArgumentException()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> TYPE_ALBUM
            else -> TYPE_SONG
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            TYPE_ALBUM -> onBindAlbum(holder)
            TYPE_SONG -> onBindSong(holder, items[position])
            else -> throw IllegalArgumentException()
        }

    private fun onBindAlbum(holder: RecyclerView.ViewHolder) {
        val albumHolder = holder as AlbumHolder
        albumHolder.albumName.text = album.collectionName
        albumHolder.artistName.text = album.artistName
        albumHolder.prise.text = "Price: ${album.collectionPrice}$"
        albumHolder.trackCount.text = "Track count: ${album.trackCount}"
        albumHolder.copyright.text = "Copyright: ${album.copyright}"
        albumHolder.country.text = "Country: ${album.country}"
        albumHolder.primaryGenreName.text = "Genre: ${album.primaryGenreName}"
        Glide.with(albumHolder.albumPreview.context)
            .load(album.artworkUrl100)
            .transform(CenterCrop(), RoundedCorners(10))
            .into(albumHolder.albumPreview)
    }

    private fun onBindSong(holder: RecyclerView.ViewHolder, item: Song) {
        val songsHolder = holder as SongsHolder
        songsHolder.textSongName.text = item.trackName
        songsHolder.textSongDesc.text = item.trackNumber.toString()
    }

    inner class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumPreview: ImageView = itemView.findViewById(R.id.albumPreview)
        val albumName: TextView = itemView.findViewById(R.id.albumName)
        val artistName: TextView = itemView.findViewById(R.id.artistName)
        val prise: TextView  = itemView.findViewById(R.id.prise)
        val trackCount: TextView  = itemView.findViewById(R.id.trackCount)
        val copyright:TextView = itemView.findViewById(R.id.copyright)
        val country:TextView = itemView.findViewById(R.id.country)
        val primaryGenreName:TextView = itemView.findViewById(R.id.primaryGenreName)
    }

    inner class SongsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textSongName: TextView = itemView.findViewById(R.id.textSongName)
        val textSongDesc: TextView = itemView.findViewById(R.id.textSongDesc)
    }

    companion object {
        private const val TYPE_ALBUM = 0
        private const val TYPE_SONG = 1
    }
}