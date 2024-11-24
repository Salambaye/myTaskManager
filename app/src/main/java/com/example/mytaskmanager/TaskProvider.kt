package com.example.mytaskmanager

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class TaskProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.example.mytaskmanager.provider"
        const val TASKS_TABLE = "tasks"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TASKS_TABLE")
    }

    private lateinit var database: SQLiteDatabase
    override fun onCreate(): Boolean {
        val dbHelper = TaskDatabaseHelper(context!!)
        database = dbHelper.writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // Pour partager les tâches
        return database.query(
            TASKS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    //Pour insérer les données dans la base
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = database.insert(TASKS_TABLE, null, values)
        return if (id != -1L) {
            Uri.withAppendedPath(CONTENT_URI, id.toString())
        } else {
            null
        }
    }

    //Pour mettre à jour des données existantes
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return database.update(TASKS_TABLE, values, selection, selectionArgs)
    }

    //Pour supprimer les données
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return database.delete(TASKS_TABLE, selection, selectionArgs)
    }

    // Classe pour la gestion de la base SQLite
    class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "tasks.db", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                """
                CREATE TABLE $TASKS_TABLE (
                    _id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    completed INTEGER DEFAULT 0
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TASKS_TABLE")
            onCreate(db)
        }
    }

    // Retourne le type MIME des données pour un URI spécifique
    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/$TASKS_TABLE"
    }

}