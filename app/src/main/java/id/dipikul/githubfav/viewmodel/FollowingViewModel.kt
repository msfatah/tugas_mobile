package id.dipikul.githubfav.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import id.dipikul.githubfav.model.UserModel
import org.json.JSONArray

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserModel>>()

    fun setFollowing(name: String?){
        val listItems = ArrayList<UserModel>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token eca6d6fc61cc9b9295b7c51b9eada7931b37e126")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$name/following"

        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()){
                        val jsonObject = responseArray.getJSONObject(i)
                        val mModel = UserModel()
                        mModel.login = jsonObject.getString("login")
                        mModel.avatar = jsonObject.getString("avatar_url")
                        listItems.add(mModel)
                    }
                    listFollowing.postValue(listItems)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<UserModel>>{
        return listFollowing
    }

}