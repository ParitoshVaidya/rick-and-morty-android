package com.supersnippets.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.supersnippets.rickandmorty.adapters.CharacterAdapter
import com.supersnippets.rickandmorty.adapters.LoadingStateAdapter
import com.supersnippets.rickandmorty.databinding.ActivityMainBinding
import com.supersnippets.rickandmorty.interfaces.OnItemClickedListener
import com.supersnippets.rickandmorty.models.CharacterDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClickedListener<CharacterDto> {
    private val characterViewModel by viewModel<CharacterViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var characterAdapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.characters)

        binding.recyclerView.apply {
            adapter = characterAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { characterAdapter.retry() }
            )
            characterAdapter.addLoadStateListener { loadState ->
                binding.group.visibility = View.GONE
                binding.progressBar.isVisible = false
                if (characterAdapter.itemCount < 1) {
                    if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                        binding.progressBar.isVisible = true
                    } else {
                        binding.progressBar.isVisible = false

                        val errorState = when {
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        errorState?.let {
                            binding.group.visibility = View.VISIBLE
                            binding.message.text = it.error.localizedMessage
                        }
                    }
                }
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            characterAdapter.setOnItemClickListener(this@MainActivity)
            setHasFixedSize(true)
        }

        binding.btnRetry.setOnClickListener {
            characterAdapter.retry()
        }

        lifecycleScope.launch {
            characterViewModel.characters.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    override fun onItemClicked(dto: CharacterDto) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("character", dto)
        startActivity(intent)
    }
}