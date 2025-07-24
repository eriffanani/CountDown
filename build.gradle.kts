plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory.asFile.get())
}