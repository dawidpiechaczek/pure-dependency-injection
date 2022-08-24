package com.appsirise.pure_di.screens.breedslist

import android.os.Bundle
import com.appsirise.pure_di.R
import com.appsirise.pure_di.base.BaseActivity

class CatBreedsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.content_frame, CatBreedsListFragment())
                .commit()
        }
    }
}