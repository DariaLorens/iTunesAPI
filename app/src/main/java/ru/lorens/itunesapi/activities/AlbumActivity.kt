package ru.lorens.itunesapi.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_album.*
import ru.lorens.itunesapi.R
import ru.lorens.itunesapi.adapters.SongsRecyclerAdapter
import ru.lorens.itunesapi.contracts.AlbumContract
import ru.lorens.itunesapi.decorations.ElementItemDecoration
import ru.lorens.itunesapi.presenters.AlbumActivityPresenter
import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.Song


class AlbumActivity : AppCompatActivity(), AlbumContract.AlbumView {
    private var presenter: AlbumContract.AlbumPresenter? = null
    private var album: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        //get presenter
        presenter = AlbumActivityPresenter(this)

        album?.let {
            presenter?.getSongs(
                it
            )
        }
    }

    override fun initView() {
        val arguments = intent.extras
        if (arguments != null) {
            album = arguments.getSerializable(Album::class.java.simpleName) as Album?
        }

        backArrow.setOnClickListener { onBackPressed() }

        progressBarSongs.visibility = View.VISIBLE
    }

    //update content recycler
    override fun updateViewData(songsList: List<Song>?) {
        runOnUiThread {
            if (songsList != null) {
                //set adapter to recycler view and sort songs list
                val myAdapter = album?.let {
                    SongsRecyclerAdapter(
                        it,
                        songsList.sortedWith(compareBy(Song::trackNumber))
                    )
                }
                songsRecycler.adapter = myAdapter
                ContextCompat.getDrawable(this, R.drawable.items_divider)
                    ?.let { ElementItemDecoration(it) }
                    ?.let { songsRecycler.addItemDecoration(it) }

            } else {
                Toast.makeText(
                    this@AlbumActivity,
                    "Sorry, server is not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
            progressBarSongs.visibility = View.INVISIBLE
        }
    }
}

