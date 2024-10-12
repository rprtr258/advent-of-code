package adventofcode.task_7

import adventofcode.utils.*

val bagRegex = Regex("""^(\w+ \w+) bags contain (.+)\.$""")
val contentRegex = Regex("""(\d+) (\w+ \w+) bags?""")
const val start = "shiny gold"
val input: List<Pair<String, List<List<String>>>> = readInputFile(7)
    .map { bagRegex.find(it)!!.groupValues }
    .map { (_, from, to) -> Pair(from, contentRegex.findAll(to).map { it.groupValues }.toList()) }
val parents: List<Pair<String, String>> = input
    .map { (from, to) -> Pair(from, to.map { it[2] }) }
    .flatMap { (x, ys) -> ys.map { Pair(it, x) } }
val children: Map<String, List<String>> = input
    .map { (from, to) -> Pair(from, to.flatMap { x -> List(x[1].toInt()) { x[2] }  }) }
    .toMap()
val visited: MutableSet<String> = mutableSetOf()
val count: MutableMap<String, Int> = mutableMapOf()

fun dfs(v: String) {
    if (v in visited)
        return
    visited.add(v)
    for (to in parents)
        if (to.first == v)
            dfs(to.second)
}

fun dfs2(v: String): Int {
    if (v in count)
        return count.getValue(v)
    count[v] = children.getValue(v)
        .map(::dfs2)
        .sum() + 1
    return count.getValue(v)
}

fun main() {
    dfs(start)
    printAnswers(
        visited.minus(start).size,
        dfs2(start) - 1
    )
}