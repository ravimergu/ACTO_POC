package com.example.acto_poc.ui.photos

import android.os.Bundle
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
import com.example.acto_poc.data.network.Photo
import com.example.acto_poc.data.network.User
import kotlinx.android.synthetic.main.photos_fragment.*
import javax.inject.Inject

class PhotosFragment : Fragment(), PhotosAdapter.PhotoAdapterListener {

    companion object {
        fun newInstance() = PhotosFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as MainApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel
    private lateinit var photosAdapter: PhotosAdapter
    lateinit var navController: NavController
    private var user: User?=null
    private var album: Album?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photos_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        photosAdapter= PhotosAdapter(listOf())
        photosAdapter.setPhotoAdapterListener(this)
        photo_recycler_view.adapter=photosAdapter


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(PhotosViewModel::class.java)
        user = arguments?.getParcelable("user")
        album = arguments?.getParcelable("album")
        user?.let { viewModel.setUser(it) }
        album?.let { viewModel.setAlbum(it) }
        viewModel.loadPhotos()
        viewModel.getPhotos().observe(viewLifecycleOwner, Observer {photosAdapter.updateData(it)
        })
    }

    override fun OnItemClick(photo: Photo) {
        viewModel.downloadImage(photo);
    }

}
