package com.supersnippets.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.supersnippets.rickandmorty.adapters.CharacterAdapter
import com.supersnippets.rickandmorty.databinding.ActivityMainBinding
import com.supersnippets.rickandmorty.interfaces.OnItemClickedListener
import com.supersnippets.rickandmorty.models.CharactersDto
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClickedListener<CharactersDto> {
    private val characterViewModel by viewModel<CharacterViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView
    private var characterAdapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.characters)
        characterViewModel.getCharacters()

        characterViewModel.characters.observe(this, Observer {
            println("data received " + it.size)
            hideProgressBar()
            if (it.isEmpty()) {
                binding.message.text = getString(R.string.no_data)
            } else {
                binding.message.text = ""
                binding.recyclerView.apply {
                    characterAdapter.setItems(it)
                    adapter = characterAdapter
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    characterAdapter.setOnItemClickListener(this@MainActivity)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuItem = menu.findItem(R.id.item_search)
        searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                characterAdapter.filter.filter(newText)
                return true
            }
        })

        return true
    }

    override fun onItemClicked(dto: CharactersDto) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("character", dto)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            menuItem.collapseActionView()
            searchView.isIconified = true
        } else {
            super.onBackPressed()
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }
}