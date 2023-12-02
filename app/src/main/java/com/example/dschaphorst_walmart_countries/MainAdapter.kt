package com.example.dschaphorst_walmart_countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dschaphorst_walmart_countries.databinding.CountriesItemCardBinding
import com.example.dschaphorst_walmart_countries.model.Countries
import com.example.dschaphorst_walmart_countries.model.CountriesItem

class MainAdapter (
    private val countriesDataSet: MutableList<CountriesItem> = mutableListOf()
) : RecyclerView.Adapter<CountryViewHolder>() {

    fun setData(countries: Countries) {
        countriesDataSet.clear()
        countries.forEach{
            countriesDataSet.add(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            CountriesItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(countriesDataSet[position])
    override fun getItemCount(): Int = countriesDataSet.size

}

class CountryViewHolder(private val binding: CountriesItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(countriesItem: CountriesItem) {
        val strNameRegion = "${countriesItem.name}, ${countriesItem.region}"
        binding.cardCountryNameRegion.text = strNameRegion
        binding.cardCountryCode.text = countriesItem.code
        binding.cardCountryCapitol.text = countriesItem.capital
    }
}