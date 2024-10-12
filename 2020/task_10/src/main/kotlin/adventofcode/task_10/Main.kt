package adventofcode.task_10

import adventofcode.utils.*

val input = readInputFile(10)
    .map { it.toInt() }
    .toMutableList()
    .let {
        it.add(0)
        it.add(it.max()!! + 3)
        it
    }
    .sorted()
val graph = input
    .map { u -> u to input.filter { v -> v - u in 1..3 } }
    .toMap()
val dp: MutableMap<Int, Long> = mutableMapOf(input.last() to 1.toLong())

fun dfs(v: Int): Long {
    if (v !in dp)
        dp[v] = graph.getValue(v).map(::dfs).sum()
    return dp.getValue(v)
}

fun main() = printAnswers(
    input
        .zipWithNext { x, y -> y - x }
        .groupBy { it }
        .mapValues { (_, v) -> v.size }
        .let { it.getValue(1) * it.getValue(3) },
    dfs(0)
)