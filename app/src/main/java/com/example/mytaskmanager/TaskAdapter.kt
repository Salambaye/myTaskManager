package com.example.mytaskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskCompleted: CheckBox = itemView.findViewById(R.id.taskCompleted)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTitle.text = task.name
        holder.taskCompleted.isChecked = task.isCompleted

        // Gérer les changements d'état de la case à cocher
        holder.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
        }

        // Supprimer une tâche
        holder.deleteButton.setOnClickListener {
            taskList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, taskList.size)
        }
    }

    override fun getItemCount() = taskList.size
}
