package com.example.projetpendu

import android.content.ContentValues
import com.example.demorecylcerview.DatabaseHelper

class MotDao (private val helper: DatabaseHelper) {
    fun insertMot(mot: Mot){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_MOT2, mot.mot)
            put(DatabaseHelper.COLUMN_LANGUE, mot.langue)
            put(DatabaseHelper.COLUMN_DIFFICULTE, mot.difficulte)
        }
        db.insert(DatabaseHelper.TABLE_NAME2, null,values)
        db.close()
    }
    fun findAll() : List<Mot>{
        val motList = mutableListOf<Mot>()
        val db = helper.writableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME2}"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MOT2))
            val langue = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUE))
            val difficulte = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIFFICULTE))

            val newMot = Mot(mot,langue,difficulte)
            motList.add(newMot)
        }
        cursor.close()
        db.close()
        return motList
    }
    fun findByLangueDifficulte(langue: String, difficulte: String) : List<Mot>{
        val motList = mutableListOf<Mot>()
        val db = helper.writableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_NAME2} WHERE ${DatabaseHelper.COLUMN_LANGUE} = ? AND ${DatabaseHelper.COLUMN_DIFFICULTE} = ?"
        val cursor = db.rawQuery(query, arrayOf(langue,difficulte))

        while (cursor.moveToNext()){
            val mot = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MOT2))
            val langue = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUE))
            val difficulte = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIFFICULTE))

            val newMot = Mot(mot,langue,difficulte)
            motList.add(newMot)
        }
        cursor.close()
        db.close()
        return motList
    }

    fun deleteMot(mot: Mot){
        val db = helper.writableDatabase
        db.delete(DatabaseHelper.TABLE_NAME2, "${DatabaseHelper.COLUMN_MOT2} = ?", arrayOf(mot.mot))
        db.close()
    }
}