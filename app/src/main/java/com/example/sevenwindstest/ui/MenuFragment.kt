package com.example.sevenwindstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MenuModel
import com.example.sevenwindstest.MainViewModel
import com.example.sevenwindstest.adapters.CoffeeShopMenuAdapter
import com.example.sevenwindstest.databinding.FragmentMenuBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment(), OnItemClickedListener {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var adapter: CoffeeShopMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id = it.getInt(ID)
            viewModel.getMenu(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        menuRecyclerView = binding.rvMenu
        adapter = CoffeeShopMenuAdapter(requireContext(), this as OnItemClickedListener)
        menuRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.menu.observe(viewLifecycleOwner) { menu ->
            menu?.let { adapter.updateList(it) }
        }
        binding.btNavigateToPay.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToOrderFragment()
            findNavController().navigate(action)
        }
        binding.btBack.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToCoffeShopsFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val ID = "id"

        @JvmStatic
        fun newInstance() = MenuFragment()
    }

    override fun onItemClicked(item: MenuModel) {
        viewModel.updateMenu(item)
    }
}