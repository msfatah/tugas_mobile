package id.dipikul.githubfav.helper

import android.database.Cursor
import android.util.Log
import id.dipikul.githubfav.database.DatabaseContract
import id.dipikul.githubfav.model.UserModel

object MappingHelper {
    val TAG = MappingHelper::class.java.simpleName
    fun mapCursorToArrayList(gitCursor: Cursor?): ArrayList<UserModel>{
            val gitList = ArrayList<UserModel>()
            gitCursor?.apply {
                while (moveToNext()){
                    val userModel = UserModel()
                    userModel.id = getInt(getColumnIndexOrThrow(DatabaseContract.GitColumns._ID))
                    userModel.login = getString(getColumnIndexOrThrow(DatabaseContract.GitColumns.LOGIN_NAME))
                    userModel.avatar = getString(getColumnIndexOrThrow(DatabaseContract.GitColumns.AVATAR))
                    gitList.add(userModel)
                    Log.d(TAG, userModel.toString())
                }
            }
            return gitList
    }
}