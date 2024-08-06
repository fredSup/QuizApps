package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.classes.*


class ModeActivity : AppCompatActivity() {
    lateinit var joueur: Joueur
    lateinit var nb_limcoins: TextView
    lateinit var listeScore: ListeScore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)

        val btnModeClassique = findViewById<Button>(R.id.btn_classic)
        btnModeClassique.setOnClickListener {
            modeClassique()
        }
        val btnVoirScore = findViewById<Button>(R.id.score_btn)
        btnVoirScore.setOnClickListener{
            voirScore()
        }

        nb_limcoins = findViewById(R.id.nb_limcoins)

        val limcoinsButton = findViewById<Button>(R.id.limcoins_btn)
        limcoinsButton.setOnClickListener {
            refillerLimcoins()
        }
        joueur = intent.getParcelableExtra("joueur",Joueur::class.java)!!
        majLimcoins()

    }

    private fun majLimcoins(){
        nb_limcoins.text = joueur.limcoins.toString()
    }
    private fun modeClassique(){
        joueur.retirerLimcoins(100)
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("joueur",joueur)
        startActivity(intent)
    }

    private fun voirScore(){
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("joueur",joueur)
        startActivity(intent)
    }

    private fun refillerLimcoins(){
        joueur.ajouterLimcoins(500)
        majLimcoins()
    }

    fun modeDefi(){
    }
}