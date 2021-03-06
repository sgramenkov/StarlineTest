package com.example.starlinetest.presentation.map.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.starlinetest.App
import com.example.starlinetest.R
import com.example.starlinetest.databinding.FragmentMapBinding
import com.example.starlinetest.di.MapModule
import com.example.starlinetest.presentation.map.viewmodel.MapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.ui_view.ViewProvider
import javax.inject.Inject

class MapFragment : Fragment() {

    @Inject
    lateinit var mapViewModel: MapViewModel

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MapKitFactory.initialize(requireContext())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        setMapPoint()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val subComp = App.getBuilder().build().addModule(MapModule(this))
        subComp.inject(this)
    }

    private fun setMapPoint() {
        var lat: Double? = null
        var lng: Double? = null

        arguments?.run {
            lat = getDouble("lat")
            lng = getDouble("lng")
        }

        if (lat != null && lng != null) {
            val location = Point(lat!!, lng!!)

            val mapView = binding.mapView

            mapView.map.mapObjects.addPlacemark(location, ViewProvider(getMapIcon()))

            val cameraPos = CameraPosition(location, 15f, 0f, 0f)
            val anim = Animation(Animation.Type.SMOOTH, 1f)

            mapView.map.move(cameraPos, anim) { Log.e("map", "move finished") }
        }

    }

    private fun getMapIcon(): View {
        return View(requireContext()).apply {
            background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_car)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}