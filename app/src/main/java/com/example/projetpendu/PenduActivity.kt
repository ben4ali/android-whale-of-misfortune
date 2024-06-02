package com.example.projetpendu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.demorecylcerview.DatabaseHelper

class PenduActivity : AppCompatActivity(), OnClickListener {
    lateinit var characterView : ImageView
    lateinit var letterHolder : LinearLayout
    lateinit var letterPlaceHolder : LinearLayout
    lateinit var prefBtn : ConstraintLayout
    lateinit var histoBtn : ConstraintLayout
    lateinit var partieDAO: PartieDAO
    lateinit var settingDAO: SettingDao
    lateinit var motDao: MotDao
    lateinit var setting: Setting
    lateinit var listeMots : ArrayList<Mot>
    lateinit var jeu : Jeu
    var btns = ArrayList<ImageButton>()
    var startTime : Int = 0
    //Pour disable les boutons quand on fait les animations
    var debounce : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        //initialisation de l'activité
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        characterView = findViewById(R.id.characterView)
        letterHolder = findViewById(R.id.letterHolder)
        letterPlaceHolder = findViewById(R.id.placeHolder)
        prefBtn = findViewById(R.id.prefBtn2)
        histoBtn = findViewById(R.id.histoBtn2)
        prefBtn.setOnClickListener(this)
        histoBtn.setOnClickListener(this)

        startTime = (System.currentTimeMillis()/1000).toInt()

        val helper = DatabaseHelper(this)
        partieDAO = PartieDAO(helper)
        settingDAO = SettingDao(helper)
        motDao = MotDao(helper)

        setting = settingDAO.findFirst()?: Setting("CONFIGURATION_BASE", "francais", "facile")
        listeMots = motDao.findByLangueDifficulte(setting.langue, setting.difficulte) as ArrayList<Mot>
        if (listeMots.isEmpty()){
            listeMots.add(Mot("BALEINE", "francais", "facile"))
            listeMots.add(Mot("POISSON", "francais", "facile"))
            listeMots.add(Mot("RAIE", "francais", "facile"))
            listeMots.add(Mot("REQUIN", "francais", "facile"))
            listeMots.add(Mot("VOITURE", "francais", "facile"))
            listeMots.add(Mot("AVION", "francais", "facile"))
            listeMots.add(Mot("MOTO", "francais", "facile"))
            listeMots.add(Mot("BATEAU", "francais", "facile"))
            listeMots.add(Mot("TRAIN", "francais", "facile"))
            listeMots.add(Mot("BUS", "francais", "facile"))
        }
        jeu = Jeu(listeMots)

        for (i in 1..26){
            val btn = findViewById<ImageButton>(resources.getIdentifier("btn$i", "id", packageName))
            btns.add(btn)
            btn.setOnClickListener(this)
        }

        //initialisation des éléments visuels de l'activité

        /*Ça c'est une libraire qui permet de jouer des gifs
        parce que android studio support pas ca de base*/
        Glide.with(this)
            .load(R.drawable.whale_animated )
            .into(findViewById(R.id.characterView))

