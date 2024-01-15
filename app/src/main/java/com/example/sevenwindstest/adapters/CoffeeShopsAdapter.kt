package com.example.sevenwindstest.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.NearestLocationModel
import com.example.sevenwindstest.R
import com.example.sevenwindstest.ui.CoffeeShopsFragmentDirections

class CoffeeShopsAdapter(
    private val context: Context
): RecyclerView.Adapter<CoffeeShopsAdapter.CoffeeShopsHolder>() {
    private var coffeeShopsList = emptyList<NearestLocationModel>()
    class CoffeeShopsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name_of_coffee_shops)
        val distanceTo = itemView.findViewById<TextView>(R.id.distance_to_coffe_shops)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffe_shops, parent, false)
        return CoffeeShopsHolder(view)
    }

    override fun getItemCount(): Int {
        return coffeeShopsList.size
    }

    override fun onBindViewHolder(holder: CoffeeShopsHolder, position: Int) {
        val point = coffeeShopsList[position]
        holder.apply {
            name.text = point.name
            distanceTo.setText(context.getString(R.string.DISTANCE_TO, point.distanceTo.toInt().toString()))
            itemView.setOnClickListener {
                val action = CoffeeShopsFragmentDirections.actionCoffeShopsFragmentToMenuFragment(point.id)
                itemView.findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<NearestLocationModel>) {
        coffeeShopsList = list
        notifyDataSetChanged()
    }
}