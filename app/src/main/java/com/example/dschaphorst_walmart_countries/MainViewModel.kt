package com.example.dschaphorst_walmart_countries

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dschaphorst_walmart_countries.util.UIState
import com.example.dschaphorst_walmart_countries.network.ApiHelper
import com.example.dschaphorst_walmart_countries.util.FailureResponseFromServer
import com.example.dschaphorst_walmart_countries.util.NullResponseFromServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

private const val TAG = "MainViewModel"

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val _countriesStatus: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val countriesStatus: LiveData<UIState> get() = _countriesStatus

    init {
        pullCountriesData()
    }

    fun pullCountriesData(){
        viewModelScope.launch(Dispatchers.IO) {
            val flowHolder: Flow<UIState> = flow {
                emit(UIState.LOADING)
                try {
                    val response = ApiHelper.serviceApi.getCountries()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d(TAG, "pullCountriesData: $it")
                            emit(UIState.SUCCESS(it))
                        } ?: throw NullResponseFromServer("Countries are null")
                    } else {
                        throw FailureResponseFromServer(response.errorBody()?.string())
                    }
                } catch (e : Exception){
                    emit(UIState.ERROR(e))
                    Log.e(TAG, "Caught Error: ${e.localizedMessage}", e)
                }
            }
            flowHolder.collect {
                withContext(Dispatchers.Main){
                    Log.d(TAG, "Change to main thread: $this")
                }
                _countriesStatus.postValue(it)
            }
        }
    }
}