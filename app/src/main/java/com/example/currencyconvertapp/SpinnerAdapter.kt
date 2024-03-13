package com.example.currencyconvertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class SpinnerAdapter (private val context: Context, private val items: List<SpinnerItem>) : BaseAdapter(){
    data class SpinnerItem(val iconRes: Int, val text: String)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, parent, false)
            holder = ViewHolder()
            holder.icon = view.findViewById(R.id.spinner_icon)
            holder.text = view.findViewById(R.id.spinner_text)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = items[position]
        holder.icon.setImageResource(item.iconRes)
        holder.text.text = item.text

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    private class ViewHolder {
        lateinit var icon: ImageView
        lateinit var text: TextView
    }
}