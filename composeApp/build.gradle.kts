import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17) // Update to 17
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3") // Use the latest version
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            // Add KMM compatible libraries here
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            implementation("io.insert-koin:koin-compose:1.1.2")
            implementation(libs.compose.uiToolingPreview) // Move or add this here
        }

        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            // Move Android-specific Room and Coroutines here
            val room_version = "2.6.1"
            implementation("androidx.room:room-runtime:$room_version")
            implementation("androidx.room:room-ktx:$room_version")
            // Note: KSP for Room must be configured separately (see below)
        }
    }
}

android {
    namespace = "org.example.pantry_manager_scan"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.pantry_manager_scan"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Update to 17
        targetCompatibility = JavaVersion.VERSION_17 // Update to 17
    }
    buildToolsVersion = "36.0.0"
}

dependencies {
    // 1. Core Jetpack Compose & Material 3
    implementation(platform("androidx.compose:compose-bom:2024.02.00")) // Gunakan BOM terbaru
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 2. Navigation untuk Compose (Pengganti Navigator di Flutter)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 3. Lifecycle & ViewModel (State Management)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // 4. Dependency Injection (Koin - Lebih ringan dan sangat KMM-friendly)
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    // 5. Coroutines (Untuk Asynchronous / background task)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 6. Local Database (Room - Standar Android untuk saat ini sebelum full KMM)
    add("kspAndroid", "androidx.room:room-compiler:2.6.1")
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
}

