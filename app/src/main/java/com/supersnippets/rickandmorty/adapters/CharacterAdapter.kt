package com.supersnippets.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.supersnippets.rickandmorty.databinding.ItemCharacterBinding
import com.supersnippets.rickandmorty.interfaces.OnItemClickedListener
import com.supersnippets.rickandmorty.models.CharacterDto

class CharacterAdapter :
    PagingDataAdapter<CharacterDto, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {
    lateinit var listener: OnItemClickedListener<CharacterDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickedListener<CharacterDto>) {
        listener = onItemClickListener
    }

    object CharacterComparator : DiffUtil.ItemCallback<CharacterDto>() {
        override fun areItemsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterDto, newItem: CharacterDto): Boolean {
            return oldItem == newItem
        }
    }

    inner class CharacterViewHolder(private val viewBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: CharacterDto) {
            viewBinding.txtName.text = item.name
            Picasso.get().load(item.image).into(viewBinding.image)
            viewBinding.itemLayout.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}