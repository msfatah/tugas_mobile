package id.dipikul.githubfav.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import id.dipikul.githubfav.model.UserModel
import org.json.JSONObject

class DetailViewModel: ViewModel() {

    companion object{
        private val TAG = DetailViewModel::class.java.simpleName
    }
    private val dataDetails =  MutableLiveData<UserModel>()

    fun setDetailUser(login: String?) {
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token eca6d6fc61cc9b9295b7c51b9eada7931b37e126")
        asyncClient.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$login"
        asyncClient.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, "getApi success")
                try {
                    val responseObject = JSONObject(result)
                    val mModel = UserModel()
                    mModel.login = responseObject.getString("login")
                    mModel.id = responseObject.getString("id").toInt()
                    mModel.name = responseObject.getString("name")
                    mModel.company = responseObject.getString("company")
                    mModel.location = responseObject.getString("location")
                    mModel.blog = responseObject.getString("blog")
                    dataDetails.postValue(mModel)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                val errorMessage = when(statusCode){
                    401 -> "$statusCode : Bad request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d(TAG, "$errorMessage GetApi Failure")
            }
        })
    }

    fun getDetailData(): LiveData<UserModel>{
        return dataDetails
    }
}