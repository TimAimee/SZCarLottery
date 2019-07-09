package com.veepoo.szcarlottery.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.veepoo.szcarlottery.PerAdapter
import com.veepoo.szcarlottery.R
import com.veepoo.szcarlottery.per.Person
import com.veepoo.szcarlottery.sql.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class DbActivity : AppCompatActivity() {
    val context by lazy { this }
    val TAG: String = "ljl-" + DbActivity::class.java.simpleName;
    var listView: ListView? = null
    var datalist = arrayListOf<Person>()
    var adapter: PerAdapter? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_db)
        (this as AppCompatActivity).supportActionBar!!.setTitle("用户信息")
        initList()
        updateList()
    }

    private fun initList() {
        listView = findViewById(R.id.list)
        adapter = PerAdapter(applicationContext, datalist)
        listView?.adapter = adapter
        listView?.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val person = datalist.get(position)
                var intent = Intent()
                intent.putExtra("number", person.number)
                setResult(RESULT_OK, intent);
                //关闭Activity
                finish();
            }
        })
    }

    private fun updateList() {
        Log.i(TAG, "updateList")
        database.use {
            var list = select(PERSON).exec {
                parseList(classParser<Person>())
            }
            for (person in list) {
                Log.i(TAG, "person:" + person)
            }
            datalist.clear()
            list.forEach {
                datalist.add(it)
            }
            adapter?.notifyDataSetChanged()

        }
    }
}


