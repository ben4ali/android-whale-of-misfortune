package com.example.projetpendu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demorecylcerview.DatabaseHelper

class Preference : AppCompatActivity(), OnClickListener {
    lateinit var retourBtn : ConstraintLayout
    lateinit var frBtn : ConstraintLayout
    lateinit var enBtn : ConstraintLayout
    lateinit var ezBtn : ConstraintLayout
    lateinit var midBtn : ConstraintLayout
    lateinit var hrdBtn : ConstraintLayout
    lateinit var dictionnaireBtn : ConstraintLayout
    lateinit var settingDao : SettingDao
    lateinit var setting : Setting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preference)
        retourBtn = findViewById(R.id.retourPref)
        frBtn = findViewById(R.id.frBtn)
        enBtn = findViewById(R.id.enBtn)
        ezBtn = findViewById(R.id.ezBtn)
        midBtn = findViewById(R.id.medBtn)
        hrdBtn = findViewById(R.id.hrdBtn)
        dictionnaireBtn = findViewById(R.id.dictionnaireBtn)

        retourBtn.setOnClickListener(this)
        frBtn.setOnClickListener(this)
        enBtn.setOnClickListener(this)
        ezBtn.setOnClickListener(this)
        midBtn.setOnClickListener(this)
        hrdBtn.setOnClickListener(this)
        dictionnaireBtn.setOnClickListener(this)

        settingDao = SettingDao(DatabaseHelper(this))

        languageDisableBtns()
        difficultyDisableBtns()

        setting = settingDao.findFirst()?: Setting("ECHEC","FR","FACILE")
        if (setting.equals(Setting("ECHEC","FR","FACILE"))){
            println("ICI")
            setting = Setting("CONFIGURATION_BASE","FR","FACILE")
            settingDao.insertSetting(setting)
        } else{
            println("Setting : ${setting.langue} ${setting.difficulte}")
        }
        if (setting.langue == "FR"){
            println("FRANCAIS SET")
            frBtn.alpha = 1f
        } else {
            enBtn.alpha = 1f
        }
        if (setting.difficulte == "FACILE"){
            ezBtn.alpha = 1f
        } else if (setting.difficulte == "NORMAL"){
            midBtn.alpha = 1f
        } else {
            hrdBtn.alpha = 1f
        }

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.retourPref -> {
                finish()
            }
            R.id.dictionnaireBtn -> {
                var intention : Intent = Intent(this,Dictionnaire::class.java)
                startActivity(intention)
            }
            R.id.frBtn ->{
                languageDisableBtns()
                frBtn.alpha = 1f
                setting.langue = "FR"
                settingDao.updateSetting(setting)
            }
            R.id.enBtn ->{
                languageDisableBtns()
                enBtn.alpha = 1f
                setting.langue = "EN"
                settingDao.updateSetting(setting)
            }
            R.id.ezBtn -> {
                difficultyDisableBtns()
                ezBtn.alpha = 1f
                setting.difficulte = "FACILE"
                settingDao.updateSetting(setting)
            }
            R.id.medBtn -> {
                difficultyDisableBtns()
                midBtn.alpha = 1f
                setting.difficulte = "NORMAL"
                settingDao.updateSetting(setting)
            }
            R.id.hrdBtn -> {
                difficultyDisableBtns()
                hrdBtn.alpha = 1f
                setting.difficulte = "DIFFICILE"
                settingDao.updateSetting(setting)
            }
        }
    }
    fun languageDisableBtns(){
        frBtn.alpha = 0.5f
        enBtn.alpha = 0.5f
    }
    fun difficultyDisableBtns(){
        ezBtn.alpha = 0.5f
        midBtn.alpha = 0.5f
        hrdBtn.alpha =0.5f
    }
}