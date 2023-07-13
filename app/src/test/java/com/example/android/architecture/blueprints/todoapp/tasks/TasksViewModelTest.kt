package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class TasksViewModelTest {

	// Subject under test
	private lateinit var tasksViewModel: TasksViewModel

	@get:Rule
	var instantExecutorRule = InstantTaskExecutorRule()

	// Subject under test private lateinit var tasksViewModel: TasksViewModel
	@Before
	fun setupViewModel() {
		tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
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