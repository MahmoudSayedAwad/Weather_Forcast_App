package com.example.weather_forcast_app.ui.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.FavouriteCityEntity
import com.example.weather_forcast_app.R
import com.example.weather_forcast_app.ui.favourite.FavouriteViewModel
import com.example.weather_forcast_app.utils.Constants
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    lateinit var comingFrom: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyAbuHdF1hB_ddtCtgEZ7iE3cDqXk4zpVJU")
        }
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypesFilter(listOf(PlaceTypes.CITIES))
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                //  Get info about the selected place.
                place.latLng?.let {
                    mMap.addMarker(MarkerOptions().position(it).title(place.name))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
                    val citynameAR = viewModel.getCityName(
                        it.latitude, it.longitude, requireContext(), "ar"
                    )[0].adminArea
                    val cityname = viewModel.getCityName(
                        it.latitude, it.longitude, requireContext(), "en"
                    )[0].adminArea
                    if (comingFrom.equals(Constants.COMING_FROM_SETTING)) {
                        displayAlertDialogToSaveCurrentLocation(
                            FavouriteCityEntity(
                                cityname, citynameAR, it.latitude, it.longitude
                            )
                        )
                    } else {
                        displayAlertDialogToSaveFavourite(
                            FavouriteCityEntity(
                                cityname, citynameAR, it.latitude, it.longitude
                            )
                        )
                    }
                }


            }

            override fun onError(status: Status) {


            }
        })
        comingFrom = MapFragmentArgs.fromBundle(requireArguments()).commingFrom
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragement) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val defaultLocation = LatLng(viewModel.getLatitude(), viewModel.getLongitude())
        mMap.addMarker(MarkerOptions().position(defaultLocation).title("Suez"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
        mMap.setOnMapLongClickListener { latlng ->
            val citynameAR = viewModel.getCityName(
                latlng.latitude, latlng.longitude, requireContext(), "ar"
            )[0].adminArea
            val cityname = viewModel.getCityName(
                latlng.latitude, latlng.longitude, requireContext(), "en"
            )[0].adminArea
            if (comingFrom.equals(Constants.COMING_FROM_SETTING)) {
                displayAlertDialogToSaveCurrentLocation(
                    FavouriteCityEntity(
                        cityname, citynameAR, latlng.latitude, latlng.longitude
                    )
                )
            } else {
                displayAlertDialogToSaveFavourite(
                    FavouriteCityEntity(
                        cityname, citynameAR, latlng.latitude, latlng.longitude
                    )
                )
            }
        }
    }

    fun displayAlertDialogToSaveFavourite(model: FavouriteCityEntity) {
        var alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alert.setTitle(requireView().resources.getString(R.string.add_to_favourite))
        alert.setMessage("Do you want to save ${model.cityName} in Favourite")
        alert.setPositiveButton(requireView().resources.getString(R.string.save)) { _: DialogInterface, _: Int ->
            viewModel.addFavourite(model)
            val action = MapFragmentDirections.actionMapFragmentToNavigationFavourite()
            findNavController().navigate(action)
        }
        alert.setNegativeButton(requireView().resources.getString(R.string.cancel)) { _: DialogInterface, _: Int ->
        }

        val dialog = alert.create()

        dialog.show()


    }

    fun displayAlertDialogToSaveCurrentLocation(model: FavouriteCityEntity) {

        var alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alert.setTitle(requireView().resources.getString(R.string.add_location))
        alert.setMessage("Do you want to save ${model.cityName} ")
        alert.setPositiveButton("Save") { _: DialogInterface, _: Int ->
            viewModel.setLatitude(model.latitude.toFloat())
            viewModel.setLongitude(model.longitude.toFloat())
            viewModel.setLocationMethod(Constants.LOCATION_METHOD_MAP)
            val action = MapFragmentDirections.actionMapFragmentToNavigationHome()
            findNavController().navigate(action)

        }
        alert.setNegativeButton("cancel") { _: DialogInterface, _: Int ->
        }

        val dialog = alert.create()

        dialog.show()


    }

}