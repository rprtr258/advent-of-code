package adventofcode.task_1

import adventofcode.utils.*

const val YEAR = 2020
val ints: List<Int> = readInputFile(1)
    .map { it.toInt() }

fun rec(sum: Int, numsLeft: Int): Int? {
    for (x in ints) {
        if (numsLeft == 1) {
            if (x == sum)
                return x
        } else if (sum >= x) {
            val y = rec(sum - x, numsLeft - 1)
            if (y != null)
                return x * y
        }
    }
    return null
}

fun main() = printAnswers(rec(YEAR, 2), rec(YEAR, 3))