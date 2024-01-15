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
import com.example.sevenwindstest.adapters.OrderAdapter
import com.example.sevenwindstest.databinding.FragmentOrderBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OrderFragment : Fragment(), OnItemClickedListener {
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentOrderBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater)
        recyclerView = binding.rvOrdersList
        adapter = OrderAdapter(this as OnItemClickedListener)
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.menu.observe(viewLifecycleOwner) { order ->
            order?.let { adapter.updateList(it) }
        }
        binding.btBack.setOnClickListener {
            val action = OrderFragmentDirections.actionOrderFragmentToMenuFragment(viewModel.coffeeShopId.value?: 0)
            findNavController().navigate(action)
        }
        binding.btPay.setOnClickListener {

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = OrderFragment()
    }

    override fun onItemClicked(item: MenuModel) {
        viewModel.updateMenu(item)
    }
}