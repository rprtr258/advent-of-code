package adventofcode.task_3

import adventofcode.utils.*

val map: List<String> = readInputFile(3)
val width = map[0].length
val height = map.size
val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))

fun getTreesBySlope(slope: Pair<Int, Int>): Long = slope.let {
    val (dx, dy) = it
    generateSequence(Pair(0, 0))
    { (x, y) -> if (y + dy < height) Pair((x + dx) % width, y + dy) else null }
        .map { (x, y) -> map[y][x] }
        .count { it == '#' }
        .toLong()
}

fun main() = printAnswers(
    getTreesBySlope(Pair(3, 1)),
    slopes.map(::getTreesBySlope).reduce { x, y -> x * y }
)