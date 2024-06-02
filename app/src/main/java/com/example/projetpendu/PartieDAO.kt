package com.example.projetpendu

import android.content.ContentValues
import com.example.demorecylcerview.DatabaseHelper

class PartieDAO (private val helper: DatabaseHelper){
    fun insertPartie(partie: Partie){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_STATUS, partie.status)
            put(DatabaseHelper.COLUMN_TEMPS, partie.temps)
            put(DatabaseHelper.COLUMN_MOT, partie.mot)
            put(DatabaseHelper.COLUMN_NB_ERREUR, partie.nbErreur)
        }
        db.insert(DatabaseHelper.TABLE_NAME, null,values)
        db.close()
    }
    fun findAll() : List<Partie>{
        val partieList = mutableListOf<Partie>()
        val db = helper.writableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME}"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STATUS))
            val temps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEMPS))
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MOT))
            val nbErreur = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NB_ERREUR))

            val partie = Partie(status,temps,mot,nbErreur)
            partieList.add(partie)
        }
        cursor.close()
        db.close()
        return partieList
    }
    fun deleteAll(){
        val db = helper.writableDatabase
        db.delete(DatabaseHelper.TABLE_NAME, null, null)
        db.close()
    }
}