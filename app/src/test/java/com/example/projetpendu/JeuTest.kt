package com.example.projetpendu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertFalse

class JeuTest {

    // Teste si la méthode essayerUneLettre fonctionne correctement pour une lettre correcte
    @Test
    fun testEssayerUneLettre_LettreCorrecte() {
        val mots = ArrayList<Mot>()
        mots.add(Mot("bonjour", "français", "facile"))
        var jeu: Jeu = Jeu(mots)
        val indices = jeu.essayerUneLettre('o')
        assertTrue(indices)
        assertEquals(1, jeu.pointage) // Vérifie si le pointage a été mis à jour correctement
        assertEquals(0, jeu.nbErreurs) // Vérifie si le nombre d'erreurs est toujours à 0
    }

    // Teste si la méthode essayerUneLettre fonctionne correctement pour une lettre incorrecte
    @Test
    fun testEssayerUneLettre_LettreIncorrecte() {
        val mots = ArrayList<Mot>()
        mots.add(Mot("bonjour", "français", "facile"))
        var jeu: Jeu = Jeu(mots)
        val indices = jeu.essayerUneLettre('z')
        assertFalse(indices)
        assertEquals(0, jeu.pointage) // Vérifie si le pointage a été mis à jour correctement
        assertEquals(1, jeu.nbErreurs) // Vérifie si le nombre d'erreurs est toujours à 0
    }

    // Teste si la méthode estReussi renvoie correctement false pour un mot incorrect
    @Test
    fun testMotIncorrect() {
        val mots = ArrayList<Mot>()
        mots.add(Mot("bonjour", "français", "facile"))
        var jeu: Jeu = Jeu(mots)
        assertFalse(jeu.estReussi("auto"))
    }

    // Teste si la méthode estReussi renvoie correctement true pour un mot correct
    @Test
    fun testMotCorrect() {
        val mots = ArrayList<Mot>()
        mots.add(Mot("bonjour", "français", "facile"))
        var jeu: Jeu = Jeu(mots)
        assertTrue(jeu.estReussi("bonjour"))
    }

    // Teste la methode recommencer
    @Test
    fun testRecommencer(){
        val mots = ArrayList<Mot>()
        mots.add(Mot("bonjour", "français", "facile"))
        var jeu: Jeu = Jeu(mots)
        jeu.nbErreurs = 5
        jeu.pointage = 5

        jeu.recommencer()
        assertEquals(0,jeu.nbErreurs)
        assertEquals(0,jeu.pointage)
    }
}
