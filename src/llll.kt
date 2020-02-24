import java.io.File
import kotlin.math.min


fun main() {
//    println(birthday("Андрей 23 февраля, Ольга 8, Мария 10 февраля, Михаил 31 сентября"))
//    println(transfer("input/plane", "Peterburg", ""))
//    println(timeToInt("13:25"))
//    println(stockProducts("input/products", "008243 10"))
//    println(findRoute("input/bus", "Парк отдыха", "Аэропорт"))
//    println(maxRainfall("input/rainfall", "апрель 9...15"))
//    println(intersectionOfMany("input/arrays", "A & C & X & !Y"))
//    println(rangeAverage("input/rangeAverage", "B3:D3"))
//    println(apartments("input/apartments", "коридор 4"))
//    println(footballMatchStatistics("input/football"))
    println(maxRainfall("input/rainfall", "апрель 17...май 30"))
}

/**
 * № 1
 * Имеется строка с информацией о днях рождения ряда людей в следующем формате:
 * "Андрей 23 февраля, Ольга 8 марта, Мария 30 февраля, Михаил 31 сентября"
 * Имена людей отделены от их дней рождения одним или несколькими пробелами, а данные разных людей разделяются запятой и пробелом.
 * День рождения начинается с номера дня, за которым следует название месяца.
 * Следует вернуть список людей, у которых день рождения является некорректным (т.е. такой день не существует),
 * вместе с их днями рождения.
 * Считать, что 29 февраля -- корректная дата.
 * Имя функции и тип результата функции предложить самостоятельно;
 * в задании указан тип Collection<Any>,
 * то есть коллекция объектов произвольного типа, можно (и нужно) изменить как вид коллекции,
 * так и тип её элементов.
 * Кроме функции, следует написать тесты, подтверждающие её работоспособность.
 *
 */

fun birthday(text: String): List<String> {
    val namesList = mutableListOf<String>()
    val daysAndMonth = mapOf(
        "января" to 31,
        "февраля" to 29,
        "марта" to 31,
        "апреля" to 30,
        "мая" to 31,
        "июня" to 30,
        "июля" to 31,
        "августа" to 31,
        "сентября" to 30,
        "октября" to 31,
        "ноября" to 30,
        "декабря" to 31
    )
    val listOfMen = text.split(", ")
    for (element in listOfMen) {
        val list = element.split(" ")
        if (list.size == 3) {
            val day = list[1].toInt()
            val month = list[2]
            if (month !in daysAndMonth || day !in 1..daysAndMonth[month]!!) {
                namesList.add(element)
            }
        } else {
            namesList.add(element)
        }
    }
    return namesList
}


/**
 * № 2
 * В файле с именем inputName заданы номера телефонов различных людей в следующем формате:
 *
 * Михаил - Мобильный +79211234567, Домашний +78217654321
 * Мария - Служебный +79069876543
 * Антон - Мобильный +79116543456, Мобильный +79318239475
 * Иван - Домащний +74950257329
 *
 * Строчка начинается с имени человека (без пробелов), после черточки следуют номера телефонов через запятую,
 * причем перед каждым номером указывается его тип (без пробелов).
 * Типы номеров могут совпадать, имена людей - нет.
 * Параметр query содержит запрос, начинающийся с имени человека, за которым следует тип номера, например "Михаил Домашний".
 * Функция должна найти этого человека в списке и вернуть номер телефона соответствующего типа.
 *
 * "Удовлетворительно" -- достаточно найти любой из номеров телефона указаного типа для указаного человека.
 *
 * "Хорошо" -- необходимо вернуть все номера телефонов указаного типа для указаного человека. Также, должны работать
 * запросы вида "Мария *", означающие "все номера Марии".
 *
 * "Отлично" -- также, должны работать запросы вида "Антон 911", означающие "все номера Антона, содержащие 911".
 * В таких запросах, после имени и пробела следует набор цифр.
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 *
 */

