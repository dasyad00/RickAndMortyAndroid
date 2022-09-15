package me.dasyad.rickandmortyandroid.ui

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.dasyad.rickandmortyandroid.databinding.CharacterViewBinding
import me.dasyad.rickandmortyandroid.databinding.LoadingViewBinding
import me.dasyad.rickandmortyandroid.model.Character

private const val VIEW_TYPE_ITEM = 0;
private const val VIEW_TYPE_LOADING = 1;

class CharacterListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var characters: List<Character> = emptyList()
    private var imageBitmaps: List<Bitmap> = emptyList()

    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = CharacterViewBinding.inflate(inflater, parent, false)
            CharacterViewHolder(binding)
        } else {
            val binding = LoadingViewBinding.inflate(inflater, parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            holder.bind(characters[position], imageBitmaps.getOrNull(position))
        }
    }

    override fun getItemCount(): Int = characters.size + (if (isLoading) 1 else 0)

    override fun getItemViewType(position: Int): Int =
        if (position < characters.size) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }

    fun setCharacters(characters: List<Character>) {
        val oldSize = this.characters.size
        val range = characters.size - oldSize
        this.characters = characters
        if (oldSize == 0) {
            this.notifyDataSetChanged()
        } else {
            this.notifyItemRangeInserted(oldSize, range)
        }
    }

    fun setLoading(state: Boolean) {
        this.isLoading = state
        val pos = characters.size + 1
        if (isLoading) {
            this.notifyItemInserted(pos)
        } else {
            this.notifyItemRemoved(pos)
        }
    }

    fun setImageBitmaps(imageBitmaps: List<Bitmap>) {
        val oldSize = this.imageBitmaps.size
        val range = imageBitmaps.size - oldSize
        this.imageBitmaps = imageBitmaps
        this.notifyItemRangeChanged(oldSize, range)
    }
}

class CharacterViewHolder(
    private val binding: CharacterViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(character: Character, image: Bitmap?) {
        binding.name.text = character.name
        if (image == null) {
            binding.imagePlaceholder.visibility = View.VISIBLE
            binding.image.visibility = View.INVISIBLE
        } else {
            binding.imagePlaceholder.visibility = View.INVISIBLE
            binding.image.visibility = View.VISIBLE
            binding.image.setImageBitmap(image)
        }
    }
}

class LoadingViewHolder(
    private val binding: LoadingViewBinding
) : RecyclerView.ViewHolder(binding.root)