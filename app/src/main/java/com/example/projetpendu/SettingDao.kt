package com.example.projetpendu

import android.content.ContentValues
import com.example.demorecylcerview.DatabaseHelper

class SettingDao (private val helper: DatabaseHelper){
    fun insertSetting(setting: Setting){
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMMN_NOM, setting.nom)
            put(DatabaseHelper.COLUMN_LANGUE2, setting.langue)
            put(DatabaseHelper.COLUMN_DIFFICULTE2, setting.difficulte)
        }
        db.insert(DatabaseHelper.TABLE_NAME3, null,values)
        db.close()
    }

    fun findFirst(): Setting? {
        val db = helper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_NAME3, arrayOf(DatabaseHelper.COLUMMN_NOM, DatabaseHelper.COLUMN_LANGUE2, DatabaseHelper.COLUMN_DIFFICULTE2), null, null, null, null, null)
        val setting = if (cursor.moveToFirst()){
            Setting(cursor.getString(0), cursor.getString(1), cursor.getString(2))
        } else {
            null
        }
        cursor.close()
        db.close()
        if (setting != null) {
            if (setting.nom == "CONFIGURATION_BASE"){
                return setting
            }
        }
        return null
    }


    fun updateSetting(setting: Setting){
        val db = helper.writableDatabase
        println(setting.nom)
        println(setting.langue)
        println(setting.difficulte)
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMMN_NOM, setting.nom)
            put(DatabaseHelper.COLUMN_LANGUE2, setting.langue)
            put(DatabaseHelper.COLUMN_DIFFICULTE2, setting.difficulte)
        }
        db.update(DatabaseHelper.TABLE_NAME3, values, "${DatabaseHelper.COLUMMN_NOM} = ?", arrayOf(setting.nom))
        db.close()
    }

}