fun findNumberPhone(inputName: String, query: String): Set<String> {
    if (!query.matches(Regex("""([а-яА-Я]+\s+[а-яА-Я+0-9]*\**)*"""))) throw IllegalArgumentException("Input Error")

    val map = mutableMapOf<String, MutableSet<String>>()
    var vest = ""
    val (queriedName, queriedPhoneType) = query.split(" ")
    for (line in File(inputName).readLines()) {
        val nameToNum = line.split(" -")
        if (nameToNum[0] == queriedName) {
            vest = nameToNum[1]
        }
    }

    if (vest.isNotEmpty()) {
        val numbers = vest.split(",")
        for (element in numbers) {
            val typeAndNum = element.trim().split(" ")
            val phoneType = typeAndNum[0]
            val numOfPhone = typeAndNum[1]
            if (phoneType in map) {
                map[phoneType]!!.add(numOfPhone)
            } else {
                map[phoneType] = mutableSetOf(numOfPhone)
            }
        }
    }
    if (queriedPhoneType == "*") {
        val result = mutableSetOf<String>()
        for (set in map.values) {
            result.addAll(set)
        }
        return result
    }
    if (queriedPhoneType in map) {
        return map[queriedPhoneType]?.toSet().orEmpty()
    }

    return emptySet()
}

/**
 * № 3
 * В файле с именем inputName заданы результаты игр футбольного чемпионата в виде таблицы в следующем формате:
 * Арсенал   0 4 1 1
 * Бавария   1 0 2 3
 * Интер     1 2 0 0
 * Барселона 3 8 0 0
 *
 * Элемент таблицы чисел из i-й строки и j-го столбца содержит число голов, забитое командой i в ворота команды j.
 * Например (см. выделенные элементы) команда 2 (Бавария) и команда 4(Барселона) сыграли со счетом 3:8.
 * Диагональные элементы(i=j) всегда равны 0. Названия команды не могут содержать пробелы.
 * За победу в матче (большее количество мячнй) команде даётся 3 очка, за ничью (одинаковое количество мячей) - 1 очко.
 * Необходимо посчитать статистику каждой команды по итогам чемпионата и вернуть её как результат функции.
 *
 * "Удовлетворительно" -- достаточно расчитать суммарное количество мячей, забитых и пропущенных каждой командой.
 *
 * "Хорошо" -- для каждой команды расчитать количество очков, побед, ничьих, поражений, суммарное количество забитых и
 * пропущенных мячей.
 *
 * "Отлично" -- также необходимо определить занятые командами места по количеству набранными ими очков, а при равенстве
 * очков - по разности забитых и пропущенных мячей.
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */

fun footballMatchStatistics(inputName: String): List<String> {
    val result = mutableListOf<String>()
    val sumByColumn = mutableListOf<Int>()

    File(inputName).forEachLine {
        val listOfLine = it.split("\\s+".toRegex())
        if (listOfLine.size > 1) {
            var goal = 0
            val team = listOfLine[0]
            for (i in 1 until listOfLine.size) {
                val score = listOfLine[i].toInt()
                goal += score
                val j = i - 1
                if (j >= sumByColumn.size) {
                    sumByColumn.add(0)
                }
                sumByColumn[j] += score
            }
            result.add("$team: забитые мячи - $goal;")
        }
    }

    for (i in result.indices) {
        val missedBall = sumByColumn[i]
        result[i] += " пропущенные мячи - $missedBall;"
    }

    return result
}


