package com.example.demorecylcerview

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        const val DATABASE_VERSION = 8
        const val DATABASE_NAME = "Gestion.db"

        const val TABLE_NAME = "Partie"
        const val COLUMN_STATUS = "status"
        const val COLUMN_TEMPS = "temps"
        const val COLUMN_MOT = "mot"
        const val COLUMN_NB_ERREUR = "nbErreur"

        const val TABLE_NAME2 = "Mot"
        const val COLUMN_MOT2 = "mot"
        const val COLUMN_LANGUE = "langue"
        const val COLUMN_DIFFICULTE = "difficulte"

        const val TABLE_NAME3 = "Setting"
        const val COLUMMN_NOM = "nom"
        const val COLUMN_LANGUE2 = "langue"
        const val COLUMN_DIFFICULTE2 = "difficulte"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_STATUS TEXT," +
                "$COLUMN_TEMPS INTEGER," +
                "$COLUMN_MOT TEXT," +
                "$COLUMN_NB_ERREUR INTEGER)"
        val CREATE_TABLE2 = "CREATE TABLE $TABLE_NAME2 (" +
                "$COLUMN_MOT2 TEXT PRIMARY KEY," +
                "$COLUMN_LANGUE TEXT," +
                "$COLUMN_DIFFICULTE TEXT)"
        val CREATE_TABLE3 = "CREATE TABLE $TABLE_NAME3 (" +
                "$COLUMMN_NOM TEXT PRIMARY KEY," +
                "$COLUMN_LANGUE2 TEXT," +
                "$COLUMN_DIFFICULTE2 TEXT)"

        db?.execSQL(CREATE_TABLE)
        db?.execSQL(CREATE_TABLE2)
        db?.execSQL(CREATE_TABLE3)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        val DROP_TABLE2 = "DROP TABLE IF EXISTS $TABLE_NAME2"
        val DROP_TABLE3 = "DROP TABLE IF EXISTS $TABLE_NAME3"
        db?.execSQL(DROP_TABLE)
        db?.execSQL(DROP_TABLE2)
        db?.execSQL(DROP_TABLE3)
        onCreate(db)
    }
}