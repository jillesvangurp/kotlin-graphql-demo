plugins {
    id("org.jetbrains.kotlin.js") version "1.3.61"
}

apply {
//    plugin("kotlin-dce-js")
}

group = "com.jillesvangurp"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    testImplementation(kotlin("test-js"))
}

kotlin {
    sourceSets["main"].dependencies {
        // set kotlin.js.experimental.generateKotlinExternals=true in gradle.properties for dukat to do its thing

        implementation(npm("left-pad", "1.3.0"))
        implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.6.12")
    }
    target {
        useCommonJs()

        browser {
            webpackTask {

            }
        }
    }
}