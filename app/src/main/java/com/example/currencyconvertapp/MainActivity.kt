package com.example.currencyconvertapp

import SpinnerAdapter
import ResponseData
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconvertapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.amount.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                convertNow()
                hideKeyboard()
            }
            true
        }

        val spinnerItems = listOf(
            SpinnerAdapter.SpinnerItem(R.drawable.rub, "RUB"),
            SpinnerAdapter.SpinnerItem(R.drawable.usa, "USD"),
            SpinnerAdapter.SpinnerItem(R.drawable.eur, "EUR"),
            SpinnerAdapter.SpinnerItem(R.drawable.jpy, "JPY"),
            SpinnerAdapter.SpinnerItem(R.drawable.cny, "CNY"),
            SpinnerAdapter.SpinnerItem(R.drawable.ils, "ILS"),
            )

        val adapter = SpinnerAdapter(this, spinnerItems)
        binding.secondSpinner.adapter = adapter
        binding.firstSpinner.adapter = adapter

        binding.btnCalculate.setOnClickListener {
            convertNow()
        }

        mainViewModel.liveData.observe(this) { curr ->
            hideProgress()
            val currencyList = arrayListOf<ResponseData>()
            currencyList.add(curr)
            val gotAmount = curr.conversionResult.toDouble()
            val enterAmount = binding.amount.text.toString()
            val totalAmount = (gotAmount * enterAmount.toDouble())
            binding.resultNum.text = totalAmount.toFloat().toString()

        }

        mainViewModel.errorLiveData.observe(this){
            hideProgress()
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("Dismiss"
                ) { dialog, which ->
                    dialog.dismiss()
                }.create().show()
        }

    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }
    private fun convertNow() {
        val selectedItem1 =
            binding.firstSpinner.selectedItem as SpinnerAdapter.SpinnerItem
        val selectedItem2 =
            binding.secondSpinner.selectedItem as SpinnerAdapter.SpinnerItem
        val firstCurrency = selectedItem1.text.lowercase()
        val secondCurrency: String = selectedItem2.text.lowercase()

        showProgress()
        mainViewModel.getData(firstCurrency, secondCurrency)
    }
    private fun hideKeyboard(){
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
