package id.dipikul.githubfav.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.dipikul.githubfav.R
import id.dipikul.githubfav.adapter.FavoriteAdapter
import id.dipikul.githubfav.database.DatabaseContract.CONTENT_URI
import id.dipikul.githubfav.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    internal val TAG = FavoriteActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite"

        adapter = FavoriteAdapter(this)

        GlobalScope.launch(Dispatchers.Main) {
            val deferredGit = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val fav = deferredGit.await()
            if (fav.size > 0){
                adapter.listFavorite = fav
            }else{
                adapter.listFavorite = ArrayList()
                showSnackbarMessage("Data is empty")
            }
        }

        showRecylerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        onBackPressed()
        startActivity(intent)
        return super.onSupportNavigateUp()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showRecylerView() {
        rv_favorite.adapter = adapter
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
    }

    private fun showSnackbarMessage(msg: String) {
        Snackbar.make(rv_favorite, msg, Snackbar.LENGTH_SHORT).show()
    }
}