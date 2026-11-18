import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    // One JVM target for now: the Android app consumes it, and the JVM unit tests run on it. iOS targets join
    // when the iOS app starts (ADR 0073); nothing here imports Android.
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.io.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    // The planning golden vectors are shared with the server's kernels (FAH-268); CI checks out the repository root.
    systemProperty("divehub.vectors.dir", rootProject.file("../../packages/test-vectors/planning").absolutePath)
}
