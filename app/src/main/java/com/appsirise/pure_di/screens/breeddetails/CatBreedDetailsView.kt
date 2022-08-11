package com.appsirise.pure_di.screens.breeddetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appsirise.pure_di.R
import com.appsirise.pure_di.screens.common.toolbar.MyToolbar

class CatBreedDetailsView(layoutInflater: LayoutInflater, parent: ViewGroup?) {

    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtCatBreedBody: TextView

    private val listeners = HashSet<Listener>()

    val rootView: View = layoutInflater.inflate(R.layout.layout_breed_details, parent, false)

    init {
        txtCatBreedBody = findViewById(R.id.txt_breed_body)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { listeners.forEach { listener -> listener.onBackClicked() } }

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun bindCatBreedDescription(description: String) {
        txtCatBreedBody.text = description
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    private fun <T : View?> findViewById(@IdRes id: Int) = rootView.findViewById<T>(id)
}