package com.example.projetpendu

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MatchSummaryActivity : AppCompatActivity(), OnClickListener {
    lateinit var statutMessage : TextView
    lateinit var reponseMessage : TextView
    lateinit var tempsMessage : TextView
    lateinit var nbErreurMessage : TextView
    lateinit var btnRestart : ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_match_summary)
        statutMessage = findViewById(R.id.statutMessage)
        reponseMessage = findViewById(R.id.reponse)
        tempsMessage = findViewById(R.id.tempsPlaceHolder)
        nbErreurMessage = findViewById(R.id.nbErreurPlaceHolder)
        btnRestart = findViewById(R.id.btnRestart)
        btnRestart.setOnClickListener(this)

        val statut = intent.getStringExtra("statut")
        val reponse = intent.getStringExtra("reponse")
        val temps : Int = intent.getIntExtra("temps",0)
        if (statut == "win") {
            //https://stackoverflow.com/questions/9585029/changing-value-of-r-string-programmatically
            statutMessage.text = resources.getString(R.string.victoire)
        }
        tempsMessage.text = resources.getString(R.string.temps) + " " + temps + "s"
        nbErreurMessage.text = resources.getString(R.string.nbErreur) + " " + intent.getIntExtra("nbErreur",0)
        reponseMessage.text = resources.getString(R.string.reponse) + " " + reponse

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRestart -> {
                finish()
            }
        }
    }
}

