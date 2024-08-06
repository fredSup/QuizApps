package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.classes.*


class GameActivity : AppCompatActivity() {

    lateinit var receiver: QuestionReponseReceiver
    lateinit var plateau: PlateauDeJeu
    lateinit var btnReponse1: Button
    lateinit var btnReponse2: Button
    lateinit var btnReponse3: Button
    lateinit var btnReponse4: Button
    lateinit var questionText: TextView
    lateinit var tempsText: TextView
    lateinit var progresText: TextView
    lateinit var limcoinTextView: TextView
    lateinit var tempsSave :String
    lateinit var joueur : Joueur
    var duree = 60000

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        plateau = PlateauDeJeu()

        receiver = QuestionReponseReceiver { question ->
            plateau.questionReponse = question // Affecter la question reçue au plateau de jeu
            majPlateau()
        }
        val filter = IntentFilter("com.example.myapplication.QUESTION")
        registerReceiver(receiver, filter)



        questionText = findViewById(R.id.textViewQuestion)
        btnReponse1 = findViewById(R.id.buttonReponse1)
        btnReponse1.setOnClickListener {
            verifierReponse(plateau.questionReponse!!, btnReponse1.text.toString())
        }

        btnReponse2 = findViewById(R.id.buttonReponse2)
        btnReponse2.setOnClickListener {
            verifierReponse(plateau.questionReponse!!, btnReponse2.text.toString())
        }

        btnReponse3 = findViewById(R.id.buttonReponse3)
        btnReponse3.setOnClickListener {
            verifierReponse(plateau.questionReponse!!, btnReponse3.text.toString())
        }

        btnReponse4 = findViewById(R.id.buttonReponse4)
        btnReponse4.setOnClickListener {
            verifierReponse(plateau.questionReponse!!, btnReponse4.text.toString())
        }
        progresText = findViewById(R.id.proges)
        tempsText = findViewById(R.id.temps)
        val timer = object: CountDownTimer(duree.toLong(), 1000) {
            override fun onTick(temps: Long) {
                // Convertir les millisecondes restantes en minutes et secondes
                val minutes = temps / 1000 / 60
                val seconds = temps / 1000 % 60

                // Mettre à jour le texte du TextView
                tempsSave = String.format("%02d:%02d", minutes, seconds)
                tempsText.text = tempsSave
            }

            override fun onFinish() {
                perdu() // Appeler la fonction perdu() lorsque le compte à rebours est terminé
            }
        }

        timer.start()

        joueur = intent.getParcelableExtra("joueur",Joueur::class.java)!!
        limcoinTextView = findViewById(R.id.limcoins)
        limcoinTextView.text = joueur.limcoins.toString()




        // Lancer le service au démarrage de l'activité
        val intent = Intent(this, TriviaService::class.java)
        startService(intent)



    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    fun majPlateau() {
        btnReponse1.text = plateau.questionReponse?.reponse1
        btnReponse2.text = plateau.questionReponse?.reponse2
        btnReponse3.text = plateau.questionReponse?.reponse3
        btnReponse4.text = plateau.questionReponse?.reponse4
        questionText.text = plateau.questionReponse?.question
        progresText.text = plateau.progression.toString()
    }

    fun verifierReponse(questionReponse: QuestionReponse, reponse: String) {
        if (questionReponse.verifReponse(reponse)) {
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show()
            plateau.progression ++
            majNiveau()
            // Lancer le service pour une nouvelle question
            val intent = Intent(this, TriviaService::class.java)
            startService(intent)
        } else {
            Toast.makeText(this, "Mauvaise réponse!", Toast.LENGTH_SHORT).show()
            perdu()
        }
    }

    fun perdu(){
        Toast.makeText(this, "Vous avez perdu!", Toast.LENGTH_SHORT).show()

        val scoreSave = ScoreJoueur(tempsSave,plateau.progression)
        joueur.scoreJ.listeScore.add(scoreSave)


        val intentService = Intent(this, TriviaService::class.java)
        intentService.putExtra("resetList",true)
        startService(intentService)

        val intent = Intent(this,ModeActivity::class.java)
        intent.putExtra("joueur",joueur)
        startActivity(intent)


    }
        fun majNiveau(){
        if (plateau.progression % 11 == 0){//on verifie si la progression du joueur est un multiple de 11
            joueur.ajouterLimcoins(200)
            limcoinTextView.text = joueur.limcoins.toString()
            duree= duree - 10000
            val intentService = Intent(this, TriviaService::class.java)
            intentService.putExtra("resetList",true)
            startService(intentService)
        }
        else if (plateau.progression  % 3 == 0){
            joueur.ajouterLimcoins(30)
            limcoinTextView.text = joueur.limcoins.toString()
        }


    }
}