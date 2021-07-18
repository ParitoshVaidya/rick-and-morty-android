package com.supersnippets.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.supersnippets.rickandmorty.databinding.ActivityDetailsBinding
import com.supersnippets.rickandmorty.models.CharacterDto

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.character_details)
        val characterDto: CharacterDto = intent.getParcelableExtra("character")!!

        binding.characterDto = characterDto
        Picasso.get().load(characterDto.image).into(binding.image)
    }
}