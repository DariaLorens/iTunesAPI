package ru.lorens.itunesapi

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.itunesapi.adapters.SongsRecyclerAdapter
import ru.lorens.itunesapi.decorations.ElementItemDecoration
import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.RestClient
import ru.lorens.itunesapi.rest.Song


class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val arguments = intent.extras
        if (arguments != null) {
            val album = arguments.getSerializable(Album::class.java.simpleName) as Album?

            album?.collectionId?.let { getSongs(this, album) }

            backArrow.setOnClickListener { onBackPressed() }

            progressBarSongs.visibility = View.VISIBLE
        }
    }

    private fun getSongs(context: Context, album: Album) {
        //get songs list
        GlobalScope.launch {
            val songsList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getSongsByAlbumId(album.collectionId).results
                } catch (e: Throwable) {
                    Toast.makeText(
                        this@AlbumActivity,
                        "Sorry, server is not available",
                        Toast.LENGTH_SHORT
                    ).show()
                    print(e)
                    null
                }
            }

            if (songsList != null) {
                runOnUiThread {
                    //set adapter to recycler view and sort songs list
                    val myAdapter = SongsRecyclerAdapter(
                        album,
                        songsList.sortedWith(compareBy(Song::trackNumber))
                    )
                    songsRecycler.adapter = myAdapter
                    ContextCompat.getDrawable(context, R.drawable.items_divider)
                        ?.let { ElementItemDecoration(it) }
                        ?.let { songsRecycler.addItemDecoration(it) }

                    progressBarSongs.visibility = View.INVISIBLE
                }
            }
        }
    }
}