        //https://github.com/bumptech/glide
    }
    override fun onClick(v: View?) {
        if (v is ImageButton && !debounce){
            var found = jeu.essayerUneLettre(v.tag.toString().first().uppercaseChar())
            if (found){
                for (i in 0..jeu.motADeviner.length-1){
                    if (v.tag == jeu.motADeviner[i].toString().lowercase()){
                        letterHolder.getChildAt(i).alpha = 1f
                    }
                }
            }

            var motCourant = ""
            for (i in 0..jeu.motADeviner.length-1){
                if (letterHolder.getChildAt(i).alpha == 1f){
                    motCourant += jeu.motADeviner[i]
                }
                else{
                    motCourant += "_"
                }
            }
            //SI IL GAGNE TOUT CE PASSE ICI
            if (jeu.estReussi(motCourant)){
                v.postDelayed({
                    var temps = (System.currentTimeMillis()/1000).toInt() - startTime
                    partieDAO.insertPartie(Partie("win", temps,jeu.motADeviner,jeu.nbErreurs))
                    var intention : Intent = Intent(this, MatchSummaryActivity::class.java)
                    intention.putExtra("statut", "win")
                    intention.putExtra("temps", temps)
                    intention.putExtra("reponse", jeu.motADeviner)
                    intention.putExtra("nbErreur", jeu.nbErreurs)
                    startActivity(intention)
                }, 500)
            }
            //Erreur si la lettre n'est pas dans le mot
            if (!found) {
                val errorAnimation: Int
                val temps: Long
                val idleAnimation: Int
                debounce = true
                effetErreur(v)
                when (jeu.nbErreurs) {
                    1 -> {
                        //effet de slash undertale
                        errorAnimation = R.drawable.whale_error1
                        temps = 3100
                        idleAnimation = R.drawable.whale_idle1
                    }
                    2 -> {
                        //kamehameha
                        errorAnimation = R.drawable.whale_error2
                        temps = 3255
                        idleAnimation = R.drawable.while_idle2
                    }
                    3 -> {
                        //portal
                        errorAnimation = R.drawable.whale_error3
                        temps = 3255
                        idleAnimation = R.drawable.whale_idle3
                    }
                    4 -> {
                        //épée laser star wars
                        errorAnimation = R.drawable.whale_error4
                        temps = 4255
                        idleAnimation = R.drawable.whale_idle4
                    }
                    5 -> {
                        //hollow purple dans jujutsu kaisen
                        errorAnimation = R.drawable.whale_error5
                        temps = 7020
                        idleAnimation = R.drawable.whale_idle5
                    }
                    6 -> {
                        //thanos snap
                        //SI IL PERD TOUT CE PASSE ICI
                        errorAnimation = R.drawable.whale_error6
                        temps = 3255
                        idleAnimation = R.drawable.whale_idlefinal
                        v.postDelayed({
                            var temps = (System.currentTimeMillis()/1000).toInt() - startTime
                            partieDAO.insertPartie(Partie("perdu", temps,jeu.motADeviner,6))
                            val intention = Intent(this, MatchSummaryActivity::class.java)
                            intention.putExtra("statut", "perdu")
                            intention.putExtra("temps", temps)
                            intention.putExtra("reponse", jeu.motADeviner)
                            intention.putExtra("nbErreur", 6)
                            startActivity(intention)
                        }, temps)
                    }
                    else -> {
                        return
                    }
                }

                playGif(errorAnimation, temps, idleAnimation, characterView)
                if (jeu.nbErreurs != 6) {
                    v.postDelayed({
                        debounce = false
                    }, temps)
                }
            } else {
                effetBonneReponse(v)
                v.isClickable = false
            }
        }
        else{
            when(v?.id){
                R.id.prefBtn2 -> {
                    val intention: Intent = Intent(this, Preference::class.java)
                    startActivity(intention)
                }
                R.id.histoBtn2 -> {
                    val intention: Intent = Intent(this, Historique::class.java)
                    startActivity(intention)
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        startTime = (System.currentTimeMillis()/1000).toInt()
        debounce = false

        setting = settingDAO.findFirst()?: Setting("CONFIGURATION_BASE", "francais", "facile")
        listeMots = motDao.findByLangueDifficulte(setting.langue, setting.difficulte) as ArrayList<Mot>
        //si listeMots est vide, on rajoute des mots par défaut comme baleine, poisson, raie et requin
        if (listeMots.isEmpty()){
            listeMots.add(Mot("BALEINE", "francais", "facile"))
            listeMots.add(Mot("POISSON", "francais", "facile"))
            listeMots.add(Mot("RAIE", "francais", "facile"))
            listeMots.add(Mot("REQUIN", "francais", "facile"))
            listeMots.add(Mot("VOITURE", "francais", "facile"))
            listeMots.add(Mot("AVION", "francais", "facile"))
            listeMots.add(Mot("MOTO", "francais", "facile"))
            listeMots.add(Mot("BATEAU", "francais", "facile"))
            listeMots.add(Mot("TRAIN", "francais", "facile"))
            listeMots.add(Mot("BUS", "francais", "facile"))
        }
        letterPlaceHolder.removeAllViews()
        letterHolder.removeAllViews()
        jeu.mots = listeMots
        jeu.recommencer()
        addLetterToPlaceHolder(jeu.motADeviner,letterPlaceHolder,letterHolder,btns)

        for (btn in btns){
            btn.isClickable = true
            btn.alpha = 1f
        }
        for (i in 0..jeu.motADeviner.length-1){
            letterHolder.getChildAt(i).alpha = 0f
        }
        Glide.with(this)
            .load(R.drawable.whale_animated )
            .into(findViewById(R.id.characterView))
    }
}

/**
 * Fonction qui permet d'ajouter des placeholders pour les lettres du mot
 * @param mot : String, le mot à deviner
 * @param letterPlaceHolder : LinearLayout, le layout où ajouter les placeholders
 * @param letterholder : LinearLayout, le layout où ajouter les lettres
 * @param btns : ArrayList<ImageButton>, la liste des boutons
 */
fun addLetterToPlaceHolder(mot: String, letterPlaceHolder: LinearLayout, letterholder : LinearLayout,btns: ArrayList<ImageButton>){
    for (i in 1..mot.length){
        val letterView = ImageView(letterPlaceHolder.context)
        letterView.layoutParams = ConstraintLayout.LayoutParams(128, 128)
        letterView.setPadding(10,0,10,0)
        letterView.setImageResource(R.drawable.letter_holder)
        letterPlaceHolder.addView(letterView)

        for (btn in btns){
            if (btn.tag == mot[i-1].toString().lowercase()){
                val letter = ImageView(letterholder.context)
                letter.layoutParams = ConstraintLayout.LayoutParams(128, 128)
                letter.setPadding(10,0,10,0)
                val letterResId = letterholder.context.resources.getIdentifier("letter_${mot[i-1].lowercase()}", "drawable", letterholder.context.packageName)
                letter.setImageResource(letterResId)
                letter.alpha = 0f
                letterholder.addView(letter)
            }
        }
    }
}

/**
 * Fonction qui permet de jouer un effet d'erreur sur un bouton
 * @param btn : ImageButton, le bouton sur lequel jouer l'effet
 */
fun effetErreur(btn: ImageButton){
    //https://stackoverflow.com/questions/42379301/how-to-use-postdelayed-correctly-in-android-studio
    btn.isClickable = false
    btn.setColorFilter(Color.RED)
    btn.postDelayed({
        btn.setColorFilter(Color.TRANSPARENT)
        btn.postDelayed({
            btn.setColorFilter(Color.RED)
            btn.postDelayed({
                btn.setColorFilter(Color.TRANSPARENT)
                btn.postDelayed({
                    btn.setColorFilter(Color.rgb(255, 255, 255))
                    btn.alpha = 0.5f
                }, 100)
            }, 100)
        }, 100)
    }, 100)
}
fun effetBonneReponse(btn: ImageButton){
    btn.isClickable = false
    btn.setColorFilter(Color.GREEN)
    btn.postDelayed({
        btn.setColorFilter(Color.TRANSPARENT)
        btn.postDelayed({
            btn.setColorFilter(Color.GREEN)
            btn.postDelayed({
                btn.setColorFilter(Color.TRANSPARENT)
                btn.postDelayed({
                    btn.setColorFilter(Color.rgb(255, 255, 255))
                    btn.alpha = 0.5f
                }, 100)
            }, 100)
        }, 100)
    }, 100)
}

/**
 * Fonction qui permet de jouer une animation avec des gifs
 * @param premier_gif : Int, le premier gif à jouer
 * @param time : Long, le temps avant de jouer le deuxième gif
 * @param deuxieme_gif : Int, le deuxième gif à jouer
 * @param view : ImageView, la vue où jouer les gifs
 */
fun playGif(premier_gif: Int, time: Long, deuxieme_gif: Int, view: ImageView){
    Glide.with(view.context)
        .load(premier_gif)
        .into(view)
    view.postDelayed({
        Glide.with(view.context)
            .load(deuxieme_gif)
            .into(view)
    }, time)
}