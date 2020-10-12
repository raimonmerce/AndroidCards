package com.example.holamon

import android.widget.Toast
import com.example.holamon.stats.Record
import io.realm.Realm

class LoginData {
    private lateinit var realm: Realm

    fun initDatabase(){
        realm = Realm.getDefaultInstance()

    }

    fun getAll(): MutableList<Record>
    {
        return realm.where(Record::class.java).findAll().toMutableList()
    }

    fun add(record: Record) {
        realm.executeTransaction {
            it.copyToRealm(record)
        }
    }

}