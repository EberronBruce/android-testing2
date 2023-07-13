package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.IsNull.nullValue

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class TasksViewModelTest {

	@get:Rule
	var instantExecutorRule = InstantTaskExecutorRule()

	@Test
	fun addNewTask_setsNewTaskEvent() {
		//Given a fresh ViewModel
		val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

		//When adding a new task
		taskViewModel.addNewTask()

		// Then the new task event is triggered
		val value = taskViewModel.newTaskEvent.getOrAwaitValue()

		assertThat(value.getContentIfNotHandled(), `is`(notNullValue()))
	}
}