/**
 * № 4
 * В файле с именем inputName  содержится расписание прибытия самолётов и отправления самолётов аэропорта Москвы в следующем формате:
 * LD210 Peterburg > 13:45
 * LV2222 Frankfurt > 15:10
 * FV1234 Peterburg > 16:22
 * AF345 Paris > 17:20
 * LD220 Peterburg > 17:52
 * BA457 London > 18:30
 * LV2223 Frankfurt < 18:10
 * FV1235 Peterburg < 19:42
 * JA4590 Tokyo < 18:42
 * AF345 Irkutsk < 22:20
 *
 * В каждой строчке файла имеется код рейса, название города вылета (без пробелов) и > для прибывающих рейсов или
 * название города назначения и < для отправляющихся рейсов, время прибытия или отправления.
 * Функция для названий городов src(город вылета) и dst(город прилёта) определяет возможность перелёта с пересадкой в
 * аэропорту Москвы.
 *
 * "Удовлетворительно" -- определить только возможность перелёта (да, усли есть прибывающий рейс из src и отправляющийся в dst).
 *
 * "Хорошо" -- учесть также, что прибывший рейс должен прилетать минимум на полчаса раньше отправления второго рейса.
 * В результате функции указать коды рейсов прибытия и отправления.
 *
 * "Отлично" -- вернуть список из всех подходящих пар рейсов прибытия и отправления, упорядочный по возрастанию интервала
 * между ними.
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */
fun transfer(inputName: String, src: String, dst: String): String {
    if (!src.matches(Regex("""([a-zA-Z])+"""))) return "NO"
    if (!dst.matches(Regex("""([a-zA-Z])+"""))) return "NO"

    val flownIn = mutableMapOf<String, Pair<String, String>>()
    val flownAway = mutableMapOf<String, Pair<String, String>>()

    for (line in File(inputName).readLines()) {
        if (">" in line) {
            val planeAndTime =
                line.split(">")               //список [рейс-самолёт, время] для внесения в мапу прилетевших самолётов
            val planeAndFlight =
                planeAndTime[0].split(" ")    //список [рейс, самолёт] для внесения в мапу прилетевших самолётов
            flownIn[planeAndFlight[0]] = Pair(planeAndFlight[1], planeAndTime[1])
        } else if ("<" in line) {
            val planeAndTime =
                line.split("<")               //список [рейс-самолёт, время] для внесения в мапу улетающих самолётов
            val planeAndFlight =
                planeAndTime[0].split(" ")    //список [рейс, самолёт] для внесения в мапу улетающих самолётов
            flownAway[planeAndFlight[0]] = Pair(planeAndFlight[1], planeAndTime[1])
        } else continue
    }

    var departureTime = 0
    var arrivalTime = 0
    for ((key, element) in flownIn) {
        for ((key2, unit) in flownAway) {
            if (src in element.first && dst in unit.first) {
                departureTime = timeToInt(unit.second.trim())
                arrivalTime = timeToInt(element.second.trim())
            }
            if (departureTime - arrivalTime > 30) {
                return "$key > $key2"
            }
        }
    }

    return "NO"
}

//возвращает время в минутах от начала текущих суток
fun timeToInt(time: String): Int {
    if (!time.matches(Regex("""(\d+[:]+\d+)"""))) throw IllegalArgumentException("Некорректное время")
    val hoursAndMinuts = time.split(":")
    return hoursAndMinuts[0].toInt() * 60 + hoursAndMinuts[1].toInt()
}

/**
 * № 5
 * В файле с именем inputName  заданы описания квартир, предлагающихся для продажи, в следующем формате:
 * Пионерская 9-17: комната 18, комната 14, кухня 7, коридор 4
 * Школьная 12-14: комната 19, кухня 8, коридор 3
 * Садовая 19-1-55: комната 12, комната 19, кухня 9, коридор 5
 * Железнодорожная 3-6: комната 21, кухня 6, коридор 4
 *
 * Строчка начинается с адреса квартиры, после двоеточия перечисляются помещения квартиры через запятую, с указанием их площади.
 * Параметр query содержит запрос, начинающийся с названия помещения, за которым следует его минимальная площадь,
 * например, "кухня 8". Через запятую могут следовать другие ограничения, например "кухня 8, коридор 4".
 * Функция должна найти все квартиры в списке, удовлетворяющие запросу(площадь кухни больше или равна 8, площадь
 * коридора больше или равна 4).
 *
 * "Удовлетворительно" -- в запросе может присутствовать только одно помещение, например "кухня 8".
 *
 * "Хорошо" -- в запросе может присутствовать несколько помещений, например "кухня 8, комната 15"
 *
 * "Отлично" -- в запросе может присутствовать два и более однотипных помещения, например, "комната 12, комната 19" --
 * двухкомнатная квартира, одна комната не менее 19, другая не менее 12.
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */

