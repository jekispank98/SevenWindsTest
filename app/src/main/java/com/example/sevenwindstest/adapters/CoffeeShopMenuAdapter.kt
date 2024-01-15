package com.example.sevenwindstest.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.model.MenuModel
import com.example.sevenwindstest.R
import com.example.sevenwindstest.ui.OnItemClickedListener

class CoffeeShopMenuAdapter(
    private val context: Context,
    private val listener: OnItemClickedListener
) :RecyclerView.Adapter<CoffeeShopMenuAdapter.CoffeeShopMenuHolder>() {
    private var menuList = emptyList<MenuModel>().toMutableList()

    class CoffeeShopMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage = itemView.findViewById<ImageView>(R.id.dish_img)
        val itemName = itemView.findViewById<TextView>(R.id.name_of_dish)
        val itemprice = itemView.findViewById<TextView>(R.id.item_price)
        val itemCount = itemView.findViewById<TextView>(R.id.item_count)
        val btPlus = itemView.findViewById<Button>(R.id.bt_plus)
        val btMinus = itemView.findViewById<Button>(R.id.bt_minus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopMenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return CoffeeShopMenuHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeShopMenuHolder, position: Int) {
        val menuItem = menuList[position]
        holder.apply {
            Glide.with(context)
                .load(menuItem.imageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemImage)
            itemName.text = menuItem.name
            itemprice.text = context.getString(R.string.PRICE, menuItem.price.toString())
            itemCount.text = menuItem.count.toString()

            btPlus.setOnClickListener {
                menuItem.count++
                listener.onItemClicked(menuItem)
            }

            btMinus.setOnClickListener {
                if (menuItem.count >= 1) menuItem.count--
                listener.onItemClicked(menuItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MenuModel>) {
        menuList = list.toMutableList()
        notifyDataSetChanged()
    }
}