package com.veepoo.szcarlottery.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.veepoo.szcarlottery.activity.PERSON
import org.jetbrains.anko.db.*

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(this)

class MyDatabaseOpenHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "carDataBase", null, 1) {

    init {
        instance = this
    }

    companion object {
        private var instance: MyDatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(ctx: Context) = instance ?: MyDatabaseOpenHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(PERSON, true, "id" to INTEGER + PRIMARY_KEY + UNIQUE, "number" to TEXT, "name" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(PERSON, true)
    }
}