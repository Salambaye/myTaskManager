package com.example.mytaskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

data class Task(val name: String, var isCompleted: Boolean = false)

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf(
        Task("Acheter des courses"),
        Task("Faire du sport"),
        Task("Préparer la réunion")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialiser le RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialiser l'adaptateur
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter

        // Bouton "Ajouter une tâche"
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            showAddTaskDialog() // Affiche la boîte de dialogue
        }

        // Toast de bienvenue
        Toast.makeText(this, "Bienvenue dans l'application de gestion de tâches", Toast.LENGTH_SHORT).show()
    }

    private fun showAddTaskDialog() {
        // Créer une boîte de dialogue pour ajouter une nouvelle tâche
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val taskInput = dialogView.findViewById<TextInputEditText>(R.id.taskInput)

        AlertDialog.Builder(this)
            .setTitle("Nouvelle tâche")
            .setView(dialogView)
            .setPositiveButton("Ajouter") { _, _ ->
                val taskName = taskInput.text.toString().trim()
                if (taskName.isNotEmpty()) {
                    // Ajouter la tâche et mettre à jour la liste
                    val newTask = Task(taskName)
                    taskList.add(newTask)
                    taskAdapter.notifyItemInserted(taskList.size - 1)
                    recyclerView.scrollToPosition(taskList.size - 1)
                } else {
                    Toast.makeText(this, "La tâche ne peut pas être vide", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }
}
