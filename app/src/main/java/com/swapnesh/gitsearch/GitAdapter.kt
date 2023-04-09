package com.swapnesh.gitsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swapnesh.gitsearch.databinding.ItemRestBinding
import com.swapnesh.gitsearch.model.GItRepoResponse




class GitAdapter(val restList: ArrayList<GItRepoResponse.Item>) :
    RecyclerView.Adapter<GitAdapter.ViewMaker>() {
    var ctx: Context? = null

    inner class ViewMaker(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRestBinding.bind(itemView)
        fun bind() {
            val gitrepo = restList[adapterPosition]

            binding.tvRestName.text = gitrepo.full_name
            binding.tvRating.text = gitrepo.watchers_count.toString()
            binding.address.text = gitrepo.description
            binding.tvPhone.text = gitrepo.language


            try {
                if(!gitrepo.owner.avatar_url.isEmpty()){
                    Picasso.get().load(gitrepo.owner.avatar_url).into(binding.imgview);
                }
            } catch (e: Exception) {
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMaker {
        ctx = parent.context
        return ViewMaker(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rest, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ViewMaker, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = restList.size
}