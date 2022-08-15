package com.appsirise.pure_di.screens.breedslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsirise.pure_di.R
import com.appsirise.pure_di.breeds.CatBreed
import com.appsirise.pure_di.screens.breeddetails.CatBreedDetailsActivity
import com.appsirise.pure_di.screens.breedslist.CatBreedsListActivity.CatBreedsAdapter.CatBreedViewHolder
import com.appsirise.pure_di.screens.common.dialogs.ServerErrorDialogFragment
import kotlinx.coroutines.*

class CatBreedsListActivity : AppCompatActivity(), CatBreedsListView.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var catBreedsListView: CatBreedsListView

    private var isDataLoaded = false

    private lateinit var fetchCatsCatBreedsUseCase: FetchCatBreedsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catBreedsListView = CatBreedsListView(LayoutInflater.from(this), null)
        setContentView(catBreedsListView.rootView)
        fetchCatsCatBreedsUseCase = FetchCatBreedsUseCase()
    }

    override fun onStart() {
        super.onStart()
        catBreedsListView.registerListener(this)
        if (!isDataLoaded) {
            fetchCatBreeds()
        }
    }

    override fun onStop() {
        catBreedsListView.unregisterListener(this)
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchCatBreeds() {
        coroutineScope.launch {
            catBreedsListView.showProgressIndication()
            try {
                when (val result = fetchCatsCatBreedsUseCase.fetchCatBreeds()) {
                    FetchCatBreedsUseCase.Result.Failure -> {
                        onFetchFailed()
                    }
                    is FetchCatBreedsUseCase.Result.Success -> {
                        catBreedsListView.bindCats(result.cats)
                        isDataLoaded = true
                    }
                }
            } finally {
                catBreedsListView.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    override fun onRefreshClicked() {
        fetchCatBreeds()
    }

    override fun onCatBreedClicked(catBreed: CatBreed) {
        CatBreedDetailsActivity.start(this, catBreed.id)
    }

    class CatBreedsAdapter(
        private val onBreedClickListener: (CatBreed) -> Unit
    ) : RecyclerView.Adapter<CatBreedViewHolder>() {

        private var catBreedsList: List<CatBreed> = ArrayList(0)

        inner class CatBreedViewHolder(view: View) : ViewHolder(view) {
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
}