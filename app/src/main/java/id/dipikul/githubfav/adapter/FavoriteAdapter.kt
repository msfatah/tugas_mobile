package id.dipikul.githubfav.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.dipikul.githubfav.R
import id.dipikul.githubfav.activity.DetailActivity
import id.dipikul.githubfav.helper.CustomOnItemClickListener
import id.dipikul.githubfav.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*

class FavoriteAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    val TAG = FavoriteAdapter::class.java.simpleName
    var listFavorite = ArrayList<UserModel>()
    set(listFavorite){
        if (listFavorite.size > 0){
            this.listFavorite.clear()
        }
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }

    fun addItem(userModel: UserModel){
        this.listFavorite.add(userModel)
        notifyItemInserted(this.listFavorite.size - 1)
    }

    fun removeItem(position: Int){
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemChanged(position, this.listFavorite.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return this.listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceType")
        fun bind(userModel: UserModel){
            with(itemView){
                Glide.with(itemView.context)
                    .load(userModel.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.refresh_black))
                    .error(R.drawable.broken_image_black)
                    .into(img_item_user)
                tv_item_username.text = userModel.login
                Log.d(TAG, "${userModel.avatar}")

                rv_list_item.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback{
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_STATE, userModel)
                        intent.putExtra(DetailActivity.EXTRA_FAV, "favorite")
                        activity.startActivity(intent)
                    }
                }))
            }
        }
    }
}