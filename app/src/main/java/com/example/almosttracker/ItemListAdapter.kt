package com.example.almosttracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ItemListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ItemListAdapter.ViewHolderRecyclerView>() {
    private var items = emptyList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecyclerView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolderRecyclerView(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderRecyclerView, position: Int) {
        val currentItem = items[position]

        holder.tv_address.text = currentItem.item_address
        holder.tv_lat.text = currentItem.item_lat
        holder.tv_lon.text = currentItem.item_lon
        holder.tv_date.text = currentItem.item_date
    }

    internal fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolderRecyclerView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_address: TextView = itemView.tv_address
        val tv_lat: TextView = itemView.tv_lat
        val tv_lon: TextView = itemView.tv_lon
        val tv_date: TextView = itemView.tv_date
    }
}