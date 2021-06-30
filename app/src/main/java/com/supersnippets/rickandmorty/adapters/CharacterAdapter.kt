package com.supersnippets.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.supersnippets.rickandmorty.databinding.ItemCharacterBinding
import com.supersnippets.rickandmorty.interfaces.OnItemClickedListener
import com.supersnippets.rickandmorty.models.CharactersDto

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(), Filterable {
    lateinit var searchableList: MutableList<CharactersDto>
    lateinit var originalList: MutableList<CharactersDto>
    lateinit var listener: OnItemClickedListener<CharactersDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = searchableList[position]
        holder.bind(item)
    }

    override fun getItemCount() = searchableList.size

    fun setItems(items: List<CharactersDto>) {
        this.searchableList = ArrayList(items)
        this.originalList = ArrayList(items)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchableList.clear()
                if (constraint.isNullOrBlank()) {
                    searchableList.addAll(originalList)
                } else {
                    val searchResults =
                        originalList.filter { it.name.toLowerCase().contains(constraint) }
                    searchableList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchableList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickedListener<CharactersDto>) {
        listener = onItemClickListener
    }

    inner class CharacterViewHolder(private val viewBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: CharactersDto) {
            viewBinding.txtName.text = item.name
            Picasso.get().load(item.image).into(viewBinding.image)
            viewBinding.itemLayout.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}