fun apartments(inputName: String, query: String): Any {
    var queryResult = mutableListOf<String>()
    val result = mutableListOf<String>()
    val queryParameters = query.split(", ")
    File(inputName).forEachLine {
        val rooms = it.split(": ")
        if (rooms.size > 1) {
            val placement = rooms[1].split(", ")
            for (element in placement) {
                val (place, squer) = element.split(" ")
                val (queryRoom, queryArea) = queryParameters[0].split(" ")
                if (queryRoom == place && squer.toInt() >= queryArea.toInt()) {
                    result.add(it)
                }
            }
        }
    }
    if (queryParameters.size > 1) {
        for (i in 1 until queryParameters.size) {
            val (room, area) = queryParameters[i].split(" ")
            val filteredResult = mutableListOf<String>()
            for (address in result) {
                val rooms = address.split(": ")
                val placement = rooms[1].split(", ")
                for (element in placement) {
                    val (place, squer) = element.split(" ")
                    if (room == place && squer.toInt() >= area.toInt()) {
                        filteredResult.add(address)
                        queryResult = filteredResult
                    }
                }
            }
        }
    }

    return if (queryParameters.size > 1) queryResult else result
}


/**
 * № 6
 * В файле с именем inputName  задана таблица действительных чисел. Столбцы таблицы разделены запятыми и пробелами.
 * Каждая строка содержит не более 26 значений. Например:
 * 1.5, 2.67, 3.0, 1.4
 * 5.2, 7.1, -4.8, 0.0
 * 1.4, 6.0, 2.5, -1.9
 *
 * В строковом параметре range задан диапазон двух ячеек этой таблицы, разделенных знаком ":", например "A2:C4".
 * Ячейки закодированы так: столбец задается заглавной буквой латинского алфавита(первый столбец это буква А),
 * а строка - целым числом(первая строка это число 1).
 * Необходимо посчитать среднее арифметическое значение во всех ячейках заданого диапазона заданой таблицы.
 * Диапазон задаёт углы прямоугольника -- например "A2:C3" соответствует ячейкам А2, А3, В2, В3, С2, С3.
 *
 * "Удовлетворительно" -- все строки содержат одинаковое количество чисел, заданный диапазон относится к одной строке,
 * первая ячейка в нём обязательно находится слева, например,"B3:D3" (содержит В3, С3, D3).
 *
 * "Хорошо" -- диапазоны могут содержать ячейки из разных строк с произвольным положением углов, например "B1:A2"
 * соответствует ячейкам А1, А2, В1, В2.
 *
 * "Отлично" -- строки могут содержать разное количество чисел. Кроме того, диапазон может включать ячейки за пределами
 * входной таблицы, это не является ошибкой, ячейки за пределами входной таблицы просто не учитываются. Например:
 * диапазон "E1:B2" содержит В1, С1, D1, B2, C2, D2.
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */

fun rangeAverage(inputName: String, range: String): Double {
    val table = mutableListOf<List<Double>>()
    File(inputName).forEachLine {
        val line = it.split(", ").map { num -> num.toDouble() }
        table.add(line)
    }
    val (startCell, endCell) = range.split(":")
    var (startLine, startColumn) = cellToIndexes(startCell)
    var (endLine, endColumn) = cellToIndexes(endCell)
    if (startLine > endLine) {
        val changeLine = startLine
        startLine = endLine
        endLine = changeLine
    }

    val maxLine = min(endLine, table[0].size - 1)
    val maxColumn = min(endColumn, table.size - 1)

    var sum: Double = 0.0
    var count = 0
    for (i in startLine..maxLine) {
        for (j in startColumn..maxColumn) {
            sum += table[j][i]
            count++
        }
    }
    return sum / count
}

