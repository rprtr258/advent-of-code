package adventofcode.task_6

import adventofcode.utils.*

val answers: Sequence<List<Set<Char>>> = readInputFile(6)
    .split("")
    .asSequence()
    .map { it.map { x -> x.toSet() } }

fun countAnswers(getGroupAnswers: (List<Set<Char>>) -> Set<Char>): Int = answers
    .map { getGroupAnswers(it) }
    .map { it.size }
    .sum()

fun main() = printAnswers(
    countAnswers { it.fold("".toSet()) { acc, x -> acc.union(x) } },
    countAnswers { it.fold("abcdefghijklmnopqrstuvwxyz".toSet()) { acc, x -> acc.intersect(x) } }
)