package com.appsirise.pure_di.base

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    protected val activityDiRoot get() = (requireActivity() as BaseActivity).activityDiRoot
}