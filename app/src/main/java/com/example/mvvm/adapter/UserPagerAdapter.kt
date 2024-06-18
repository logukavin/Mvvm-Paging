package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.extensions.loadUrl
import com.example.mvvm.databinding.ItemUserBinding
import com.example.mvvm.entities.UsersItem

class UserPagerAdapter(private val onItemClick: (UsersItem) -> Unit) :
    PagingDataAdapter<UsersItem, UserPagerAdapter.UserViewHolder>(UserComparator) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)!!
        holder.view.tvName.text = user.username
        holder.view.tvPhone.text = user.phone
        holder.view.imgUser.loadUrl(user.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

   inner class UserViewHolder(val view: ItemUserBinding) : RecyclerView.ViewHolder(view.root){
       init {
           itemView.setOnClickListener {
               val position = bindingAdapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   val item = getItem(position)
                   item?.let { onItemClick.invoke(it) }
               }
           }
       }
   }

    object UserComparator : DiffUtil.ItemCallback<UsersItem>() {
        override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
            // Id is unique.
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
            return oldItem == newItem
        }
    }
}

