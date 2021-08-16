package com.example.maishabora1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.maishabora1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var interestRate: Double? = null
    private val mainViewModel: MainViewModel by viewModels()
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoanTypes()
        subscribers()
        doMath()
    }
    private fun initLoanTypes() {
        binding.loanTypeLayout.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.textView3.text = when (position) {
                        1 -> {
                            mainViewModel.setLoanTerms(1)
                            interestRate = 0.15
                            getString(R.string.fifteen_percent)
                        }
                        2 -> {
                            mainViewModel.setLoanTerms(2)
                            interestRate = 0.08
                            getString(R.string.eight_percent)
                        }
                        else -> getString(R.string.default_percent)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
    private fun subscribers() {
        mainViewModel.interest.observe(this, Observer { interest->
            binding.textView6.text = "%.2f".format(interest)
        })
        mainViewModel.cumulativeAmount.observe(this, Observer { cumulative->
            binding.txtCumulative.text = "%.2f".format(cumulative)
        })
    }
    private fun doMath() {
        with(binding) {
            editText2.doOnTextChanged { text, _, _, _ ->
                if (editText2.text.isNullOrEmpty()) {
                    return@doOnTextChanged
                }
                val amountValue = text.toString()
                val amount = amountValue.toDouble()
                mainViewModel.loanTerms.observe(this@MainActivity, Observer { terms ->
                    Log.d(TAG, terms.toString())
                    if (terms == 1){
                        when {
                            amount < 150000.0 -> {
                                interestRate?.let {

                                    mainViewModel.calculate(amount,it , 3.0)
                                }
                            }
                            amount in  150000.0..300000.0 -> {
                                interestRate?.let {
                                    mainViewModel.calculate(amount,it , 5.0)
                                }
                            }
                            amount in 300001.0..500000.0 -> {
                                interestRate?.let {
                                    mainViewModel.calculate(amount,it , 6.0)
                                }
                            }
                        }
                    } else if (terms == 2) {
                        interestRate?.let {
                            mainViewModel.calculate(amount,it , 1.0)
                        }
                    }
                })

            }
        }
    }
}