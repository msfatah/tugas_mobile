package id.dipikul.userfav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.dipikul.userfav.adapter.AdapterConsumer
import id.dipikul.userfav.database.DatabaseContract.CONTENT_URI
import id.dipikul.userfav.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AdapterConsumer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Favorite list user"
        adapter = AdapterConsumer(this)

        GlobalScope.launch(Dispatchers.Main){
            val deferredGit = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val fav = deferredGit.await()
            if(fav.size > 0){
                adapter.listFavorite = fav
            }else{
                adapter.listFavorite = ArrayList()
                showSnackbarMessage("data is empty")
            }
        }
        showRecyclerView()
    }

    private fun showRecyclerView() {
        rv_user.adapter = adapter
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.setHasFixedSize(true)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_user, message, Snackbar.LENGTH_SHORT).show()
    }
}
