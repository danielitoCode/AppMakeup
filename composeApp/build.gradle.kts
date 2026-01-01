import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()
    
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
            // Icon
            // implementation(libs.composeIcons.fontAwesome)
            implementation(libs.icon.extended)
            // Nav
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            // FileSystem
            implementation(libs.filesystem.okio)
            // Cache
            implementation(libs.multiplatform.cache)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.filesystem.okio.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.elitec.appmakeup.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi)

            packageName = "AppMakeup"
            packageVersion = "1.0.0"

            modules(
                "java.base",
                "java.desktop",
                "java.logging",
                "java.sql",
                "jdk.unsupported"
            )

            windows {
                shortcut = true
                perUserInstall = true
            }
        }
    }
}

/*compose.desktop {
    application {
        mainClass = "com.elitec.appmakeup.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.elitec.appmakeup"
            packageVersion = "1.0.0"
        }
    }
}*/
