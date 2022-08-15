package com.appsirise.pure_di.screens.breeddetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.appsirise.pure_di.screens.common.dialogs.ServerErrorDialogFragment
import kotlinx.coroutines.*

class CatBreedDetailsActivity : AppCompatActivity(), CatBreedDetailsView.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var catBreedDetailsView: CatBreedDetailsView
    private lateinit var catId: String

    private lateinit var fetchCatBreedDetailsUseCase: FetchCatBreedDetailsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catBreedDetailsView = CatBreedDetailsView(LayoutInflater.from(this), null)
        setContentView(catBreedDetailsView.rootView)
        // retrieve breed ID passed from outside
        catId = intent.extras!!.getString(EXTRA_CAT_BREED_ID)!!
        fetchCatBreedDetailsUseCase = FetchCatBreedDetailsUseCase()
    }

    override fun onStart() {
        super.onStart()
        catBreedDetailsView.registerListener(this)
        fetchCatBreedDetails()
    }

    override fun onStop() {
        catBreedDetailsView.unregisterListener(this)
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchCatBreedDetails() {
        coroutineScope.launch {
            catBreedDetailsView.showProgressIndication()
            try {
                when (val result = fetchCatBreedDetailsUseCase.fetchCatBreedDetails(catId)) {
                    FetchCatBreedDetailsUseCase.Result.Failure -> onFetchFailed()
                    is FetchCatBreedDetailsUseCase.Result.Success ->
                        catBreedDetailsView.bindCatBreedDescription(result.description)
                }
            } finally {
                catBreedDetailsView.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    override fun onBackClicked() {
        onBackPressed()
    }

    companion object {
        const val EXTRA_CAT_BREED_ID = "EXTRA_CAT_BREED_ID"
        fun start(context: Context, breedId: String) {
            val intent = Intent(context, CatBreedDetailsActivity::class.java)
            intent.putExtra(EXTRA_CAT_BREED_ID, breedId)
            context.startActivity(intent)
        }
    }
}