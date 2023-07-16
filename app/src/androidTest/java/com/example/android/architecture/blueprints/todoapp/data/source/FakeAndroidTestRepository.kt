package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FakeAndroidTestRepository :TasksRepository {

	var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()
	private val observableTasks = MutableLiveData<Result<List<Task>>>()

	override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
		return Result.Success(tasksServiceData.values.toList())
	}

	override suspend fun refreshTasks() {
		withContext(Dispatchers.Main){
			observableTasks.value = getTasks()
		}

	}

	override fun observeTasks(): LiveData<Result<List<Task>>> {
		runBlocking { refreshTasks() }
		return observableTasks
	}

	override suspend fun refreshTask(taskId: String) {
		val task = tasksServiceData[taskId]
		if (task != null) {
			observableTasks.value = Result.Success(listOf(task))
		} else {
			observableTasks.value = Result.Error(Exception("Task not found"))
		}
	}

	override fun observeTask(taskId: String): LiveData<Result<Task>> {
		val taskLiveData = MutableLiveData<Result<Task>>()
		val task = tasksServiceData[taskId]
		if (task != null) {
			taskLiveData.value = Result.Success(task)
		} else {
			taskLiveData.value = Result.Error(Exception("Task not found"))
		}
		return taskLiveData
	}

	override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
		val task = tasksServiceData[taskId]
		return if (task != null) {
			Result.Success(task)
		} else {
			Result.Error(Exception("Task not found"))
		}
	}

	override suspend fun saveTask(task: Task) {
		tasksServiceData[task.id] = task
		refreshTasks()
	}

	override suspend fun completeTask(task: Task) {
		task.isCompleted = true
		refreshTasks()
	}

	override suspend fun completeTask(taskId: String) {
		val task = tasksServiceData[taskId]
		if (task != null) {
			task.isCompleted = true
			refreshTasks()
		}
	}

	override suspend fun activateTask(task: Task) {
		task.isCompleted = false
		refreshTasks()
	}

	override suspend fun activateTask(taskId: String) {
		val task = tasksServiceData[taskId]
		if (task != null) {
			task.isCompleted = false
			refreshTasks()
		}
	}

	override suspend fun clearCompletedTasks() {
		val iterator = tasksServiceData.iterator()
		while (iterator.hasNext()) {
			val entry = iterator.next()
			if (entry.value.isCompleted) {
				iterator.remove()
			}
		}
		refreshTasks()
	}

	override suspend fun deleteAllTasks() {
		tasksServiceData.clear()
		refreshTasks()
	}

	override suspend fun deleteTask(taskId: String) {
		tasksServiceData.remove(taskId)
		refreshTasks()
	}

	fun addTasks(vararg tasks: Task) {
		for (task in tasks) {
			tasksServiceData[task.id] = task
		}
		runBlocking { refreshTasks() }
	}
}