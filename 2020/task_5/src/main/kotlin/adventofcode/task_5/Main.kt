package adventofcode.task_5

import adventofcode.utils.*

val placeIDs: Sequence<Int> = readInputFile(5)
    .asSequence()
    .map { it.replace(Regex("[FL]"), "0")}
    .map { it.replace(Regex("[BR]"), "1")}
    .map { it.toInt(2) }

fun main() = printAnswers(
    placeIDs.max(),
    (0..1023)
        .toList()
        .filter { it !in placeIDs }
        .filter { (it - 1) in placeIDs && (it + 1) in placeIDs }
)