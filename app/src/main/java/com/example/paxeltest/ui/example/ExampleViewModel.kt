package com.example.paxeltest.ui.example

import androidx.lifecycle.ViewModel
import com.example.paxeltest.data.repository.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
//) : BaseViewModel<ExampleNavigator>() {
): ViewModel() {
    val exampleList = repository.getExampleDataset()
}