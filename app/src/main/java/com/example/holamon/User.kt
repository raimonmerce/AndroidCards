package com.example.holamon.stats

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class User(
    @PrimaryKey
    var name: String = ""
): RealmObject()
