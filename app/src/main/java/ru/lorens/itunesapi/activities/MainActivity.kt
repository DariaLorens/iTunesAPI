package ru.lorens.itunesapi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.lorens.itunesapi.R
import ru.lorens.itunesapi.adapters.AlbumsRecyclerAdapter
import ru.lorens.itunesapi.contracts.MainContract
import ru.lorens.itunesapi.decorations.ElementItemDecoration
import ru.lorens.itunesapi.presenters.MainActivityPresenter
import ru.lorens.itunesapi.rest.Album

class MainActivity : AppCompatActivity(), MainContract.MainView {
    private var presenter: MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
    }

    override fun initView() {
        searchButton.setOnClickListener {
            // check string
            if (searchFrame.text.toString() != "") {
                presenter?.getAlbums(
                    searchFrame.text.toString().toLowerCase().replace(" ", "+")
                )
                progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "The string must not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }

            //hide keyboard
            val view = this.currentFocus
            view?.let { v ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    //update content recycler
    override fun updateViewData(albumsList: List<Album>?) {
        runOnUiThread {
            if (albumsList != null) {
                //set adapter to recycler view and sort album list
                val myAdapter =
                    AlbumsRecyclerAdapter(albumsList.sortedWith(compareBy(Album::collectionName))) { album ->
                        this.startActivity(
                            Intent(
                                this,
                                AlbumActivity::class.java
                            ).putExtra(Album::class.java.simpleName, album)
                        )
                    }
                mainRecycler.adapter = myAdapter
                //set divider for elements
                ContextCompat.getDrawable(this, R.drawable.items_divider)
                    ?.let { ElementItemDecoration(it) }
                    ?.let { mainRecycler.addItemDecoration(it) }

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Sorry, server is not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
            progressBar.visibility = View.INVISIBLE
        }
    }
}