fun cellToIndexes(cell: String): Pair<Int, Int> {
    val alphabetToInt = mapOf(
        'A' to 0, 'B' to 1, 'C' to 2, 'D' to 3, 'E' to 4, 'F' to 5, 'G' to 6, 'H' to 7, 'I' to 8, 'J' to 9, 'K' to 10,
        'L' to 11, 'M' to 11, 'N' to 13, 'O' to 14, 'P' to 15, 'Q' to 16, 'R' to 17, 'S' to 18, 'T' to 19, 'U' to 20,
        'V' to 21, 'W' to 22, 'X' to 23, 'Y' to 24, 'Z' to 25
    )
    val line = alphabetToInt[cell[0]]!!
    val column = cell.substring(1 until cell.length).toInt() - 1
    return Pair(line, column)
}


/**
 * № 7
 * В файле с именем inputName заданы ежедневные сведения о количестве выпавших осадков (в мм) в различные месяцы года,
 * всего не больше чем 31 ( в каждой строке и не более 12 строк во всём файле, например:
 * март 0 1 0 3 41 2 0 0 13 16 20 8 0 4 8 1 0 0 0 7 12 0 4 9
 * апрель 0 0 0 17 0 0 11 48 42 0 0 1 7 15 16 0 0 0 0 0 8 2 17 0
 * май 10 15 48 21 0 0 17 22 30 0 0 13 0 0 2 5 7 0 0 0 1 10 3
 *
 * Каждая строка начинается с названия месяца, за которым следует последовательность целых чисел - уровень осадков в мм
 * в различные дни этого месяца, начиная с 1-го. Порядок месяцев в файле должен соответствовать реальному.
 * В строковом параметре days задан интервал дат либо в формате "апрель 9...15" (дни в одном месяце), либо в формате
 * "Март 22...Май 8" (дни в различных месяцах).
 * Необходимо расчитать максимальный уровень осадков за один день в заданом интервале дат. Например, для "апрель 9...15"
 * это 42, а для "Март 22...Май 8" это 48. Отсутствующие дни игнорировать.
 *
 * "Удовлетворительно" -- используется только первый формат для параметра days - все дни в одном месяце.
 *
 * "Хорошо" -- может использоваться как первый, так и второй формат параметра days, то есть, интервал может содержать
 * дни в разных месяцах.
 *
 * "Отлично" -- результат функции должен содержать не только максимальный уровень осадков, но и список дней, в которых
 * он был достигнут (42, 9 апреля или 48, 8 апреля, 3 мая для примеров выше).
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */
//не до конца решена. Посмотреть позже

fun maxRainfall(inputName: String, days: String): List<String> {
    val result = mutableListOf<String>()
    val monthRainfalls = mutableMapOf<String, List<Int>>()
    val months = linkedSetOf<String>()
    File(inputName).forEachLine {
        val line = it.split(" ")
        val integers = mutableListOf<Int>()
        for (i in 1 until line.size) {
            val inter = line[i].toInt()
            integers.add(inter)
        }
        monthRainfalls[line[0].trim()] = integers
    }

    val dayRangeWithOneMonth = """([а-яА-Я]+) (\d+)\.\.\.(\d+)""".toRegex()
    val dayRangeWithTwoMonths = """([а-яА-Я]+) (\d+)\.\.\.([а-яА-Я]+) (\d+)""".toRegex()
    val matches = dayRangeWithOneMonth.find(days)
    val anotherMatches = dayRangeWithTwoMonths.find(days)
    var maximum = 0

    if (matches != null) {
        val (month, startDayString, endDayString) = matches.destructured
        val startDay = startDayString.toInt() - 1
        val endDay = endDayString.toInt() - 1
        if (month.toLowerCase() in monthRainfalls) {
            val rainfallQuantity = monthRainfalls[month.toLowerCase()]
                ?: throw java.lang.IllegalArgumentException("Incorrect month")
            for (dayIndex in startDay..endDay) {
                if (rainfallQuantity[dayIndex] > maximum) {
                    maximum = rainfallQuantity[dayIndex]
                    val day = dayIndex + 1
                    result.clear()
                    result.add("$maximum мм,  $month - $day")
                } else if (rainfallQuantity[dayIndex] == maximum) {
                    val day = dayIndex + 1
                    result.add("$day")
                }
            }
        }
    } else if (anotherMatches != null) {
        val (firstMonth, firstDay, secondMonth, endDay) = anotherMatches.destructured

    } else {
        throw java.lang.IllegalArgumentException("НЕВЕРНЫЙ ЗАПРОС")
    }

    return result
}

