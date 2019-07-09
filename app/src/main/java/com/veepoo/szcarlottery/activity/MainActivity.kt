package com.veepoo.szcarlottery.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.veepoo.szcarlottery.R
import com.veepoo.szcarlottery.util.SpUtil
import com.veepoo.szcarlottery.http.HttpUtil
import com.veepoo.szcarlottery.util.parse
import com.veepoo.szcarlottery.sql.database
import org.jetbrains.anko.db.insert
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*






val PERSON: String = "person"

class MainActivity : AppCompatActivity() {
    val context by lazy { this }
    val TAG: String = "ljl-" + MainActivity::class.java.simpleName
    var demo_but: Button? = null
    var db_but: ImageView? = null
    var result_but: Button? = null
    var oprate_but: Button? = null
    var appversion: TextView? = null
    var demo_tv: TextView? = null
    var result_tv: TextView? = null
    var edit: EditText? = null
    var datef: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setTextView()
        setOnClick()
        insertDb()
    }

    private fun insertDb() {
        Log.i(TAG, "insertDb")
        database.use {
            insert(
                PERSON,
                "id" to 0,
                "name" to "李金亮",
                "number" to "8471102174856"
            )
        }
        database.use {
            insert(
                PERSON,
                "id" to 1,
                "name" to "韩健",
                "number" to "0847101434342"
            )
        }
        database.use {
            insert(
                PERSON,
                "id" to 2,
                "name" to "张冲",
                "number" to "3471102473704"
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "requestCode=" + requestCode + ",resultCode=" + resultCode)
        if (requestCode == 0x11) {
            val str = data?.getStringExtra("number")
            if (!TextUtils.isEmpty(str)) {
                edit?.setText(str)
                result_tv?.setText("--")
            }
        }
    }

    private fun setOnClick() {
        demo_but?.setOnClickListener(View.OnClickListener {
            getResultWayforDemo()
        })
        db_but?.setOnClickListener(View.OnClickListener {
            val intent = Intent(MainActivity@ this, DbActivity::class.java)
            startActivityForResult(intent, 0x11)
        })
        result_but?.setOnClickListener(View.OnClickListener {
            val code = edit?.text.toString()
            val date = datef?.text.toString()
            getResultWay2(date, code)
        })
        oprate_but?.setOnClickListener(View.OnClickListener {
            val code = edit?.text.toString()
            val date = datef?.text.toString()
            val intent = Intent(MainActivity@ this, OpratePersonActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setTextView() {
        val string = SpUtil.getString(baseContext, "code", "8471102174856")
        edit?.setText(string)
        datef?.setText(getCurrentDate())
        appversion?.setText("版本:"+getAppversion())
    }
    private fun getAppversion():String{
        val pInfo = context.getPackageManager().getPackageInfo(packageName, 0)
        val version = pInfo.versionName
        return version
    }
    private fun initView() {
        demo_but = findViewById(R.id.demo_but)
        db_but = findViewById(R.id.right_db)
        result_but = findViewById(R.id.result_but)
        oprate_but = findViewById(R.id.oprate_person)
        demo_tv = findViewById(R.id.demo_tv)
        appversion = findViewById(R.id.appversion)
        result_tv = findViewById(R.id.result_tv)
        edit = findViewById(R.id.edit)
        datef = findViewById(R.id.datef)
    }

    private fun getCurrentDate(): String {
        var sim = SimpleDateFormat("yyyyMM")
        return sim.format(Date())
    }

    private fun getResultWayforDemo() {
        var map = mapOf("pageNo" to "0", "issueNumber" to "201905", "applyCode" to "0000103048641")
        HttpUtil.retrofit()
            .getHtmlResult(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val parse = parse(result)
                    val str = if (parse.first) "服务器状态良好" else "服务器崩溃"
                    demo_tv?.setText(str)
                },
                { error -> Log.e("TAG", "error:" + error.message) },
                { Log.d("TAG", "completed") })

    }

    private fun getResultWay2(date: String, code: String) {
        if (date.length != 6) {
            Toast.makeText(baseContext, "日期填写有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (code.length != 13) {
            Toast.makeText(baseContext, "编码填写有误", Toast.LENGTH_SHORT).show();
            return;
        }
        SpUtil.saveString(baseContext, "code", code)
        HttpUtil.retrofit()
            .getHtmlResults("0", date, code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val parse = parse(result)
                val str = if (parse.first) "${parse.second},\n恭喜你中签了!!!" else "下次再接再励!!!"
                result_tv?.setText(str)
            },
                { error -> Log.e("TAG", "error:" + error.message) },
                { Log.d("TAG", "completed") })

    }


}


