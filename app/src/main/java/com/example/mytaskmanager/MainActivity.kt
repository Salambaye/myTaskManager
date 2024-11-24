package com.example.mytaskmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytaskmanager.ui.theme.MyTaskManagerTheme

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf("Acheter des courses", "Faire du sport", "Préparer la réunion")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter

        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            // Ajouter une tâche à la liste
            val newTask = "Nouvelle tâche"  // Vous pouvez modifier pour afficher une boîte de dialogue pour entrer un nom
            taskList.add(newTask)
            taskAdapter.notifyItemInserted(taskList.size - 1)  // Notifie l'adaptateur d'ajouter un élément
        }

        Toast.makeText(this, "Bienvenue dans l'application de gestion de tâches", Toast.LENGTH_SHORT).show()
    }
}
