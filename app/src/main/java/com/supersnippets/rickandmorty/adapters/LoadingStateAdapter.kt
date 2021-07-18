package com.supersnippets.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supersnippets.rickandmorty.databinding.LayoutLoadingStateBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutLoadingStateBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val viewBinding: LayoutLoadingStateBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(loadState: LoadState) {

            viewBinding.btnRetry.isVisible = loadState !is LoadState.Loading
            viewBinding.txtErrorMessage.isVisible = loadState !is LoadState.Loading
            viewBinding.progress.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                viewBinding.txtErrorMessage.text =
                    loadState.error.localizedMessage
            }

            viewBinding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}