package ru.lorens.itunesapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.itunesapi.adapters.AlbumsRecyclerAdapter
import ru.lorens.itunesapi.decorations.ElementItemDecoration
import ru.lorens.itunesapi.rest.Album
import ru.lorens.itunesapi.rest.RestClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            // check string
            if (searchFrame.text.toString() != "") {
                getAlbums(
                    searchFrame.text.toString().toLowerCase().replace(" ", "+"), this
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


    private fun getAlbums(request: String, context: Context) {

        //get album list
        GlobalScope.launch {
            val albumsList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getAlbumsByName(request).results
                } catch (e: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Sorry, server is not available",
                        Toast.LENGTH_SHORT
                    ).show()
                    print(e)
                    null
                }
            }

            if (albumsList != null) {
                runOnUiThread {
                    //set adapter to recycler view and sort album list
                    val myAdapter =
                        AlbumsRecyclerAdapter(albumsList.sortedWith(compareBy(Album::collectionName))) { album ->
                            context.startActivity(
                                Intent(
                                    context,
                                    AlbumActivity::class.java
                                ).putExtra(Album::class.java.simpleName, album)
                            )
                        }
                    mainRecycler.adapter = myAdapter
                    //set divider for elements
                    ContextCompat.getDrawable(context, R.drawable.items_divider)
                        ?.let { ElementItemDecoration(it) }
                        ?.let { mainRecycler.addItemDecoration(it) }

                    progressBar.visibility = View.INVISIBLE
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Nothing found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}