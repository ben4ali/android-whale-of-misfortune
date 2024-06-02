package com.example.projetpendu

import kotlin.random.Random

class Jeu(mots: ArrayList<Mot>) {

    var mots = mots

    // Propriétés pour le pointage et le nombre d'erreurs
    private var nombre_pointage = 0
    private var nb_Erreurs = 0

    // Propriétés exposant le pointage et le nombre d'erreurs
    var pointage: Int = 0
        get() = nombre_pointage
    var nbErreurs: Int = 0
        get() = nb_Erreurs

    // Mot à deviner
    var motADeviner: String

    // Initialisation, sélectionne un mot aléatoire et initialise les compteurs
    init {
        // Vérifie si la liste de mots est vide
        if (mots.isEmpty()){
            throw IllegalArgumentException("la liste ne peut pas etre vide.")
        }

        nombre_pointage = 0
        nb_Erreurs = 0
        // Sélectionne un mot aléatoire dans la liste
        motADeviner = mots.random().mot
    }

    // Méthode pour essayer une lettre dans le mot à deviner
    fun essayerUneLettre(lettre: Char): Boolean {
        // Parcourt le mot à deviner
        for (i in motADeviner.indices) {
            if (motADeviner[i] == lettre) {
                nombre_pointage++
                return true
            }
        }
        // Si aucun indice n'est trouvé, incrémente le nombre d'erreurs
        nb_Erreurs++
        return false
    }

    // Méthode pour vérifier si le mot proposé correspond au mot à deviner
    fun estReussi(motCourant: String): Boolean {
        // Vérifie si le mot proposé est égal au mot à deviner
        return motCourant == motADeviner
    }
    fun recommencer(){
        nombre_pointage = 0
        nb_Erreurs = 0
        motADeviner = mots.random().mot
    }
}
