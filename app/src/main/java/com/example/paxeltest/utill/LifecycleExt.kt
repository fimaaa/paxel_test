package com.example.paxeltest.utill

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

fun <T> Fragment.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer { action.invoke(it) })
}