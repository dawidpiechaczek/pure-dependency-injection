package com.appsirise.pure_di.screens.breeddetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appsirise.pure_di.R
import com.appsirise.pure_di.base.BaseView
import com.appsirise.pure_di.screens.common.toolbar.MyToolbar

class CatBreedDetailsView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseView<CatBreedDetailsView.Listener>(layoutInflater, parent, R.layout.layout_breed_details) {

    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtCatBreedBody: TextView

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

}