package com.example.myapplication.classes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joueur(val pseudo: String, var limcoins: Int = 500, var scoreJ: ListeScore = ListeScore()) : Parcelable {

    fun ajouterLimcoins(amount: Int) {
        limcoins += amount
    }

    fun retirerLimcoins(amount: Int) {
        limcoins -= amount
    }

    fun trierListeScore() {
        scoreJ.listeScore.sortWith(compareByDescending { it.questions })
    }

}