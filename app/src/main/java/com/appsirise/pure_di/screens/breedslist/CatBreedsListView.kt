package com.appsirise.pure_di.screens.breedslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appsirise.pure_di.R
import com.appsirise.pure_di.breeds.CatBreed

class CatBreedsListView(layoutInflater: LayoutInflater, parent: ViewGroup?) {

    interface Listener {
        fun onRefreshClicked()
        fun onCatBreedClicked(catBreed: CatBreed)
    }

    private val swipeRefresh: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val catBreedsAdapter: CatBreedsListActivity.CatBreedsAdapter

    val rootView: View = layoutInflater.inflate(R.layout.layout_cat_breeds_list, parent, false)

    private val context: Context get() = rootView.context
    private val listeners = HashSet<Listener>()

    init {
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            listeners.forEach { listener ->
                listener.onRefreshClicked()
            }
        }

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        catBreedsAdapter = CatBreedsListActivity.CatBreedsAdapter { clickedCat ->
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

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    private fun <T : View?> findViewById(@IdRes id: Int) = rootView.findViewById<T>(id)

}