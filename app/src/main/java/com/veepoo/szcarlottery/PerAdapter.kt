package com.veepoo.szcarlottery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.veepoo.szcarlottery.per.Person

class PerAdapter(ctx: Context, data: List<Person>) : BaseAdapter() {

    val data = data;
    val layoutInflater = LayoutInflater.from(ctx)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.activity_list_item, null)
        val idTv = view.findViewById<TextView>(R.id.dbid)
        val nameTv = view.findViewById<TextView>(R.id.dbname)
        val numberTv = view.findViewById<TextView>(R.id.dbnumber)
        val person = data.get(position)
        idTv.setText(person.id.toString())
        nameTv.setText(person.name)
        numberTv.setText(person.number)
        return view;
    }

    override fun getItem(position: Int): Any {
        return data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}