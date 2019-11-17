package ru.lorens.itunesapi.presenters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.itunesapi.contracts.AlbumContract
import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.RestClient
import ru.lorens.itunesapi.rest.Song

class AlbumActivityPresenter(_view: AlbumContract.AlbumView) : AlbumContract.AlbumPresenter {
    private var view: AlbumContract.AlbumView = _view
    private var songsList: List<Song>? = null

    init {
        view.initView()
    }

    override fun incrementValue() {
        view.updateViewData(songsList)
    }

    override fun getSongs(album: Album) {
        //get songs list
        GlobalScope.launch {
            songsList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getSongsByAlbumId(album.collectionId).results
                } catch (e: Throwable) {
                    print(e)
                    null
                }
            }
            incrementValue()
        }
    }
}