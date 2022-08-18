package com.appsirise.pure_di.screens.breeddetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.appsirise.pure_di.base.BaseActivity
import com.appsirise.pure_di.screens.common.ScreensNavigator
import com.appsirise.pure_di.screens.common.dialogs.DialogsNavigator
import kotlinx.coroutines.*

class CatBreedDetailsActivity : BaseActivity(), CatBreedDetailsView.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var catBreedDetailsView: CatBreedDetailsView
    private lateinit var fetchCatBreedDetailsUseCase: FetchCatBreedDetailsUseCase
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var catId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catBreedDetailsView = CatBreedDetailsView(LayoutInflater.from(this), null)
        setContentView(catBreedDetailsView.rootView)

        fetchCatBreedDetailsUseCase = appDiRoot.fetchCatBreedDetailsUseCase
        screensNavigator = ScreensNavigator(this)
        dialogsNavigator = DialogsNavigator(supportFragmentManager)

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
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onBackClicked() {
        screensNavigator.navigateBack()
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