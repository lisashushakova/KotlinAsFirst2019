package lesson7.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class Tests {

    @Test
    fun editList() {
        editList("input/edit", listOf("2 Следующий акт", "5 Вторая глава"), "temp.txt")
        plz()

        editList("input/edit", listOf("-3", "*6 Заключение"), "temp.txt")
        plz2()
    }

    private fun plz() {
        val result = File("temp.txt").readText()
        val expected =
            """
                1 Первое действие
                2 Следующий акт
                3 Интерлюдия
                5 Вторая глава
                6 Послесловие

                """.trimIndent()
        assertEquals(expected, result)

        File("temp.txt").delete()
    }

    private fun plz2() {
        val result = File("temp.txt").readText()
        val expected =
            """
                1 Первое действие
                6 Заключение
                
                """.trimIndent()
        assertEquals(expected, result)

        File("temp.txt").delete()
    }

    @Test
    fun theGreatProcessor() {
        assertEquals(3, lesson7.task1.theGreatProcessor("input/processor"))
        assertEquals(21, lesson7.task1.theGreatProcessor("input/processor1"))
        assertEquals(2, lesson7.task1.theGreatProcessor("input/processor 2"))
    }
}


