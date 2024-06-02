package com.example.projetpendu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity(), OnClickListener {
    lateinit var commencerBtn : ConstraintLayout
    lateinit var prefBtn : ConstraintLayout
    lateinit var histoBtn : ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        commencerBtn = findViewById(R.id.commencerBtn)
        prefBtn = findViewById(R.id.prefBtn)
        histoBtn = findViewById(R.id.histoBtn)
        commencerBtn.setOnClickListener(this)
        prefBtn.setOnClickListener(this)
        histoBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.commencerBtn -> {
                val intention : Intent = Intent(this, PenduActivity::class.java)
                startActivity(intention)
            }
            R.id.prefBtn -> {
                val intention : Intent = Intent(this, Preference::class.java)
                startActivity(intention)
            }
            R.id.histoBtn -> {
                val intention: Intent = Intent(this, Historique::class.java)
                startActivity(intention)
            }
        }
    }
}