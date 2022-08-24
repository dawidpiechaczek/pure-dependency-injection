package com.appsirise.pure_di.screens.breedslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appsirise.pure_di.R
import com.appsirise.pure_di.base.BaseView
import com.appsirise.pure_di.breeds.CatBreed

class CatBreedsListView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseView<CatBreedsListView.Listener>(layoutInflater, parent, R.layout.layout_cat_breeds_list) {

    interface Listener {
        fun onRefreshClicked()
        fun onCatBreedClicked(catBreed: CatBreed)
    }

    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val catBreedsAdapter: CatBreedsAdapter

    init {
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            listeners.forEach { listener ->
                listener.onRefreshClicked()
            }
        }

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        catBreedsAdapter = CatBreedsAdapter { clickedCat ->
            listeners.forEach { listener ->
                listener.onCatBreedClicked(clickedCat)
            }
        }
        recyclerView.adapter = catBreedsAdapter
    }

    fun bindCats(cats: List<CatBreed>) {
        catBreedsAdapter.bindData(cats)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

}