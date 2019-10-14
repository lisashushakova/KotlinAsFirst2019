@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.minDivisor
import kotlin.math.sqrt
import kotlin.math.pow

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var result = 0.0
    for (element in v) {
        result += sqr(element)
    }
    return sqrt(result)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var result = 0.0
    return if (list.isNotEmpty()) {
        for (element in list) {
            result += element
        }
        result / list.size
    }
    else result
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    if (list.isNotEmpty()) {
        for (i in 0 until list.size) {
            list[i] -= mean
        }
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var result = 0
    for (i in b.indices) {
        for (j in b.indices) {
            if (i == j) result += a[i] * b[j]
        }
    }
    return result
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var result = 0
    if (p.isNotEmpty()) {
        for ((index, element) in p.withIndex()) {
            result += element * (x.toDouble().pow(index)).toInt()
        }
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var sum = 0
    if (list.isNotEmpty()) {
        for (i in 0 until list.size) {
            val element = list[i]
            sum += element
            list[i] = sum
        }
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var x = n
    val result = mutableListOf<Int>()
    while (x > 1) {
        result.add(minDivisor(x))
        x /= minDivisor(x)
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString("*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var x = n
    val result = mutableListOf<Int>()
    if (n == 0) result.add(0)
    else {
        while (x > 0) {
            result.add(x % base)
            x /= base
        }
    }
    return result.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    fun lat(n: Int): String = when (n) {
        10 -> "a"
        11 -> "b"
        12 -> "c"
        13 -> "d"
        14 -> "e"
        15 -> "f"
        16 -> "g"
        17 -> "h"
        18 -> "i"
        19 -> "j"
        20 -> "k"
        21 -> "l"
        22 -> "m"
        23 -> "n"
        24 -> "o"
        25 -> "p"
        26 -> "q"
        27 -> "r"
        28 -> "s"
        29 -> "t"
        30 -> "u"
        31 -> "v"
        32 -> "w"
        33 -> "x"
        34 -> "y"
        35 -> "z"
        else -> "$n"
    }
    var x = n
    var result = String()
    while (x > 0) {
        result += lat(x % base)
        x /= base
    }
    return if (n > 0) result.reversed()
    else "0"
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = polynom(digits.reversed(), base)

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    fun num(str: String): Int = when (str) {
        "a" -> 10
        "b" -> 11
        "c" -> 12
        "d" -> 13
        "e" -> 14
        "f" -> 15
        "g" -> 16
        "h" -> 17
        "i" -> 18
        "j" -> 19
        "k" -> 20
        "l" -> 21
        "m" -> 22
        "n" -> 23
        "o" -> 24
        "p" -> 25
        "q" -> 26
        "r" -> 27
        "s" -> 28
        "t" -> 29
        "u" -> 30
        "v" -> 31
        "w" -> 32
        "x" -> 33
        "y" -> 34
        "z" -> 35
        else -> str.toInt()
    }
    var result = 0
    for ((index, element) in str.reversed().withIndex()) {
        result += num(element.toString()) * (base.toDouble().pow(index)).toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var x = n
    val result = mutableListOf<String>()
    var i = 0
    val arab = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val roman = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    while (x > 0) {
        if (x >= arab[i]) {
            result.add(roman[i])
            x -= arab[i]
        }
        else {
            i += 1
        }
    }
    return result.joinToString("")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val result = mutableListOf<String>()
    if (n / 1000 != 0) {
        when (n / 1000 / 100) {
            1 -> result.add("сто")
            2 -> result.add("двести")
            3 -> result.add("триста")
            4 -> result.add("четыреста")
            5 -> result.add("пятьсот")
            6 -> result.add("шестьсот")
            7 -> result.add("семьсот")
            8 -> result.add("восемьсот")
            9 -> result.add("девятьсот")
        }
        if (n / 1000 % 100 in 11..19) {
            when (n / 1000 % 100) {
                11 -> result.add("одиннадцать тысяч")
                12 -> result.add("двенадцать тысяч")
                13 -> result.add("тринадцать тысяч")
                14 -> result.add("четырнадцать тысяч")
                15 -> result.add("пятнадцать тысяч")
                16 -> result.add("шестнадцать тысяч")
                17 -> result.add("семнадцать тысяч")
                18 -> result.add("восемнадцать тысяч")
                19 -> result.add("девятнадцать тысяч")
            }
        }
        else {
            when (n / 1000 / 10 % 10) {
                1 -> result.add("десять")
                2 -> result.add("двадцать")
                3 -> result.add("тридцать")
                4 -> result.add("сорок")
                5 -> result.add("пятьдесят")
                6 -> result.add("шестьдесят")
                7 -> result.add("семьдесят")
                8 -> result.add("восемьдесят")
                9 -> result.add("девяносто")
            }
            when (n / 1000 % 10) {
                0 -> result.add("тысяч")
                1 -> result.add("одна тысяча")
                2 -> result.add("две тысячи")
                3 -> result.add("три тысячи")
                4 -> result.add("четыре тысячи")
                5 -> result.add("пять тысяч")
                6 -> result.add("шесть тысяч")
                7 -> result.add("семь тысяч")
                8 -> result.add("восемь тысяч")
                9 -> result.add("девять тысяч")
            }
        }
    }
    if (n % 1000 != 0) {
        when (n % 1000 / 100) {
            1 -> result.add("сто")
            2 -> result.add("двести")
            3 -> result.add("триста")
            4 -> result.add("четыреста")
            5 -> result.add("пятьсот")
            6 -> result.add("шестьсот")
            7 -> result.add("семьсот")
            8 -> result.add("восемьсот")
            9 -> result.add("девятьсот")
        }
        if (n % 1000 % 100 in 11..19) {
            when (n % 1000 % 100) {
                11 -> result.add("одиннадцать")
                12 -> result.add("двенадцать")
                13 -> result.add("тринадцать")
                14 -> result.add("четырнадцать")
                15 -> result.add("пятнадцать")
                16 -> result.add("шестнадцать")
                17 -> result.add("семнадцать")
                18 -> result.add("восемнадцать")
                19 -> result.add("девятнадцать")
            }
        }
        else {
            when (n % 1000 / 10 % 10) {
                1 -> result.add("десять")
                2 -> result.add("двадцать")
                3 -> result.add("тридцать")
                4 -> result.add("сорок")
                5 -> result.add("пятьдесят")
                6 -> result.add("шестьдесят")
                7 -> result.add("семьдесят")
                8 -> result.add("восемьдесят")
                9 -> result.add("девяносто")
            }
            when (n % 1000 % 10) {
                1 -> result.add("один")
                2 -> result.add("два")
                3 -> result.add("три")
                4 -> result.add("четыре")
                5 -> result.add("пять")
                6 -> result.add("шесть")
                7 -> result.add("семь")
                8 -> result.add("восемь")
                9 -> result.add("девять")
            }
        }
    }
    return result.joinToString(" ")
}