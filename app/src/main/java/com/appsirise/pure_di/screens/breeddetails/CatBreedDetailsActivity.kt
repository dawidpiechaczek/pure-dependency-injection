package com.appsirise.pure_di.screens.breeddetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.R
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.common.dialogs.ServerErrorDialogFragment
import com.appsirise.pure_di.screens.common.toolbar.MyToolbar
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CatBreedDetailsActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var toolbar: MyToolbar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var txtCatBreedBody: TextView

    private lateinit var stackoverflowApi: CatsApi

    private lateinit var catId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_breed_details)

        txtCatBreedBody = findViewById(R.id.txt_breed_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { onBackPressed() }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false

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
        fetchCatBreedDetails()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchCatBreedDetails() {
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = stackoverflowApi.fetchBreedDetails(catId)
                if (response.isSuccessful && response.body() != null) {
                    txtCatBreedBody.text = response.body()!!.description
                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
                }
            } finally {
                hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
    }

    private fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
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