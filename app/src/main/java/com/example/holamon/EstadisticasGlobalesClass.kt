package com.example.holamon

import android.content.Context
import android.widget.Toast
import com.example.holamon.stats.Record
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.realm.Realm
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class EstadisticasGlobalesClass {

    companion object {
        public val ip = "52.47.125.164"
        public val mainClient = OkHttpClient()

        public fun addNewRecord(record: Record, context: Context): String {
            val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val recordJson =
                Gson().toJson(record).toRequestBody(jsonMediaType)
            var res: String = "Nothing"
            val requestPost = Request
                .Builder()
                .post(recordJson)
                .url("http://$ip:8080/scores")
                .build()

            mainClient.newCall(requestPost).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Error al añadir nuevo record!")
                    println(e.toString())
                    res = "Error"
                    //Toast.makeText(context,"Mal 2 ",Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        println("Nuevo record añadido correctamente!")
                        //Toast.makeText(context,"Bien ",Toast.LENGTH_SHORT).show()
                        res = "Succes"
                        // success
                    } else {
                        println("Error al añadir nuevo record!")
                        println(response.toString())
                        println(response.body?.string())
                        res = "Error"
                        //Toast.makeText(context,"Mal ",Toast.LENGTH_SHORT).show()
                    }
                }
            })
            return res
        }
    }
}
