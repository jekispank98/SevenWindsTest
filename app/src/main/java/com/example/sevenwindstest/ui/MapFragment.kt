package com.example.sevenwindstest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sevenwindstest.MainViewModel
import com.example.sevenwindstest.R
import com.example.sevenwindstest.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var yandexMap: MapView
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("52f9d5d7-2bd8-4485-922f-ad87ceaa3f4f")
        MapKitFactory.initialize(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yandexMap = binding.map
        viewModel.apply {
            getMyLocation()
            myLocation.observe(viewLifecycleOwner) { point ->
                if (point != null) {
                    yandexMap.map.apply {
                        move(
                            CameraPosition(Point(point.latitude, point.longitude), 12.0F, 0.0F, 0.0F),
                            Animation(Animation.Type.SMOOTH, 5F), null
                        )
                        mapObjects.addPlacemark(Point(point.latitude, point.longitude)).setIcon(
                            ImageProvider.fromResource(requireContext(), R.drawable.icon_my_location_png))
                    }
                }
            }
        }

        viewModel.locations?.observe(viewLifecycleOwner) { locations ->
            locations.forEach{
                yandexMap.map.mapObjects.addPlacemark(Point(it.point.latitude, it.point.longitude))
                    .setIcon(
                    ImageProvider.fromResource(requireContext(), R.drawable.icon_coffee_png))
            }
        }
        binding.btBack.setOnClickListener {
            val action = MapFragmentDirections.actionMapFragmentToCoffeShopsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        yandexMap.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        yandexMap.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {

        @JvmStatic
        fun newInstance() = MapFragment()
    }
}