/**
 * № 8
 * В файле с именем inputName задано несколько множеств целых чисел в следующем формате:
 * A = -1, 3, 7, 5, 28, -9
 * C = 40, 7, -5, 28, 14, 15, 19, -1
 * X = 24, 9, 17, 9, 7, -1, 48, 28
 * Y = 40, -5, -1, 15, 19
 *
 * Каждое множество описывается на отдельной строчке. Описание начинается с прописной буквы и знака равенства, за которым
 * следует перечисление чисел, входящих в множество, через запятую. В описании могут присутствовать одинаковые числа, в
 * этом случае их следует устранить из множества.
 * Параметр expr содержит операцию над множествами, например, "X & Y", "A & !C" или "A & C & X & !Y".
 * Функция должна вернуть множество, являющееся результатом данной операции. Здесь & обозначает пересечение множеств --
 * его результатом являются числа, присутствующие в обоих множествах. ! обозначает дополнение множества -- туда входят
 * все целые числа, отсутствующие в данном множестве.
 *
 * "Удовлетворительно" -- выражение expr может описывать только пересечение 2 множеств из файла, например, "X & Y", "A & C".
 *
 * "Хорошо" -- также, выражение expr может описывать пересечение одного множества с дополнением другого, например, "A & !C".
 * Первое множество в выражении всегда присутствует без дополнения.
 *
 * "Отлично" -- также, выражение expr может описывать пересечение трёх и более множеств, некоторые из которых (кроме первого)
 * могут быть дополнениями множеств из файла, например, "A & !Y & C & !X".
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */

fun intersectionOfMany(inputName: String, expr: String): Set<Int> {
    val sets = mutableMapOf<String, Set<Int>>()
    File(inputName).forEachLine {
        val line = it.split("=")
        val listIntegers = line[1].split(",")
        val integers = mutableSetOf<Int>()
        for (el in listIntegers) {
            val inter = el.trim().toInt()
            integers.add(inter)
        }
        sets[line[0].trim()] = integers
    }
    val queriedSets = expr.split('&').map { it.trim() }
    var result = sets[queriedSets[0]]!!
    for (setName in queriedSets.slice(1 until queriedSets.size)) {
        result = if (setName.startsWith('!')) {
            result.subtract(sets[setName.substring(1 until setName.length)]!!)
        } else {
            result.intersect(sets[setName]!!)
        }
    }
    return result
}


