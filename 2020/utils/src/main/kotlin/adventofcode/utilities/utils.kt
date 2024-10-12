package adventofcode.utils

import java.io.File

fun <T> List<T>.split(delimiter: T): List<List<T>> {
    val res = mutableListOf<List<T>>()
    var group = mutableListOf<T>()
    for (x in this)
        when {
            x != delimiter -> {
                group.add(x)
            }
            group.any() -> {
                res.add(group.toMutableList())
                group = mutableListOf()
            }
        }
    if (group.any())
        res.add(group.toMutableList())
    return res
}

fun printAnswers(first: Any?, second: Any?) {
    println("First: $first")
    println("Second: $second")
}

fun readInputFile(task: Int): List<String> {
    return File("src/main/resources/input").readLines()
}
