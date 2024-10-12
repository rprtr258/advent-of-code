package adventofcode.task_11

import adventofcode.utils.*

fun String.set(i: Int, c: Char): String = this.replaceRange(i, i + 1, c.toString())

fun Boolean.toInt() = if (this) 1 else 0

const val FLOOR = '.'
const val FREE = 'L'
const val OCCUPIED = '#'

data class Rules(val getOccupiedCount: (List<String>, Int, Int) -> Int, val tolerance: Int)
val rules = Rules({ state, i, j -> state
    .subList(i - 1, i + 2)
    .joinToString("") { it.substring(j - 1, j + 2) }
    .set(1 * 3 + 1, FLOOR)
    .count { it == OCCUPIED }
}, 4)
val realRules = Rules({ state, i, j -> (-1..1)
    .flatMap { di -> (-1..1).map { dj -> di to dj } }
    .filterNot { (di, dj) -> di == 0 && dj == 0 }
    .map { (di, dj) ->
        val height = state.size
        val width = state[0].length
        var ci = i + di
        var cj = j + dj
        while (ci in 0 until height && cj in 0 until width) {
            if (state[ci][cj] != FLOOR)
                return@map (state[ci][cj] == OCCUPIED).toInt()
            ci += di
            cj += dj
        }
        return@map 0
    }
    .sum()
}, 5)

val map: List<String> = readInputFile(11)
    .map { "$FLOOR$it$FLOOR" }
    .toMutableList()
    .let {
        val wall = List(it[0].length) { FLOOR }.joinToString("")
        it.add(0, wall)
        it.add(wall)
        it
    }
val seats = map.indices
    .flatMap { i -> map[0].indices.map { j -> i to j } }
    .filter { (i, j) -> map[i][j] != FLOOR }

fun evolve(state: List<String>, rules: Rules): List<String> {
    val newState = state.toMutableList()
    for ((i, j) in seats) {
        val occupied = rules.getOccupiedCount(state, i, j)
        newState[i] = newState[i].set(j,
            when (state[i][j]) {
                FREE -> { if (occupied == 0) OCCUPIED else FREE }
                OCCUPIED -> { if (occupied >= rules.tolerance) FREE else OCCUPIED }
                else -> throw error("Happy new year!")
            }
        )
    }
    return newState
}

fun predictOccupied(rules: Rules): Int = generateSequence(map) { evolve(it, rules) }
    .zipWithNext()
    .takeWhile { (x, y) -> x != y }
    .map { (_, y) -> y }
    .last()
    .joinToString("")
    .count { it == OCCUPIED }

fun main() = printAnswers(predictOccupied(rules), predictOccupied(realRules))
