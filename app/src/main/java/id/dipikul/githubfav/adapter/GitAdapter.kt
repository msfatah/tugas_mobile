package id.dipikul.githubfav.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.dipikul.githubfav.R
import id.dipikul.githubfav.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*

class GitAdapter: RecyclerView.Adapter<GitAdapter.GitViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }

    private val mData = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return GitViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class GitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceType")
        fun bind(userModel: UserModel){
            with(itemView){
                Glide.with(itemView.context)
                    .load(userModel.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.refresh_black))
                    .error(R.drawable.broken_image_black)
                    .into(img_item_user)
                tv_item_username.text = userModel.login

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(userModel)}
            }
        }
    }
}