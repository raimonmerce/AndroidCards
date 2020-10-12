package com.example.holamon.stats

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class Friend(
    var surname: String = "",
    var age: Int = 0,
    var game: String = "",
    @PrimaryKey
    var name: String = ""
): RealmObject()
