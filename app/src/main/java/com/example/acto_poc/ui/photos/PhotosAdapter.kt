package com.example.acto_poc.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.acto_poc.R
import com.example.acto_poc.data.network.Photo
import com.example.acto_poc.databinding.PhotoListItemBinding

class PhotosAdapter(private var photosList: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private lateinit var photoAdapterListener: PhotoAdapterListener;
    fun updateData(photosList: List<Photo>) {
        this.photosList = photosList
        notifyDataSetChanged()
    }
    fun setPhotoAdapterListener(adapterListener: PhotoAdapterListener){
        this.photoAdapterListener=adapterListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val photoListItemBinding: PhotoListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.photo_list_item,
            parent,
            false
        )
        return PhotoViewHolder(photoListItemBinding)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photosList.get(position)
        holder.photoListItemBinding.photo=photo;
        holder.photoListItemBinding.listener=photoAdapterListener
    }

    class PhotoViewHolder(val photoListItemBinding: PhotoListItemBinding) : RecyclerView.ViewHolder(photoListItemBinding.root){

    }

    interface PhotoAdapterListener{
        fun OnItemClick(photo:Photo)
    }
}