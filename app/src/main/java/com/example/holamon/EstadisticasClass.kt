package com.example.holamon

import android.widget.Toast
import com.example.holamon.stats.Record
import com.example.holamon.stats.Friend
import com.example.holamon.stats.User
import io.realm.Realm

class EstadisticasClass {
    companion object {

        private lateinit var realm: Realm

        fun initDatabase(){
            realm = Realm.getDefaultInstance()

        }

        fun addRecord(record: Record) {
            realm.executeTransaction {
                it.copyToRealm(record)
            }
        }

        fun addUser(user: User) {
            realm.executeTransaction {
                it.copyToRealm(user)
            }
        }

        fun deleteFriend(user: User) {
            realm.executeTransaction {
                user.deleteFromRealm()
            }
        }

        fun addFriend(friend: Friend) {
            realm.executeTransaction {
                it.copyToRealm(friend)
            }
        }

        fun deleteFriend(friend: Friend) {
            realm.executeTransaction {
                friend.deleteFromRealm()
            }
        }

        fun getAllRecords(): MutableList<Record>
        {
            return realm.where(Record::class.java).findAll().toMutableList()
        }

        fun getAllFriends(): MutableList<Friend>
        {
            return realm.where(Friend::class.java).findAll().toMutableList()
        }

        fun haveUser(): Boolean {
            return !realm.where(User::class.java).findAll().toMutableList().isEmpty()
        }

        fun getUserString(): String {
            for (usr in realm.where(User::class.java).findAll().toMutableList()) {
                return usr.name
            }
            return "No User"
        }

        fun deleteUser() {
            for (usr in realm.where(User::class.java).findAll().toMutableList()) {
                realm.executeTransaction {
                    usr.deleteFromRealm()
                }
            }
        }
    }
}
