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

class OpratePersonActivity : AppCompatActivity() {
    val context by lazy { this }
    val TAG: String = "ljl-" + OpratePersonActivity::class.java.simpleName;
    var listView: ListView? = null
    var datalist = arrayListOf<Person>()
    var adapter: PerAdapter? = null


    var editAddId: EditText? = null
    var editAddName: EditText? = null
    var editAddNumber: EditText? = null
    var addBut: Button? = null

    var removeBut: Button? = null
    var editRemoveid: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_opreate)
        (this as AppCompatActivity).supportActionBar!!.setTitle("修改用户")
        initView()
        setClick()
        initList()
        updateList()
    }

    private fun setClick() {
        addBut?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val textId = editAddId?.text.toString()
                val textName = editAddName?.text.toString()
                val textNumber = editAddNumber?.text.toString()
                if (TextUtils.isEmpty(textId)) {
                    Toast.makeText(context, "textId is null", Toast.LENGTH_SHORT).show()
                    return;
                }
                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(context, "textName is null", Toast.LENGTH_SHORT).show()
                    return;
                }
                if (TextUtils.isEmpty(textNumber)) {
                    Toast.makeText(context, "textNumber is null", Toast.LENGTH_SHORT).show()
                    return;
                }
                if (textNumber?.length != 13) {
                    Toast.makeText(context, "textNumber is not 13", Toast.LENGTH_SHORT).show()
                    return;
                }
                database.use {
                    insert(
                        PERSON,
                        "id" to Integer.valueOf(textId),
                        "name" to textName,
                        "number" to textNumber
                    )
                    updateList()
                }
            }
        })
        removeBut?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val text = editRemoveid?.text
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(context, "id is null", Toast.LENGTH_SHORT).show()
                } else {
                    database.use {
                        execSQL("delete from Person where id = $text")
                    }
                    updateList();
                }
            }
        })
    }

    private fun initView() {
        editRemoveid = findViewById(R.id.id_remove)
        removeBut = findViewById(R.id.but_remove)


        addBut = findViewById(R.id.but_add)
        editAddId = findViewById(R.id.person_id)
        editAddName = findViewById(R.id.person_name)
        editAddNumber = findViewById(R.id.person_number)
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


