package com.example.myapplication.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class QuestionReponseReceiver( val onReceive: (QuestionReponse) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val question = intent.getParcelableExtra("question", QuestionReponse::class.java)
        if (question != null) {
            onReceive(question)
        }

    }
}