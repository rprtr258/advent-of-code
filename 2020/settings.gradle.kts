rootProject.name = "Advent-of-code-2020"
(1..14).map { "task_$it" }.map { include(it) }
include("utils")
