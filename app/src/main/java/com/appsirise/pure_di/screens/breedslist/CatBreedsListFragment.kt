package com.appsirise.pure_di.screens.breedslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsirise.pure_di.base.BaseFragment
import com.appsirise.pure_di.breeds.CatBreed
import com.appsirise.pure_di.screens.common.ScreensNavigator
import com.appsirise.pure_di.screens.common.dialogs.DialogsNavigator
import kotlinx.coroutines.*

class CatBreedsListFragment : BaseFragment(), CatBreedsListView.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var catBreedsListView: CatBreedsListView
    private lateinit var fetchCatsCatBreedsUseCase: FetchCatBreedsUseCase
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var dialogsNavigator: DialogsNavigator

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchCatsCatBreedsUseCase = activityDiRoot.fetchCatBreedsUseCase
        screensNavigator = activityDiRoot.screensNavigator
        dialogsNavigator = activityDiRoot.dialogsNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catBreedsListView = CatBreedsListView(LayoutInflater.from(requireContext()), container)
        return catBreedsListView.rootView
    }

    override fun onStart() {
        super.onStart()
        catBreedsListView.registerListener(this)
        if (!isDataLoaded) {
            fetchCatBreeds()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        catBreedsListView.unregisterListener(this)
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
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onRefreshClicked() {
        fetchCatBreeds()
    }

    override fun onCatBreedClicked(catBreed: CatBreed) {
        screensNavigator.navigateToCatBreedDetails(catBreed.id)
    }
}