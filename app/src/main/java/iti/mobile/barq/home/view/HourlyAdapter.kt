package iti.mobile.barq.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import iti.mobile.barq.R
import iti.mobile.barq.databinding.WeatherHourlyItemBinding
import iti.mobile.barq.model.Current
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(var hourlyList:List<Current>, var context: Context):
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>(){

    lateinit var binding: WeatherHourlyItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WeatherHourlyItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current=hourlyList[position]

        Glide.with(context).load(context.resources.getString(R.string.weather_icon_url,current.weather[0].icon.toString()))
            .into(holder.binding.iconTemp)

        val date = Date(current.dt * 1000L)
        val sdf = SimpleDateFormat("HH:mm a")


        holder.binding.tvHourDay.text = sdf.format(date)

        holder.binding.tvTemp.text =context.resources.getString(R.string.temperature_kelvin ,current.temp.toString() )




    }

    override fun getItemCount(): Int = hourlyList.size

    inner class ViewHolder(var binding: WeatherHourlyItemBinding): RecyclerView.ViewHolder(binding.root)
}