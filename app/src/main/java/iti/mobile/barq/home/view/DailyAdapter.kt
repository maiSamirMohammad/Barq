package iti.mobile.barq.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import iti.mobile.barq.R
import iti.mobile.barq.databinding.WeatherDayItemBinding
import iti.mobile.barq.model.Daily
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(private var dailyList: List<Daily>, var context: Context) :
    RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    lateinit var binding: WeatherDayItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = WeatherDayItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current = dailyList[position]

        val imgUrl = context.resources.getString(R.string.weather_icon_url,current.weather[0].icon.toString())
        imgUrl.let {
            holder.binding.dayIcon.load(imgUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }


        holder.binding.dayDescription.text = current.weather[0].description.toString()
        holder.binding.dayTemperature.text =context.resources.getString(R.string.min_max_temperature,
               current.temp.max.toString(), current.temp.min.toString() )





        if (position == 0) {

                holder.binding.day.text = "Today"
        }
        else if (position == 1) {

                holder.binding.day.text = "Tomorrow"
        }
        else {
            val date= Date(current.dt * 1000L)
            val sdf = SimpleDateFormat("EEEE")
                holder.binding.day.text = sdf.format(date)
        }

    }

    override fun getItemCount(): Int = dailyList.size

    inner class ViewHolder(var binding: WeatherDayItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}