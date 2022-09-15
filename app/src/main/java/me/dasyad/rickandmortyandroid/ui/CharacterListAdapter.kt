package me.dasyad.rickandmortyandroid.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.dasyad.rickandmortyandroid.databinding.CharacterViewBinding
import me.dasyad.rickandmortyandroid.model.Character

class CharacterListAdapter : RecyclerView.Adapter<CharacterViewHolder>() {
    private var characters: List<Character> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterViewBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
        this.notifyDataSetChanged()
    }

    fun updateCharacter(index: Int) {
        this.notifyItemChanged(index)
    }
}

class CharacterViewHolder(
    private val binding: CharacterViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(character: Character) {
        binding.name.text = character.name
    }
}