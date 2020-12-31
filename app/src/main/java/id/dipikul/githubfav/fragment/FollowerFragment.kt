package id.dipikul.githubfav.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dipikul.githubfav.R
import id.dipikul.githubfav.adapter.FollowersAdapter
import id.dipikul.githubfav.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_follower.*


/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    companion object {
        const val EXTRA_FOLLOWERS = "followers_name"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        showRecyclerView()

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

        if (arguments != null){
            val username = arguments?.getString(EXTRA_FOLLOWERS)
            followersViewModel.setFollowers(username.toString())
        }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, Observer { usermodel ->
            if (usermodel != null){
                adapter.setData(usermodel)
            }
        })
    }

    private fun showRecyclerView() {
        rv_follower.layoutManager = LinearLayoutManager(context)
        rv_follower.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}