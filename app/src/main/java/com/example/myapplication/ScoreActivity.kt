package com.example.myapplication
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.classes.Joueur
import com.example.myapplication.classes.ListeScore

class ScoreActivity : AppCompatActivity() {
    lateinit var score1: TextView
    lateinit var score2: TextView
    lateinit var score3: TextView
    lateinit var score4: TextView
    lateinit var joueur : Joueur

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joueur = intent.getParcelableExtra("joueur",Joueur::class.java)!!
        joueur.trierListeScore()
        setContentView(R.layout.score_activity)
        val btnReturn = findViewById<Button>(R.id.return_btn)
        btnReturn.setOnClickListener {
            retour()
        }
        score1= findViewById(R.id.score1)
        score2= findViewById(R.id.score2)
        score3= findViewById(R.id.score3)
        score4= findViewById(R.id.score4)
        majScore()
    }


    fun majScore(){
        score1.text = "Temps: ${joueur.scoreJ.listeScore[0].temps}  Questions: ${joueur.scoreJ.listeScore[0].questions}"
        score2.text = "Temps: ${joueur.scoreJ.listeScore[1].temps} Questions: ${joueur.scoreJ.listeScore[1].questions}"
        score3.text = "Temps: ${joueur.scoreJ.listeScore[2].temps} Questions: ${joueur.scoreJ.listeScore[2].questions}"
        score4.text = "Temps: ${joueur.scoreJ.listeScore[3].temps} Questions: ${joueur.scoreJ.listeScore[3].questions}"
    }

    fun retour(){
        val intent = Intent(this, ModeActivity::class.java)

        intent.putExtra("joueur",joueur)
        startActivity(intent)
    }


}

