package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config




class TasksViewModelTest {

	private lateinit var tasksRepository: FakeTestRepository

	// Subject under test
	private lateinit var tasksViewModel: TasksViewModel

	@get:Rule
	var instantExecutorRule = InstantTaskExecutorRule()

	// Subject under test private lateinit var tasksViewModel: TasksViewModel
	@Before
	fun setupViewModel() {
		//We initialise the tasks to 3, with one active and two completed
		tasksRepository = FakeTestRepository()
		val task1 = Task("Title1", "Description1")
		val task2 = Task("Title2", "Description2")
		val task3 = Task("Title3", "Description3")
		tasksRepository.addTasks(task1, task2, task3)

		tasksViewModel = TasksViewModel(tasksRepository)
	}

	@Test
	fun addNewTask_setsNewTaskEvent() {

		//When adding a new task
		tasksViewModel.addNewTask()

		// Then the new task event is triggered
		val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

		assertNotNull(value.getContentIfNotHandled())
	}

	@Test
	fun setFilterAllTasks_taskAddViewVisible() {

		// When the filter type is ALL_TASKS
		tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

		// Then the "Add task" action is visible
		assertTrue(tasksViewModel.tasksAddViewVisible.getOrAwaitValue())
	}
}