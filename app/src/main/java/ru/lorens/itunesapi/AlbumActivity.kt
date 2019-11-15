package ru.lorens.itunesapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_album.*
import ru.lorens.itunesapi.rest.Album


class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val arguments = intent.extras
        if (arguments != null) {
            val album = arguments.getSerializable(Album::class.java.simpleName) as Album?

            Glide.with(albumPreview.context)
                .load(album?.artworkUrl100)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(albumPreview)

            albumName.text = album?.collectionName
            artistName.text = album?.artistName

        }

//        val album = intent.extras?.getParcelable<Album>("myCustomerObj")

    }
}
