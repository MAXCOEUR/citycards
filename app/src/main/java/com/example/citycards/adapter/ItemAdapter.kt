package com.example.citycards.adapter

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.citycards.Model.City
import com.example.citycards.R

class ItemAdapter(
    private val context: Context,
    private val dataset: List<City>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.item_title)
        val textViewRegion: TextView = view.findViewById(R.id.item_region)
        val constraintBack: ConstraintLayout = view.findViewById(R.id.carte_1)
        val favoriButton: ImageButton = view.findViewById(R.id.star)
        val ligneView: View = view.findViewById(R.id.separation_line)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewTitle.text = item.name.toString()
        holder.textViewRegion.text = item.region.toString()
        val rang = dataset[position].getRang()
        val star =  dataset[position].favori
        when (star){
            null -> holder.favoriButton.visibility = View.GONE
            false -> holder.favoriButton.setImageResource(R.drawable.star_outline)
            true -> holder.favoriButton.setImageResource(R.drawable.star)
        }
        when (rang){
            0 -> Log.e("erreur", "le rang est 0")
            5 -> {
                holder.constraintBack.backgroundTintList = ContextCompat.getColorStateList(context,R.color.primaryContainer)
                holder.textViewRegion.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimaryContainer))
                holder.textViewTitle.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimaryContainer))
                holder.ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onPrimaryContainer))
            }
            4 -> {
                holder.constraintBack.backgroundTintList = ContextCompat.getColorStateList(context,R.color.secondaryContainer)
                holder.textViewRegion.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondaryContainer))
                holder.textViewTitle.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondaryContainer))
                holder.ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onSecondaryContainer))
            }
            3 -> {
                holder.constraintBack.backgroundTintList = ContextCompat.getColorStateList(context,R.color.primary)
                holder.textViewRegion.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimary))
                holder.textViewTitle.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimary))
                holder.ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onPrimary))
            }
            2 -> {
                holder.constraintBack.backgroundTintList = ContextCompat.getColorStateList(context,R.color.secondary)
                holder.textViewRegion.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondary))
                holder.textViewTitle.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondary))
                holder.ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onSecondary))
            }
            1 -> {
                holder.constraintBack.backgroundTintList = ContextCompat.getColorStateList(context,R.color.tertiary)
                holder.textViewRegion.setTextColor(ContextCompat.getColorStateList(context,R.color.onTertiary))
                holder.textViewTitle.setTextColor(ContextCompat.getColorStateList(context,R.color.onTertiary))
                holder.ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onTertiary))
            }
        }
        holder.favoriButton.setOnClickListener{
            dataset[position].favori?.let {
                dataset[position].favori= !it
                notifyDataSetChanged()
            }
        }
        holder.constraintBack.setOnClickListener{
            Log.e("click carte", "carte cliquée")
        }
    }

}