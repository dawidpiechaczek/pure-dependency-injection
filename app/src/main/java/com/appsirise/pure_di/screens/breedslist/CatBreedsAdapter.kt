package com.appsirise.pure_di.screens.breedslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appsirise.pure_di.R
import com.appsirise.pure_di.breeds.CatBreed

class CatBreedsAdapter(
    private val onBreedClickListener: (CatBreed) -> Unit
) : RecyclerView.Adapter<CatBreedsAdapter.CatBreedViewHolder>() {

    private var catBreedsList: List<CatBreed> = ArrayList(0)

    inner class CatBreedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txt_title)
    }

    fun bindData(cats: List<CatBreed>) {
        catBreedsList = ArrayList(cats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_cat_breed_list_item, parent, false)
        return CatBreedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatBreedViewHolder, position: Int) {
        holder.title.text = catBreedsList[position].name
        holder.itemView.setOnClickListener {
            onBreedClickListener.invoke(catBreedsList[position])
        }
    }

    override fun getItemCount(): Int {
        return catBreedsList.size
    }
}