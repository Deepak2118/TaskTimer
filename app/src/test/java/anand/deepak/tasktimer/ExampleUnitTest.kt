package anand.deepak.tasktimer

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test add functionality`() {
        val taskList = ArrayList<Task>()
        val task = Task("ABC", "", 1)
        val result = Util().AddTask(taskList, task)
        Assert.assertTrue(result)
    }
}