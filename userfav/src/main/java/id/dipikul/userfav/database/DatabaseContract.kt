package id.dipikul.userfav.database

import android.net.Uri
import android.provider.BaseColumns
import id.dipikul.userfav.database.DatabaseContract.GitColumns.Companion.TABLE_NAME

object DatabaseContract {
    private const val AUTHORITY = "id.dipikul.githubfav"
    private const val SCHEME = "content"

    class GitColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "git_user"
            const val _ID = "_id"
            const val LOGIN_NAME = "login_name"
            const val AVATAR = "avatar"
        }
    }

    val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}