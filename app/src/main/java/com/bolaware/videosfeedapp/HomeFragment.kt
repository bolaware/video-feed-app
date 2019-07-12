package com.bolaware.videosfeedapp



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bolaware.videosfeedapp.model.MediaObject
import com.bolaware.videosfeedapp.viewmodel.HomeViewModel
import com.danikula.videocache.HttpProxyCacheServer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject


class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var vm : HomeViewModel

    lateinit var rootView : View

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.rootView = view

        setUpViews(view)

        setUpObservers(view)

        vm.fetchPosts()

    }

    private fun setUpViews(view: View){
        view.postRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.postRV)

        arguments?.getInt(BOTTOM_NAV_C0NSTANT_KEY, 0)?.let {
            view.postRV.adapter = PostRecyclerAdapterv2(mutableListOf(), it)
        }
    }

    private fun setUpObservers(view : View){
        vm.toastLd.observe(this, Observer {
            it.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        vm._postsLd.observe(this, Observer {
            it?.let {
                (view.postRV.adapter as PostRecyclerAdapterv2).apply {
                    view.postRV.setMediaObjects(it.map { MediaObject(it.title, it.media_url, it.thumbnail_url, it.description) })
                    posts = it.toMutableList()
                    notifyDataSetChanged()
                    view.postRV.post {
                        view.postRV.playVideo()
                    }
                }
            }
        })
    }


    override fun onDestroy() {
        if (rootView.postRV != null)
            rootView.postRV.releasePlayer()
        super.onDestroy()
    }

}
