package com.appsirise.pure_di.base

import androidx.fragment.app.Fragment
import com.appsirise.pure_di.di.PresentationDiRoot

open class BaseFragment : Fragment() {

    protected val presentationDiRoot by lazy { PresentationDiRoot((requireActivity() as BaseActivity).activityDiRoot) }
}