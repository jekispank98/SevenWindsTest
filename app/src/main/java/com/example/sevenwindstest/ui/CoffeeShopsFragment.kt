package com.example.sevenwindstest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Point
import com.example.sevenwindstest.MainViewModel
import com.example.sevenwindstest.adapters.CoffeeShopsAdapter
import com.example.sevenwindstest.databinding.FragmentCoffeShopsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CoffeeShopsFragment : Fragment() {
    private lateinit var binding: FragmentCoffeShopsBinding
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var coffeeShopsAdapter: CoffeeShopsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var myLocation: Point

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoffeShopsBinding.inflate(inflater)
        viewModel.getNearestCoffeeShops()
        recyclerView = binding.rvListOfCoffeShops
        coffeeShopsAdapter = CoffeeShopsAdapter(requireContext())
        recyclerView.adapter = coffeeShopsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.locations?.observe(viewLifecycleOwner) { locations ->
            coffeeShopsAdapter.updateList(locations)
        }
        binding.btOnMap.setOnClickListener {
            val action = CoffeeShopsFragmentDirections.actionCoffeShopsFragmentToMapFragment()
            findNavController().navigate(action)
        }
        binding.btBack.setOnClickListener {
            val action = CoffeeShopsFragmentDirections.actionCoffeShopsFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoffeeShopsFragment()
    }
}