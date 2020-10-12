package com.example.holamon.game

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import com.example.holamon.*
import com.example.holamon.stats.Record
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class GameFragment : Fragment() {
    var lastKey: Int = 0
    var lastImage: Int = 0
    var cardsSolved: Int = 0
    var waiting : Boolean = false
    var isWorking: Boolean = false


    val flippableCardMap: MutableMap<Int, FlippableCard> = mutableMapOf<Int, FlippableCard>()
    private lateinit var meter: Chronometer
    private lateinit var viewFrag: View
    private lateinit var buttonStart: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFrag = inflater.inflate(R.layout.fragment_game, container, false)

        meter = viewFrag.findViewById<Chronometer>(R.id.cronometre)
        meter.setBase(SystemClock.elapsedRealtime());

        flippableCardMap.put(1, FlippableCard(viewFrag.findViewById(R.id.cardView1)))
        flippableCardMap.put(2, FlippableCard(viewFrag.findViewById(R.id.cardView2)))
        flippableCardMap.put(3,FlippableCard(viewFrag.findViewById(R.id.cardView3)))
        flippableCardMap.put(4,FlippableCard(viewFrag.findViewById(R.id.cardView4)))
        flippableCardMap.put(5,FlippableCard(viewFrag.findViewById(R.id.cardView5)))
        flippableCardMap.put(6,FlippableCard(viewFrag.findViewById(R.id.cardView6)))
        flippableCardMap.put(7,FlippableCard(viewFrag.findViewById(R.id.cardView7)))
        flippableCardMap.put(8,FlippableCard(viewFrag.findViewById(R.id.cardView8)))
        flippableCardMap.put(9,FlippableCard(viewFrag.findViewById(R.id.cardView9)))
        flippableCardMap.put(10,FlippableCard(viewFrag.findViewById(R.id.cardView10)))
        flippableCardMap.put(11,FlippableCard(viewFrag.findViewById(R.id.cardView11)))
        flippableCardMap.put(12,FlippableCard(viewFrag.findViewById(R.id.cardView12)))

        buttonStart = viewFrag.findViewById<Button>(R.id.buttonStart)

        buttonStart.setOnClickListener{
            startGame()
        }

        return viewFrag
    }

    fun flipAllCards (flipped: Boolean){
        for ((key, card) in flippableCardMap) {
            if (flipped) {
                card.imageView.setImageResource(card.idImage)
                card.isFlipped = true
                card.locked = true
            } else {
                card.imageView.setImageResource(R.drawable.hearth_cardback)
                card.isFlipped = false
                card.locked = false
            }
        }
    }

    private fun setOnClickListener(flippableCard: FlippableCard, key: Int) {
        flippableCard.imageView.setOnClickListener {
            if (!flippableCard.locked && !waiting) {
                if (lastKey == 0){ //Primera carta que actives
                    flippableCard.locked = true
                    flipCard(flippableCard)
                    lastImage = flippableCard.idImage
                    lastKey = key
                } else {// Segona carta que actives
                    flipCard(flippableCard)
                    waiting = true
                    if (lastImage == flippableCard.idImage){ //Son parella
                        flippableCard.locked = true
                        cardsSolved += 2
                        if (cardsSolved == 12){
                            finishGame()
                        }

                    } else { //No son parella
                        flippableCard.locked = false
                        flipCard(flippableCard)
                        flippableCardMap[lastKey]?.locked = false
                        flippableCardMap[lastKey]?.isFlipped = false
                        flippableCardMap[lastKey]?.imageView?.setImageResource(
                            R.drawable.hearth_cardback
                        )
                    }
                    waiting = false
                    lastImage = 0
                    lastKey = 0
                }
            }
        }
    }

     fun flipCard (flippableCard: FlippableCard){
        if (flippableCard.isFlipped) {
            flippableCard.imageView.setImageResource(R.drawable.hearth_cardback)
        } else {
            flippableCard.imageView.setImageResource(flippableCard.idImage)
        }
        flippableCard.isFlipped = !flippableCard.isFlipped
    }

    fun closeActivity() {
        (activity as MainActivity).finishMainActivity()
        meter.setBase(SystemClock.elapsedRealtime());
    }

    fun startGame(){
        cardsSolved = 0
        val cardIdList: Queue<Int> = LinkedList<Int>(
            listOf(
                R.drawable.mana,
                R.drawable.mana,
                R.drawable.alex,
                R.drawable.alex,
                R.drawable.lord,
                R.drawable.lord,
                R.drawable.leeroy,
                R.drawable.leeroy,
                R.drawable.hex,
                R.drawable.hex,
                R.drawable.dummy,
                R.drawable.dummy
            ).shuffled()
        )

        for ((key, card) in flippableCardMap) {
            var id: Int
            if (!cardIdList.isEmpty()) {
                id = cardIdList.poll()
            }
            else id = R.drawable.dummy
            card.idImage = id
        }

        flipAllCards(true)
        buttonStart.setText("RESTART")
        meter.setBase(SystemClock.elapsedRealtime());
        Handler().postDelayed({
            flipAllCards(false)

            for ((key, card) in flippableCardMap) {
                setOnClickListener(card, key)
            }

            meter.start()
        }, 1000)
    }

    private fun finishGame(){
        val timeElapsed = SystemClock.elapsedRealtime() - meter.base
        val formattedTime = SimpleDateFormat("mm:ss").format(timeElapsed)
        //Toast.makeText(applicationContext,"Win: " + formattedTime,Toast.LENGTH_SHORT).show()

        meter.stop();

        val dialog = FinishGame.buildDialog(
            viewFrag.context,
            formattedTime,
            this::startGame
        )
        dialog.show()

        val data = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().time)
        val res: Record =
            Record(
                EstadisticasClass.getUserString(),
                formattedTime,
                data
            )
        EstadisticasClass.addRecord(res)
        addNewRecord(res, viewFrag.context)

        buttonStart.setText("START")
    }

    fun addNewRecord(record: Record, context: Context) {
        val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val recordJson =
            Gson().toJson(record).toRequestBody(jsonMediaType)
        val requestPost = Request
            .Builder()
            .post(recordJson)
            .url("http://${EstadisticasGlobalesClass.ip}:8080/scores")
            .build()

        EstadisticasGlobalesClass.mainClient.newCall(requestPost).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error al añadir nuevo record!")
                println(e.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Nuevo record añadido correctamente!")
                } else {
                    println("Error al añadir nuevo record!")
                    println(response.toString())
                    println(response.body?.string())
                    //Toast.makeText(context,"Mal ",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}