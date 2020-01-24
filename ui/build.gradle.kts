plugins {
    id("org.jetbrains.kotlin.js") version "1.3.61"
}

apply {
    // this plugin minifies the kotlin js output. Several bugs currently with this (e.g. multiple npms having an index.js)
    plugin("kotlin-dce-js")
}

group = "com.jillesvangurp"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

val closureCompiler by configurations.creating

dependencies {
    implementation(kotlin("stdlib-js"))
    testImplementation(kotlin("test-js"))
    // needed for the google closure compiler, which is recommended to minify the output of dce
    closureCompiler("com.google.javascript:closure-compiler:v20200112")
}

kotlin {
    sourceSets["main"].dependencies {
        // set kotlin.js.experimental.generateKotlinExternals=true in gradle.properties for dukat to do its thing

        implementation(npm("left-pad", "1.3.0"))
        implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.6.12")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.3")
    }
    target {
        useCommonJs()

        browser {
            webpackTask {

            }
        }
    }
}

tasks {
    register<Copy>("copy-index-html") {
        from("src/main/resources/index.html")
        into("build/packaged")
    }

    register<Copy>("copy-dce-html") {
        from("src/main/resources/dce.html")
        into("build//kotlin-js-min/main")
    }
    // does not work because of module errors
    register<JavaExec>("googleClosureCompileDceJs") {
        dependsOn("copy-index-html","copy-dce-html","assemble","runDceKotlin")
        classpath = closureCompiler
        main = "com.google.javascript.jscomp.CommandLineRunner"
        args = listOf(
            "--compilation_level=SIMPLE_OPTIMIZATIONS",
//            "--process_common_js_modules", // errors with a  ERROR - [JSC_INVALID_MODULE_PATH] Invalid module path "kotlin" for resolution mode "BROWSER"
            // nope, this doesn't work either
            "--js_output_file=build/packaged/${rootProject.name}.js",
            "build/kotlin-js-min/main/kotlin.js",
            "build/kotlin-js-min/main/kotlinx-coroutines-core.js",
            "build/kotlin-js-min/main/kotlinx-html-js.js",
            // this is left-pad, which the dce plugin fails to name properly. Also, you can only have 1 file called index.js
            "build/kotlin-js-min/main/index.js",
            "build/kotlin-js-min/main/${rootProject.name}.js"
        )
    }

    // processes the output in distributions (12M) and produces a similarly sized file that does not work (unlike the original)
    register<JavaExec>("googleClosureCompileDistributionJs") {
        dependsOn("copy-index-html","assemble")
        classpath = closureCompiler
        main = "com.google.javascript.jscomp.CommandLineRunner"
        args = listOf(
            "--compilation_level=SIMPLE_OPTIMIZATIONS",
            "--js_output_file=build/packaged/${rootProject.name}.js",
            "build/distributions/${rootProject.name}.js"
        )
    }
}
