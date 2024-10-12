package adventofcode.task_12

import adventofcode.utils.*
import kotlin.math.abs as abs

operator fun Pair<Int, Int>.plus(dir: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + dir.first, this.second + dir.second)

operator fun Pair<Int, Int>.times(count: Int): Pair<Int, Int> =
    Pair(this.first * count, this.second * count)

data class Rotation(val real: Int, val imag: Int)
fun Rotation.apply(p: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.real * p.first - this.imag * p.second, this.imag * p.first + this.real * p.second)
val identity = Pair(1, 0)

val dirs = mapOf(
    'E' to Rotation(1, 0),
    'N' to Rotation(0, 1),
    'W' to Rotation(-1, 0),
    'S' to Rotation(0, -1)
)
val rots = mapOf(
    'L' to 1,
    'R' to -1
)
const val rotation = "ENWS"

val input = readInputFile(12)
    .map { Regex("""^(\w)(\d+)$""").find(it)!!.groupValues }
    .map { (_, cmd, count) -> cmd[0] to count.toInt() }

fun firstTask(): Int {
    var pos = Pair(0, 0)
    var dir = 0
    for ((cmd, count) in input) {
        when (cmd) {
            'N', 'S', 'W', 'E'-> pos += dirs.getValue(cmd).apply(identity) * count
            'L', 'R' -> dir = (dir + rots.getValue(cmd) * (count / 90) + rotation.length) % rotation.length
            'F' -> pos += dirs.getValue(rotation[dir]).apply(identity) * count
        }
    }
    return abs(pos.first) + abs(pos.second)
}

fun secondTask(): Int {
    var pos = Pair(0, 0)
    var waypoint = Pair(10, 1)
    for ((cmd, count) in input) {
        when (cmd) {
            'N', 'S', 'W', 'E'-> waypoint += dirs.getValue(cmd).apply(identity) * count
            'L', 'R' -> {
                val dir = (rots.getValue(cmd) * (count / 90) + rotation.length) % rotation.length
                waypoint = dirs.getValue(rotation[dir]).apply(waypoint)
            }
            'F' -> pos += waypoint * count
        }
    }
    return abs(pos.first) + abs(pos.second)
}

fun main() {
    printAnswers(firstTask(), secondTask())
}

