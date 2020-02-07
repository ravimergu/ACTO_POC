package com.example.acto_poc.ui.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.acto_poc.MainApp
import com.example.acto_poc.R
import com.example.acto_poc.data.network.Album
import com.example.acto_poc.data.network.User
import kotlinx.android.synthetic.main.album_fragment.*
import javax.inject.Inject

class AlbumFragment : Fragment(),AlbumAdapter.AlbumAdapterListener {

    companion object {
        fun newInstance() = AlbumFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as MainApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var albumAdapter: AlbumAdapter
    lateinit var navController: NavController

    private lateinit var viewModel: AlbumViewModel
    private var user:User?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        albumAdapter= AlbumAdapter(listOf())
        albumAdapter.setAlbumAdapterListener(this)
        album_recycler_view.adapter=albumAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(AlbumViewModel::class.java)
        user = arguments?.getParcelable("user")
        viewModel.setUser(user)
        viewModel.loadAlbums()

        viewModel.getAlbums().observe(viewLifecycleOwner, Observer {albumAdapter.updateData(it)
        })
    }

    override fun OnItemClick(album: Album) {
        Toast.makeText(context,album.title,Toast.LENGTH_SHORT).show()

        val bundle=Bundle()
        bundle.putParcelable("user",user)
        bundle.putParcelable("album",album)
        navController.navigate(R.id.action_albumFragment_to_photosFragment,bundle)
    }

}
