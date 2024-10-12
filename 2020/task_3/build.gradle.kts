plugins {
    id("adventofcode.kotlin-application-conventions")
}

dependencies {
    implementation(project(":utils"))
}

application {
    mainClass.set("adventofcode.task_3.MainKt")
}
