package com.example.weather_forcast_app.ui.favourite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.FavouriteCityEntity
import com.example.domain.utils.ResultResponse
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.databinding.FragmentFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FavouriteInterface {
    lateinit var binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()
    lateinit var favAdapter: FavouriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addFavFloatingActionButton.setOnClickListener {
            val action =
                FavouriteFragmentDirections.actionNavigationFavouriteToMapFragment("favourite")
            it.findNavController().navigate(action)
        }
        viewModel.getFavouriteList()
        lifecycleScope.launch {
            viewModel.favouriteList.collect { result ->
                when (result) {
                    is ResultResponse.OnSuccess -> {
                        favAdapter = FavouriteAdapter(requireContext(), this@FavouriteFragment)
                        favAdapter.submitList(result.data)
                        binding.favRecyclerView.apply {
                            adapter = favAdapter
                            layoutManager = LinearLayoutManager(context).apply {
                                orientation = RecyclerView.VERTICAL
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    override fun delete(fav: FavouriteCityEntity, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireView().resources.getString(R.string.warning))
            .setMessage(getString(R.string.delete_place))
            .setPositiveButton(R.string.ok) { _, _ ->
                lifecycleScope.launch {
                    viewModel.deleteFromFav(fav)
                    favAdapter.notifyItemRemoved(position)


                }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setIcon(R.drawable.ic_warning_24)
            .show()

    }

    override fun goToDetails(fav: FavouriteCityEntity) {
        val action = FavouriteFragmentDirections.actionNavigationFavouriteToFavouriteDetails(fav.latitude.toFloat(),
            fav.longitude.toFloat())
        this.findNavController().navigate(action)
    }
}