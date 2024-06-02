package com.example.projetpendu

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demorecylcerview.DatabaseHelper
import java.util.Locale

class Dictionnaire : AppCompatActivity(), OnClickListener {
    lateinit var motInput : EditText
    lateinit var spinnerLng : Spinner
    lateinit var spinnerDif : Spinner
    lateinit var ajouterBtn : ConstraintLayout
    lateinit var motList : List<Mot>
    lateinit var motDao : MotDao
    lateinit var recycler: RecyclerView
    lateinit var adapter: MotRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dictionnaire)
        motInput = findViewById(R.id.motInput)
        spinnerLng = findViewById(R.id.spinnerLng)
        spinnerDif = findViewById(R.id.spinnerDif)
        ajouterBtn = findViewById(R.id.ajouterBtnDictio)
        ajouterBtn.setOnClickListener(this)
        motDao = MotDao(DatabaseHelper(this))
        motList = motDao.findAll()
        recycler = findViewById(R.id.motRecycler)

        //https://mkyong.com/android/android-spinner-drop-down-list-example/

        var langueList : List<String> = ArrayList()
        langueList += "FR"
        langueList += "EN"
        var langueAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,langueList)
        langueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLng.adapter = langueAdapter

        var difficulteList : List<String> = ArrayList()
        difficulteList += resources.getString(R.string.facile)
        difficulteList += resources.getString(R.string.normal)
        difficulteList += resources.getString(R.string.dur)
        var difficulteAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,difficulteList)
        difficulteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDif.adapter = difficulteAdapter


        setInfoAdapter()
    }

    private fun setInfoAdapter() {
        adapter = MotRecyclerAdapter(motList)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        adapter.onItemClick = {
            motDao.deleteMot(it)
            motList = motDao.findAll()
            setInfoAdapter()
        }
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ajouterBtnDictio -> {
                var motString = motInput.text.toString().uppercase(Locale.ROOT)
                if (motString.isEmpty()){
                    return
                }
                val langue = spinnerLng.selectedItem.toString()
                var difficulte = "FACILE"
                if (spinnerDif.selectedItem.toString().equals(resources.getString(R.string.normal))){
                    difficulte = "NORMAL"
                } else if (spinnerDif.selectedItem.toString().equals(resources.getString(R.string.dur))){
                    difficulte = "DIFFICILE"
                }
                val mot = Mot(motString,langue,difficulte)
                motDao.insertMot(mot)
                motList = motDao.findAll()
                setInfoAdapter()
                motInput.text.clear()

            }
        }
    }
}