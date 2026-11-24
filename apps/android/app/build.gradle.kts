plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.roborazzi)
}

val releaseSigningEnv = listOf(
    "ANDROID_RELEASE_KEYSTORE_PATH",
    "ANDROID_RELEASE_KEYSTORE_PASSWORD",
    "ANDROID_RELEASE_KEY_PASSWORD",
    "ANDROID_RELEASE_KEY_ALIAS",
)
val missingReleaseSigningEnv = releaseSigningEnv.filter { providers.environmentVariable(it).orNull.isNullOrBlank() }

// Phase B/C UI merges dark (FAH-457): the Dive edit screen stays hidden unless a build asks for the slice, e.g.
// `./gradlew assembleDebug -Pdivehub.editDiveScalars=true`. The default is what ships.
val editDiveScalars = providers.gradleProperty("divehub.editDiveScalars").orNull?.toBoolean() ?: false

fun envInt(name: String, fallback: Int): Int {
    val raw = providers.environmentVariable(name).orNull ?: return fallback
    return raw.toIntOrNull() ?: throw GradleException("$name must be an integer, got '$raw'")
}

android {
    namespace = "dev.divehub.android"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.divehub.android"
        minSdk = 26
        targetSdk = 37
        versionCode = envInt("ANDROID_VERSION_CODE", 1)
        versionName = providers.environmentVariable("ANDROID_VERSION_NAME").orNull ?: "0.1.0-dev"
        // Phase C merges dark (ADR 0084): the scanner is visible in debug builds, hidden in release until the
        // conflict surface ships.
        buildConfigField("boolean", "SCANNER_ENABLED", "false")
        buildConfigField("boolean", "EDIT_DIVE_SCALARS", editDiveScalars.toString())
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (missingReleaseSigningEnv.isEmpty()) {
            create("release") {
                storeFile = file(providers.environmentVariable("ANDROID_RELEASE_KEYSTORE_PATH").get())
                storePassword = providers.environmentVariable("ANDROID_RELEASE_KEYSTORE_PASSWORD").get()
                keyAlias = providers.environmentVariable("ANDROID_RELEASE_KEY_ALIAS").get()
                keyPassword = providers.environmentVariable("ANDROID_RELEASE_KEY_PASSWORD").get()
            }
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("boolean", "SCANNER_ENABLED", "true")
        }
        getByName("release") {
            if (missingReleaseSigningEnv.isEmpty()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

    testOptions {
        unitTests {
            // Robolectric needs the app's resources and manifest for the Compose screenshot tests.
            isIncludeAndroidResources = true
        }
    }
}

roborazzi {
    outputDir.set(layout.projectDirectory.dir("src/test/screenshots"))
}

tasks.matching { it.name == "preReleaseBuild" }.configureEach {
    doFirst {
        if (missingReleaseSigningEnv.isNotEmpty()) {
            throw GradleException("release signing env vars not set: ${missingReleaseSigningEnv.joinToString(", ")}")
        }
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.zxing.core)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.sqldelight.sqlite.driver)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
}
