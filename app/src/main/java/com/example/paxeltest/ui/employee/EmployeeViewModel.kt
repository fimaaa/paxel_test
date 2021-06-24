package com.example.paxeltest.ui.employee

import androidx.lifecycle.ViewModel
import com.example.paxeltest.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {
    val exampleList = repository.getExampleDataset()
}