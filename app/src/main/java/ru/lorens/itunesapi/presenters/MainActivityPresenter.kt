package ru.lorens.itunesapi.presenters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.itunesapi.contracts.MainContract
import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.RestClient

class MainActivityPresenter(_view: MainContract.MainView) : MainContract.MainPresenter {
    private var view: MainContract.MainView = _view
    private var albumsList: List<Album>? = null

    init {
        view.initView()
    }

    override fun incrementValue() {
        view.updateViewData(albumsList)
    }

    override fun getAlbums(request: String) {
        //get album list
        GlobalScope.launch {
            albumsList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getAlbumsByName(request).results
                } catch (e: Throwable) {
                    print(e)
                    null
                }
            }
            incrementValue()
        }
    }
}