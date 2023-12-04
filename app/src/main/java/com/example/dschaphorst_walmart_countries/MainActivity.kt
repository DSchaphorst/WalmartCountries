package com.example.dschaphorst_walmart_countries

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dschaphorst_walmart_countries.databinding.ActivityMainBinding
import com.example.dschaphorst_walmart_countries.util.UIState


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainAdapter by lazy {
        MainAdapter() 
    }

    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setObservers()

        mainViewModel.pullCountriesData()
    }

    private fun setObservers(){
        mainViewModel.countriesStatus.observe(this) { state ->
            when(state){
                is UIState.LOADING -> {
                    Log.d(TAG, "Loading network data.")
                    binding.loadingSpinner.visibility = View.VISIBLE
                    binding.homeRecycle.visibility = View.GONE
                }
                is UIState.SUCCESS -> {
                    binding.loadingSpinner.visibility = View.GONE
                    binding.homeRecycle.visibility = View.VISIBLE
                    mainAdapter.setData(state.countries)

                    recyclerView = findViewById(R.id.home_recycle)
                    val layoutManager = LinearLayoutManager(this)
                    recyclerView.layoutManager = layoutManager
                    val dividerItemDecoration = DividerItemDecoration(
                        recyclerView.context,
                        layoutManager.orientation
                    )
                    recyclerView.addItemDecoration(dividerItemDecoration)
                    recyclerView.adapter = mainAdapter
                }
                is UIState.ERROR -> {
                    Log.e(TAG, "Error Loading network data.")
                    binding.loadingSpinner.visibility = View.GONE
                    binding.homeRecycle.visibility = View.GONE
                    AlertDialog.Builder(this)
                        .setTitle("Error Loading Countries")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry") { dialog, _ ->
                            mainViewModel.pullCountriesData()
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
        }
    }
}