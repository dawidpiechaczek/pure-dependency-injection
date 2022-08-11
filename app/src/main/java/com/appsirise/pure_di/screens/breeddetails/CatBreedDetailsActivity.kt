package com.appsirise.pure_di.screens.breeddetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.common.dialogs.ServerErrorDialogFragment
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatBreedDetailsActivity : AppCompatActivity(), CatBreedDetailsView.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var stackoverflowApi: CatsApi
    private lateinit var catBreedDetailsView: CatBreedDetailsView
    private lateinit var catId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catBreedDetailsView = CatBreedDetailsView(LayoutInflater.from(this), null)
        setContentView(catBreedDetailsView.rootView)

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(CatsApi::class.java)

        // retrieve breed ID passed from outside
        catId = intent.extras!!.getString(EXTRA_CAT_BREED_ID)!!
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
                val response = stackoverflowApi.fetchBreedDetails(catId)
                if (response.isSuccessful && response.body() != null) {
                    catBreedDetailsView.bindCatBreedDescription(response.body()!!.description)
                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
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