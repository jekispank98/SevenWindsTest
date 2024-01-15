package com.example.sevenwindstest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MenuModel
import com.example.sevenwindstest.R
import com.example.sevenwindstest.ui.OnItemClickedListener

class OrderAdapter(
    private val listener: OnItemClickedListener
) : RecyclerView.Adapter<OrderAdapter.OrderHolder>() {
    private var orderList = emptyList<MenuModel>().toMutableList()

    class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemOrderName = itemView.findViewById<TextView>(R.id.name_of_order_item)
        val itemOrderPrice = itemView.findViewById<TextView>(R.id.price_of_order_item)
        val itemOrderCount = itemView.findViewById<TextView>(R.id.count_of_items)
        val btPlus = itemView.findViewById<Button>(R.id.bt_plus)
        val btMinus = itemView.findViewById<Button>(R.id.bt_minus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val orderItem = orderList[position]
        holder.apply {
            itemOrderName.text = orderItem.name
            itemOrderPrice.text = calculatePrice(orderItem.price, orderItem.count).toString()
            itemOrderCount.text = orderItem.count.toString()
            btPlus.setOnClickListener {
                orderItem.count++
                listener.onItemClicked(orderItem)
            }

            btMinus.setOnClickListener {
                if (orderItem.count >= 1) orderItem.count--
                listener.onItemClicked(orderItem)
            }
        }
    }

    private fun calculatePrice(price: Int, count: Int): Int {
        return price * count
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MenuModel>) {
        val newList = list.filter { it.count != 0 }
        orderList = newList.toMutableList()
        notifyDataSetChanged()
    }
}