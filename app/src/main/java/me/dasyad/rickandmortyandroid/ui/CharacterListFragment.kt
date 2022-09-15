package me.dasyad.rickandmortyandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import me.dasyad.rickandmortyandroid.databinding.FragmentCharacterListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOAD_MORE_BUFFER = 2

class CharacterListFragment : Fragment() {
    private val viewModel: CharacterListViewModel by viewModel()
    private lateinit var binding: FragmentCharacterListBinding

    private val adapter = CharacterListAdapter()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        binding.characterList.layoutManager = LinearLayoutManager(context)
        binding.characterList.adapter = adapter

        viewModel.characters.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe
            adapter.setCharacters(it)
            setLoading(false)
        }

        viewModel.imageBitmaps.observe(viewLifecycleOwner) {
            adapter.setImageBitmaps(it)
        }

        binding.characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.characterList.layoutManager as LinearLayoutManager
                if (isLoading) return
                if (
                    layoutManager.findLastCompletelyVisibleItemPosition() ==
                    viewModel.characters.value!!.size - LOAD_MORE_BUFFER
                ) {
                    setLoading(true)
                    lifecycleScope.launch {
                        viewModel.loadMoreCharacters()
                    }
                }
            }
        })

        lifecycleScope.launch {
            setLoading(true)
            viewModel.init()
        }

        return binding.root
    }

    private fun setLoading(state: Boolean) {
        this.isLoading = state
        adapter.setLoading(state)
    }
}