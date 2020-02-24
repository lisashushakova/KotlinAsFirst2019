@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.IllegalArgumentException
import java.util.*

fun editList(inputName: String, changes: List<String>, outputName: String) {
    val input = File(inputName).readLines()
    val output = File(outputName).bufferedWriter()
    val map = TreeMap<Int, String>()
    val mapStar = TreeMap<Int, String>()
    val mapChanges = TreeMap<Int, String>()
    for (line in input) {
        val number = line.substringBefore(" ").toInt()
        val str = line.substringAfter(" ")
        map += number to str
    }
    for (change in changes) {
        if ("*" in change || "-" in change) {
            if ("*" in change) {
                val numberChange = change.substringBefore(" ")
                val star = numberChange.replace("*", "").toInt()
                val strChange = change.substringAfter(" ")
                mapStar += star to strChange
            } else {
                val numberChange = change.toInt() * -1
                map.remove(numberChange)
            }
        } else {
            val numberChange = change.substringBefore(" ").toInt()
            val strChange = change.substringAfter(" ")
            map += numberChange to strChange
        }
    }
    val newMap = (map + mapChanges).toMutableMap()
    for ((key, value) in mapStar) newMap[key] = value
    for ((key, value) in newMap) output.write("$key $value\n")
    output.close()
}

fun theGreatProcessor(inputName: String): Int {
    var r0 = 0
    var r1 = 0
    var line = 0
    val lines = File(inputName).readLines()
    var command = ""
    var value = listOf<String>()
    var bool = false
    while (line != lines.size) {
        if (!(bool)) {
            command = lines[line].split(" ")[0]
            value = lines[line].split(" ").subList(1, lines[line].split(" ").size)
            bool = false
        }
        when (command) {
            "GOTO" -> line = value[0].toInt() - 1
            "ADD" -> r0 += value[0].toInt()
            "SUB" -> r0 -= value[0].toInt()
            "MUL" -> r0 *= value[0].toInt()
            "MOV" -> {
                if (value[0] == "R1") r1 = r0
                else if (value[0] == "R0") r0 = r1
                else throw IllegalArgumentException()
            }
            "IF" -> {
                when {
                    value[0] == "R0" -> {
                        val booleanResult = when (value[1]) {
                            ">" -> r0 > value[2].toInt()
                            "<" -> r0 < value[2].toInt()
                            "==" -> r0 == value[2].toInt()
                            else -> throw IllegalArgumentException()
                        }
                        command = value[3]
                        value = if (booleanResult) listOf(value[4]) else listOf(value[6])
                    }
                    value[0] == "R1" -> {
                        val boolResult = when (value[1]) {
                            ">" -> r1 > value[2].toInt()
                            "<" -> r1 < value[2].toInt()
                            "==" -> r1 == value[2].toInt()
                            else -> throw IllegalArgumentException()
                        }
                        command = value[3]
                        value = if (boolResult) listOf(value[4]) else listOf(value[6])
                    }
                    else -> throw IllegalArgumentException()
                }
            }
            else -> throw IllegalStateException()
        }
        line++
    }
    return r0
}