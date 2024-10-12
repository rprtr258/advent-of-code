package adventofcode.task_14

import adventofcode.utils.*

val setMaskRegex = Regex("""^mask = (\w{36})$""")
val setMemoryRegex = Regex("""^mem\[(\d+)\] = (\d+)$""")

fun pow(e: Long, p: Long): Long = (1..p)
    .map { e }
    .fold(1.toLong()) { x, y -> x * y }

fun firstTask(): Long {
    val memory: MutableMap<Int, Long> = mutableMapOf()
    var keepMask: Long = 0b111111111111111111111111111111111111
    var setMask: Long = 0b000000000000000000000000000000000000

    readInputFile(14)
        .map { line -> when {
        setMaskRegex.matches(line) -> {
            val mask = setMaskRegex.find(line)!!.groupValues[1]
            keepMask = mask.replace('0', '0').replace('1', '0').replace('X', '1').toLong(2)
            setMask = mask.replace('X', '0').toLong(2)
        }
        setMemoryRegex.matches(line) -> {
            val (address, value) = setMemoryRegex.find(line)!!.groupValues.let { Pair(it[1].toInt(), it[2].toLong()) }
            memory[address] = value and keepMask or setMask
        }
        else -> throw error("Happy new year!")
    } }
    return memory.values.sum()
}

fun applyMask(mask: String, address: String): String = mask.zip(address)
    .map { (m, a) ->
        when (m) {
            'X', '1' -> m
            '0' -> a
            else -> throw error("Happy new year!")
        }
    }
    .joinToString("")

fun intersectVariadicAdresses(a1: String, a2: String): String? =
    if (a1.zip(a2).any { (a, b) -> a == '0' && b == '1' || a == '1' && b == '0' }) null
    else a1.zip(a2)
        .map { (a, b) ->
            when (a) {
                '0', '1' -> a
                'X' -> when (b) {
                    '0', '1' -> b
                    'X' -> 'X'
                    else -> throw error("Happy new year!")
                }
                else -> throw error("Happy new year!")
            }
        }
        .joinToString("")

fun secondTask(): Long {
    val memory: MutableList<Pair<String, Long>> = mutableListOf()
    var mask = "000000000000000000000000000000000000"

    readInputFile(14)
        .map { line -> when {
            setMaskRegex.matches(line) -> mask = setMaskRegex.find(line)!!.groupValues[1]
            setMemoryRegex.matches(line) -> {
                val (addressNominal, value) = setMemoryRegex.find(line)!!.groupValues.let { it[1].toLong().toString(2) to it[2].toLong() }
                val address = applyMask(mask, "0".repeat(36 - addressNominal.length) + addressNominal)
                memory.addAll(memory
                    .map { (oldVariadicAdress, oldValue) -> intersectVariadicAdresses(address, oldVariadicAdress) to oldValue }
                    .filter { it.first != null }
                    .map { it.first!! to -it.second }
                )
                memory.add(address to value)
            }
            else -> throw error("Happy new year!")
        } }
    return memory
        .map { (address, value) -> pow(2, address.count { it == 'X' }.toLong()) * value }
        .sum()
}

fun main() = printAnswers(firstTask(), secondTask())
