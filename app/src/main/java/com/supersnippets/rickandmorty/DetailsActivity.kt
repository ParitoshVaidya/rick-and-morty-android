package com.supersnippets.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.supersnippets.rickandmorty.databinding.ActivityDetailsBinding
import com.supersnippets.rickandmorty.models.CharactersDto

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.character_details)
        val charactersDto: CharactersDto = intent.getParcelableExtra("character")!!

        binding.charactersDto = charactersDto
        Picasso.get().load(charactersDto.image).into(binding.image)
    }
}