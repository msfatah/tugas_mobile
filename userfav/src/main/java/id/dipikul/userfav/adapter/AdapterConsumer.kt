package id.dipikul.userfav.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.dipikul.userfav.R
import id.dipikul.userfav.helper.CustomOnItemClickListener
import id.dipikul.userfav.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterConsumer(private val activity: Activity):
    RecyclerView.Adapter<AdapterConsumer.AdapterViewHolder>() {

    var listFavorite = ArrayList<UserModel>()
    set(listFavorite){
        if (listFavorite.size > 0){
            this.listFavorite.clear()
        }
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }


    class AdapterViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        fun bind(userModel: UserModel) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(userModel.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.refresh_black))
                    .error(R.drawable.broken_image_black)
                    .into(img_item_user)
                tv_item_username.text = userModel.login

                rv_list_item.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnitemClickCallback{
                    override fun onItemClicked(view: View, position: Int) {
                        Toast.makeText(context, "${userModel.login}", Toast.LENGTH_SHORT).show()
                    }

                }))

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterConsumer.AdapterViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return AdapterViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return this.listFavorite.size
    }

    override fun onBindViewHolder(holder: AdapterConsumer.AdapterViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }
}