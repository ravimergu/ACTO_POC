package com.example.acto_poc.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.acto_poc.R
import com.example.acto_poc.data.network.Album
import com.example.acto_poc.databinding.AlbumListItemBinding

class AlbumAdapter(private var albumList: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private lateinit var albumAdapterListener: AlbumAdapterListener;
    fun updateData(albumList: List<Album>) {
        this.albumList = albumList
        notifyDataSetChanged()
    }
    fun setAlbumAdapterListener(adapterListener: AlbumAdapterListener){
        this.albumAdapterListener=adapterListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val albumListItemBinding: AlbumListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.album_list_item,
            parent,
            false
        )
        return AlbumViewHolder(albumListItemBinding)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList.get(position)
        holder.albumListItemBinding.album=album;
        holder.albumListItemBinding.listener=albumAdapterListener

    }


    class AlbumViewHolder(val albumListItemBinding: AlbumListItemBinding) : RecyclerView.ViewHolder(albumListItemBinding.root){

    }

    interface AlbumAdapterListener{
        fun OnItemClick(album:Album)
    }
}