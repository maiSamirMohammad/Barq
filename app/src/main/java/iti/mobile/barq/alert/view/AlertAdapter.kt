package iti.mobile.barq.alert.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iti.mobile.barq.R
import iti.mobile.barq.model.Alert
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AlertAdapter(
    val context: Context,
    var alerts: List<Alert>,
    private val listener: OnItemCLickListener
) : RecyclerView.Adapter<AlertAdapter.MyViewHolder>() {


    fun setList(updatedProducts: List<Alert>) {
        alerts = updatedProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.alert_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct: Alert = alerts[position]
        val time_stamp_from= Date(TimeUnit.SECONDS.toMillis(currentProduct.startDate))
        holder.from.text=SimpleDateFormat("dd MMM").format(time_stamp_from)
        val time_stamp_to= Date(TimeUnit.SECONDS.toMillis(currentProduct.endDate))
        holder.to.text=SimpleDateFormat("dd MMM").format(time_stamp_to)

        holder.delete.setOnClickListener { listener.onClick(currentProduct) }
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var from: TextView
         var to: TextView
         var delete: ImageButton


        init {
            from = itemView.findViewById(R.id.textView_from)
            to = itemView.findViewById(R.id.textView_to)
            delete = itemView.findViewById(R.id.imageButton_delete)
        }
    }


}