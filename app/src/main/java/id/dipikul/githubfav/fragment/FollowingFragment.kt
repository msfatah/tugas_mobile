package id.dipikul.githubfav.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dipikul.githubfav.R
import id.dipikul.githubfav.viewmodel.FollowingViewModel
import id.dipikul.githubfav.adapter.FollowingAdapter
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_FOLLOWING = "extra_following"
    }

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowingAdapter()
        showRecyclerView()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        if (arguments != null){
            val username = arguments?.getString(EXTRA_FOLLOWING)
            followingViewModel.setFollowing(username)
        }

        followingViewModel.getFollowing().observe(this, Observer { usermodel ->
            if (usermodel != null){
                adapter.setData(usermodel)
            }
        })
    }

    private fun showRecyclerView() {
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}