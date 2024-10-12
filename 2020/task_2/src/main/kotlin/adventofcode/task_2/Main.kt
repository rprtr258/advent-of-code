package adventofcode.task_2

import adventofcode.utils.*

val parser: Regex = Regex("""^(\d+)-(\d+) (\w): (\w+)$""")

data class PolicyAndPassword(val x: Int, val y: Int, val char: Char, val password: String)

val pps: List<PolicyAndPassword> = readInputFile(2)
    .map { parser.find(it)?.groupValues ?: throw error("Happy new year!") }
    .map { PolicyAndPassword(
        x=it[1].toInt(),
        y=it[2].toInt(),
        char=it[3][0],
        password=it[4]
    ) }

fun main() = printAnswers(
    pps.count { (x, y, c, p) -> p.count { it == c} in x..y },
    pps.count { (x, y, c, p) -> (p[x - 1] == c) xor (p[y - 1] == c) }
)