package com.example.myapplication.classes
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.example.myapplication.GameActivity
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreJoueur (
    val temps: String,
    val questions:Int,
): Parcelable



@Parcelize
class ListeScore(
    var listeScore:MutableList<ScoreJoueur> = mutableListOf(
        ScoreJoueur("0:1",0),
        ScoreJoueur("0:2",1),
        ScoreJoueur("0:3",2),
        ScoreJoueur("0:4",3),


    )

):Parcelable