/**
 * № 9
 * В файле с именем inputName заданы описания продуктов, имеющихся на складе, в следующем формате:
 * 001532: яблоки, 49 р, 54 кг
 * 004021: молоко, 74 р, 72 уп
 * 008243: хлеб, 59 р, 30 шт
 * 002762: свинина, 299 р, 0 кг
 * 009724: яйца, 69 р, 20 уп
 *
 * Строчка начинается с шестизначного числового кода продукта, после двоеточия через запятую перечисляются названия
 * продукта, цена одной единицы продукта, количество единиц продукта на складе.
 * Параметр query содержит запрос, начинающийся с кода продукта, с указанием через пробел минимально необходимого количества
 * продукта. Функция должна найти продукт с этим кодом в файле, вернуть его название, признак достаточности количества и
 * общую стоимость таких продуктов на складе.
 *
 * "Удовлетворительно" -- код продукта в запросе указывается явно, например, "009724 15", ожидаемый ответ -- яйца, достаточно,
 * общая стоимость 1380 р.
 *
 * "Хорошо" -- в запросе код продукта может быть заменен символом * (любой код), например, запросу "* 15" удовлетворяют
 * все продукты, но только яблоки и молоко присутствуют в достаточном количестве.
 *
 * "Отлично" -- в коде продукта могут присутствовать знаки вопроса, обозначающие произавольную цифру на данном месте,
 * например, запросу "00???2 10" соответствуют яблоки и свинина, причём количество свинины не является достаточеым (< 10кг).
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */

fun stockProducts(inputName: String, query: String): List<String> {
//    val input = "001532: яблоки, 49 р, 54 кг"
//    val regexp = """(\d+)[^\d]+(\d+).+?(\d+).+""".toRegex()
//    val matches = regexp.find(input)
//    val (name, price, weight) = matches!!.destructured


    val keyAndQuatity = query.split(" ")
    val result = mutableListOf<String>()
    for (line in File(inputName).readLines()) {
        val smth = line.split(": ")
        if (smth.size > 1) {
            val productData = smth[1].split(", ")
            val productName = productData[0]
            val cost = productData[1].split(" ")
            val quantity = productData[2].split(" ")
            val sum = cost[0].toInt() * quantity[0].toInt()

            if (keyAndQuatity[0] in line && keyAndQuatity[1].toInt() < quantity[0].toInt()) {
                return listOf("$productName, достаточно, общая стоимость $sum р")
            } else if (keyAndQuatity[0] == "*" && keyAndQuatity[1].toInt() < quantity[0].toInt()) {
                result.add("$productName, достаточно, общая стоимость $sum р")
            }
        } else continue
    }
    return if (result.isNotEmpty()) result
    else listOf("ничего не найдено. уточните запрос и попробуйте еще раз.")
}

/**
 * № 10
 * В файле с именем inputName содержатся марщруты общественного транспорта в следущем формате:
 * троллейбус 15 ул. Железнодорожная > ул. Садовая > ул. Пионерская > Московское ш
 * автобус 17 ул. Железнодорожная > ул. Садовая>ул. Центральная > Парк отдыха
 * трамвай 3 Парк отдыха > ул. Дворцовая > Проспект Мира
 * автобус 4 ул. Железнодорожная > Путепровод > Аэропорт
 * троллейбус 4 Путкпровод > ул. Центральная > аллея Культуры > Московское ш
 *
 * В каждой строчке файла указан вид транспорта(автобус, троллейбус, трамвай), номер маршрута и список остановок,
 * разбитых знаком >. Считается, что каждый маршрут действует, и в прямом и в обратном направлении.
 *
 * "Удовлетворительно" -- построить прямой маршрут(без пересадок) от станции src до станции dst. Например: src =
 * "Парк отдыха", dst = "ул. Дворцовая", ответ =  "трамвай 3"(впрнуть любой возможный вариант).
 *
 * "Хорошо" -- построить маршрут между станциями src и dst, напрямую или с одной пересадкой. Например: src =
 * "Парк отдыха", dst = "Аэропорт", ответ =  "тавтобус 17, пересадка(ул. Железнодорожная), автобус 4"
 * (впрнуть любой возможный вариант).
 *
 * "Отлично" -- построить все маршруты между станциями src и dst, напрямую или с одной пересадкой. Упорядочить маршруты
 * по числу остановок. Например: src = "Аэропорт", dst = "Московское ш",
 * ответ =  "автобус 3, пересадка(Путепровод), троллейбус 4(всего 5 ост)", "автобус 3, пересадка(ул. Железнодорожная),
 * троллейбус 15(всего 6 ост)".
 *
 * При нарушении форматов входных данных следует выбрасывать исключение IllegalArgumentException, при невозмажности
 * прочитать файл выбрасывать исключение IOException.
 *
 * Предложить имя и тип результата функции. Кроме функции следует написать тесты, подтверждающии её работоспособность.
 */
