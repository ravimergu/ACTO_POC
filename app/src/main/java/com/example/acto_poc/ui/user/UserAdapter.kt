package com.example.acto_poc.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.acto_poc.R
import com.example.acto_poc.data.network.User
import com.example.acto_poc.databinding.UserListItemBinding

class UserAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var userAdapterListener: UserAdapterListener;
    fun updateData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }
    fun setUserAdapterListener(userAdapterListener: UserAdapterListener){
        this.userAdapterListener=userAdapterListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val userListItemBinding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false
        )
        return UserViewHolder(userListItemBinding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList.get(position)
        holder.userListItemBinding.user=user;
        holder.userListItemBinding.listener=userAdapterListener

    }


    class UserViewHolder(val userListItemBinding: UserListItemBinding) : RecyclerView.ViewHolder(userListItemBinding.root){

    }

    interface UserAdapterListener{
        fun OnItemClick(user:User)
    }
}