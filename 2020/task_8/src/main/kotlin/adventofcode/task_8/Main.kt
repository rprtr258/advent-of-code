package adventofcode.task_8

import adventofcode.utils.*

val program = readInputFile(8)
    .map { it.split(" ") }
    .map { (cmd, valueStr) -> cmd to valueStr.toInt() }
        as MutableList<Pair<String, Int>>
val lastEIP = program.size

fun interpret(): Pair<Int?, Boolean> {
    var eip = 0
    var acc = 0
    val visited: MutableSet<Int> = mutableSetOf()
    while (!visited.contains(eip) && eip in program.indices) {
        visited.add(eip)
        val (cmd, value) = program[eip]
        when {
            "acc" in cmd -> { acc += value; eip++ }
            "jmp" in cmd -> { eip += value }
            "nop" in cmd -> { eip++ }
        }
    }
    return Pair(acc, eip == lastEIP)
}

fun patchUnpatch(at: Int) = program[at].let {
    val (cmd, x) = it
    program[at] = Pair(if (cmd == "jmp") "nop" else "jmp", x)
}

fun patchedInterpret(at: Int): Int? {
    patchUnpatch(at)
    val (acc, terminated) = interpret()
    patchUnpatch(at)
    return if (terminated) acc else null
}

fun main() = printAnswers(
    interpret().first,
    program.indices
        .filter { "acc" != program[it].first }
        .mapNotNull(::patchedInterpret)
)