fun findRoute(inputName: String, src: String, dst: String): List<List<String>> {
    if (!src.matches(Regex("""([а-я\s*\.*А-Я]+)"""))) {
        throw IllegalArgumentException()
    }
    if (!dst.matches(Regex("""([а-я\s*\.*А-Я]+)"""))) {
        throw IllegalArgumentException()
    }
    val transportWithRoute = mutableMapOf<String, List<String>>()
    val result = mutableListOf<List<String>>()
    val routePattern = """([а-яА-Я]+\s*\d+\s*) ([а-яА-Я\.\s*>\s*]+)""".toRegex()

    File(inputName).forEachLine {
        val matches = routePattern.find(it)
        if (matches != null) {
            val (transport, route) = matches.destructured
            val routeList = route.split(">").map { it.trim() }
            transportWithRoute[transport] = routeList
        }
    }

    for ((transport, route) in transportWithRoute) {
        if (src in route && dst in route) {
            result.add(listOf(transport))
            return result
        }
    }

    val startingTransportRoutes = filterRoutes(transportWithRoute, src)
    val endingTransportRoutes = filterRoutes(transportWithRoute, dst)

    for ((startingTransport, startingRoute) in startingTransportRoutes) {
        for ((endingTransport, endingRoute) in endingTransportRoutes) {
            if (startingTransport == endingTransport) {
                continue
            }

            val changingStops = startingRoute.intersect(endingRoute)
            if (changingStops.isNotEmpty()) {
                result.add(listOf(startingTransport, changingStops.elementAt(0), endingTransport))
            }
        }
    }

    return result
}
// второй вариант решения 10 задачи

fun filterRoutes(transportWithRoute: Map<String, List<String>>, stop: String): Map<String, List<String>> {
    val filtered = mutableMapOf<String, List<String>>()

    for ((transport, route) in transportWithRoute) {
        if (stop in route) {
            filtered[transport] = route
        }
    }
    return filtered
}

fun rout(inputName: String, src: String, dst: String): List<String> {
    if (!src.matches(Regex("""([а-я\s*\.*А-Я]+)"""))) {
        throw IllegalArgumentException()
    }
    if (!dst.matches(Regex("""([а-я\s*\.*А-Я]+)"""))) {
        throw IllegalArgumentException()
    }

    val transportWithTrace = mutableMapOf<String, List<String>>()
    val result = mutableListOf<String>()
    val busWithRoute = """([а-яА-Я]+\s*\d+\s*) ([а-яА-Я\.\s*>\s*]+)""".toRegex()
    File(inputName).forEachLine {
        val matches = busWithRoute.find(it)
        if (matches != null) {
            val (bus, trace) = matches.destructured
            val traceList = trace.split(">")
            val routList = mutableListOf<String>()
            for (el in traceList) {
                routList.add(el.trim())
            }
            transportWithTrace[bus] = routList
        }
    }
    val wayStart = mutableListOf<String>()
    val endWay = mutableListOf<String>()
    for ((key, value) in transportWithTrace) {
        if (src in value) {
            if (dst in value) {
                result.add(key)
                return result
            } else {
                wayStart.add(key)
            }
        }
        if (dst in value) {
            endWay.add(key)
        }
    }
    for (transport in wayStart) {
        for (machine in endWay) {
            val transfer = transportWithTrace[transport]?.intersect(transportWithTrace[machine]!!)
            if (transfer != null) {
                if (transfer.isNotEmpty()) {
                    result.add("$transport, пересадка ($transfer), $machine")
                }
            }
        }
    }
    return result
}
