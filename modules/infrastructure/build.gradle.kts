plugins {
    war
}

dependencies {
    implementation(project(":modules:domain"))
    implementation(project(":modules:adapters"))
    implementation(project(":modules:application"))
}
