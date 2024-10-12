package adventofcode.task_13

import adventofcode.utils.*
import java.math.BigInteger

fun readData(): Pair<Int, List<Pair<Int, Int>>> {
    val (startTime, buses) = readInputFile(13)
    return Pair(startTime.toInt(), buses
        .split(",")
        .withIndex()
        .filterNot { it.value == "x" }
        .map { (x, y) -> x to y.toInt() }
    )
}

// Using small Ferma's theorem to find mod inverse
fun inverse(x: Long, mod: BigInteger): Long = x.toBigInteger().modPow(mod - BigInteger.ONE * 2.toBigInteger(), mod).toLong()

// Using chinese remainder theorem formula for primes from wikipedia
fun makeChinaGreatAgain(system: List<Pair<Int, Int>>): Long {
    val M: Long = system
        .map { it.second.toLong() }
        .reduce { a, x -> a * x }
    return system
        .map { (ri, ai) -> (ai - ri) * (M / ai) * inverse(M / ai, ai.toBigInteger()) }
        .sum() % M
}

fun main() {
    val (startTime, buses) = readData()
    printAnswers(
        buses
            .map { it.second }
            .map{ it to it - startTime % it }
            .minBy { it.second }
            .let { it!!.first * it.second },
        makeChinaGreatAgain(buses)
    )
}

