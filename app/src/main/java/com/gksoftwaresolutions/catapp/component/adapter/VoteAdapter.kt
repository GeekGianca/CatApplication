package com.gksoftwaresolutions.catapp.component.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gksoftwaresolutions.catapp.R
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import com.gksoftwaresolutions.catapp.databinding.ItemVoteCatLayoutBinding

class VoteAdapter(private val items: List<Vote>, private val context: Context) :
    RecyclerView.Adapter<VoteAdapter.VoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteViewHolder =
        VoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_vote_cat_layout, parent, false)
        )

    override fun onBindViewHolder(holder: VoteViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, context)
    }

    override fun getItemCount(): Int = items.size

    inner class VoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemVoteCatLayoutBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(item: Vote, context: Context) {
            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .into(binding.imageCat)
            binding.imageId.text = "Image Id: ${item.imageId}"
            binding.timeVote.text = item.createdAt
            if (item.value == 1) {
                binding.statusVote.setImageResource(R.drawable.ic_outline_thumb_up)
            } else {
                binding.statusVote.setImageResource(R.drawable.ic_outline_thumb_down)
            }
        }
    }
}