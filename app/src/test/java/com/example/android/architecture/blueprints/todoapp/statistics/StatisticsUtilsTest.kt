package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class StatisticsUtilsTest {

	@Test
	fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
		// Create an active tasks (the false makes this active)
		val tasks  = listOf<Task>(
			Task("title", "desc", isCompleted = false)
		)

		// Call our function
		val result = getActiveAndCompletedStats(tasks)

		// Check the result
		assertThat(result.completedTasksPercent, `is`(0f))
		assertThat(result.activeTasksPercent, `is`(100f))

	}

	@Test
	fun getActiveAndCompletedStats_noActive_returnsHundredZero() {
		// Create an active tasks (the true makes this active)
		val tasks  = listOf<Task>(
			Task("title", "desc", isCompleted = true)
		)

		// Call our function
		val result = getActiveAndCompletedStats(tasks)

		// Check the result
		assertEquals(100f, result.completedTasksPercent)
		assertEquals(0f, result.activeTasksPercent)
	}

	@Test
	fun getActiveAndCompletedStats_3Active2Complete_returns4060() {
		// Create an active tasks (the true makes this active)
		val tasks  = listOf<Task>(
			Task("title", "desc", isCompleted = true),
			Task("title", "desc", isCompleted = true),
			Task("title", "desc", isCompleted = false),
			Task("title", "desc", isCompleted = false),
			Task("title", "desc", isCompleted = false),
		)

		// Call our function
		val result = getActiveAndCompletedStats(tasks)

		// Check the result
		assertEquals(40f, result.completedTasksPercent)
		assertEquals(60f, result.activeTasksPercent)
	}

	@Test
	fun getActiveAndCompletedStats_empty_returnsZeros() {
		// Create an active tasks (the false makes this active)
		val tasks  = emptyList<Task>()

		// Call our function
		val result = getActiveAndCompletedStats(tasks)

		// Check the result
		assertEquals(0f, result.completedTasksPercent)
		assertEquals(0f, result.activeTasksPercent)

	}

	@Test
	fun getActiveAndCompletedStats_error_returnsZeros() {
		// Create an active tasks (the false makes this active)
		val tasks  = emptyList<Task>()

		// Call our function
		val result = getActiveAndCompletedStats(tasks)

		// Check the result
		assertEquals(0f, result.completedTasksPercent)
		assertEquals(0f, result.activeTasksPercent)

	}

}