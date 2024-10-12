package adventofcode.task_4

import adventofcode.utils.*

val yearRegex = Regex("""^\d{4}$""")
val hgtRegex: Regex = Regex("""^(\d+)(cm|in)$""")
val fieldRegexes = mapOf(
    "byr" to yearRegex,
    "iyr" to yearRegex,
    "eyr" to yearRegex,
    "hgt" to hgtRegex,
    "hcl" to Regex("^#[0-9a-f]{6}$"),
    "ecl" to Regex("^(amb|blu|brn|gry|grn|hzl|oth)$"),
    "pid" to Regex("""^\d{9}$""")
)

val passports: List<Map<String, String>> = readInputFile(4)
    .split("")
    .asSequence()
    .map { it.joinToString(" ") }
    .map { it.split(" ") }
    .map { passport -> passport.map { it.split(":") } }
    .map { it.map { (x, y) -> Pair(x, y) } }
    .map { it.toMap() }
    .toList()
val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun isValid(passport: Map<String, String>): Boolean = requiredFields.all { it in passport }

fun isStronglyValid(passport: Map<String, String>): Boolean {
    fun isValidHeight(heightField: String): Boolean {
        val (_, hgtStr, pts) = hgtRegex.find(heightField)!!.groupValues
        val hgt = hgtStr.toInt()
        return pts == "cm" && hgt in 150..193 || pts == "in" && hgt in 59..76
    }

    return isValid(passport) &&
           fieldRegexes.all { (k, v) -> v.matches(passport.getValue(k)) } &&
           listOf(
               Pair("byr", 1920..2002),
               Pair("iyr", 2010..2020),
               Pair("eyr", 2020..2030)
           ).all { (k, range) -> passport.getValue(k).toInt() in range } &&
           isValidHeight(passport.getValue("hgt"))
}

fun main() = printAnswers(passports.count(::isValid), passports.count(::isStronglyValid))