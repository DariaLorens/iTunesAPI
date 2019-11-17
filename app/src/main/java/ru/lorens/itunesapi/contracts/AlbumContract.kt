package ru.lorens.itunesapi.contracts

import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.Song

interface AlbumContract {

    interface AlbumView {
        fun initView()
        fun updateViewData(songsList: List<Song>?)
    }

    interface AlbumPresenter {
        fun incrementValue()
        fun getSongs(
            album: Album
        )
    }
}