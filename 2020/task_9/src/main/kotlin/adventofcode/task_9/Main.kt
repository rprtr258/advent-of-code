package adventofcode.task_9

import adventofcode.utils.*

val input = readInputFile(9)
    .map { it.toLong() }
val preamble = input.take(25)
val content = input.drop(25)

fun hasSumOf(prev25: List<Long>, s: Long): Boolean = prev25
    .flatMap { x -> prev25.map { y -> x + y } }
    .toSet()
    .any { it == s }

fun getFirstIncorrect(): Long {
    val prev25 = preamble.toMutableList()
    for (x in content) {
        if (!hasSumOf(prev25, x))
            return x
        prev25.removeAt(0)
        prev25.add(x)
    }
    throw error("Happy new year!")
}

fun getVulnerableSet(weakness: Long): List<Long> {
    var l = 0
    var r = -1
    var s: Long = 0
    while (s != weakness) {
        while (s < weakness) {
            r++
            s += input[r]
        }
        if (s == weakness)
            break
        s -= input[l]
        l++
    }
    return input.subList(l, r)
}

fun main() {
    val weakness = getFirstIncorrect()
    val vulnerableSet = getVulnerableSet(weakness)
    printAnswers(
        weakness,
        vulnerableSet.max()!! + vulnerableSet.min()!!
    )
}