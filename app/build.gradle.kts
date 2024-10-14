import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "top.voemp.sppatch"
    compileSdk = 35

    defaultConfig {
        applicationId = "top.voemp.sppatch"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    java.toolchain.languageVersion = JavaLanguageVersion.of(21)
    kotlin.jvmToolchain(21)
    packaging {
        resources.excludes += "**"
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName = "SPPatch-$versionName.apk"
            }
        }
    }
}

dependencies {
    compileOnly(libs.xposed)
    implementation(libs.ezXHelper)
}