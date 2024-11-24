package com.example.mytaskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskCompleted: CheckBox = itemView.findViewById(R.id.taskCompleted)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskTitle.text = taskList[position]
        holder.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Marquer la tâche comme complétée, vous pouvez l'ajouter dans une base de données ou changer son état
                taskList[position] = taskList[position] + " (Complétée)"
            } else {
                taskList[position] = taskList[position].replace(" (Complétée)", "")
            }
        }

        holder.deleteButton.setOnClickListener {
            // Supprimer la tâche
            taskList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = taskList.size
}
