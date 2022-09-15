package me.dasyad.rickandmortyandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.dasyad.rickandmortyandroid.databinding.FragmentCharacterListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListFragment : Fragment() {
    private val viewModel: CharacterListViewModel by viewModel()
    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        val adapter = CharacterListAdapter()
        binding.characterList.layoutManager = LinearLayoutManager(context)
        binding.characterList.adapter = adapter

        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.setCharacters(it)
            binding.loadingCircle.visibility = View.GONE
        }

        lifecycleScope.launch {
            viewModel.init()
        }

        return binding.root
    }
}