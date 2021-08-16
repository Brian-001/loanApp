package com.example.maishabora1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class MainViewModel: ViewModel() {
    private val _interest: MutableLiveData<Double> = MutableLiveData()
    val interest: LiveData<Double> get() = _interest
    private val _cumulativeAmount: MutableLiveData<Double> = MutableLiveData()
    val cumulativeAmount: LiveData<Double> get() = _cumulativeAmount
    private val _loanTerms: MutableLiveData<Int> = MutableLiveData()
    val loanTerms: LiveData<Int> get() = _loanTerms
    fun calculate(principle: Double, rate: Double, years: Double) {
        val interest = principle * ((1 - rate).pow(years))
        _interest.value = interest
        _cumulativeAmount.value = principle + interest
    }
    fun setLoanTerms(position: Int) {
        _loanTerms.value = position
    }
}