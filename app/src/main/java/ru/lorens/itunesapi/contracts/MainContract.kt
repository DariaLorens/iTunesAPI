package ru.lorens.itunesapi.contracts

import ru.lorens.itunesapi.rest.Album

interface MainContract {

    interface MainView {
        fun initView()
        fun updateViewData(albumList: List<Album>?)
    }

    interface MainPresenter {
        fun incrementValue()
        fun getAlbums(
            request: String
        )
    }
}