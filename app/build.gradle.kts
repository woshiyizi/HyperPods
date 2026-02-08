plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.lsplugin.apksign)
    alias(libs.plugins.lsplugin.resopt)
    alias(libs.plugins.kotlinSerialization)
    
    // ✅ 修改：直接使用 ID，不带版本号，让它自动跟随 Kotlin 版本
    id("kotlin-parcelize")
    
    alias(libs.plugins.compose.compiler)
}

apksign {
    storeFileProperty = "KEYSTORE_FILE"
    storePasswordProperty = "KEYSTORE_PASSWORD"
    keyAliasProperty = "KEY_ALIAS"
    keyPasswordProperty = "KEY_PASSWORD"
}

android {
    namespace = "moe.chenxy.hyperpods"
    compileSdk = 36

    defaultConfig {
        applicationId = "moe.chenxy.hyperpods"
        minSdk = 35
        targetSdk = 36
        versionCode = 5
        versionName = "2.0.2-AAP-W-HyperOS3"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            multiDexEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    dependenciesInfo.includeInApk = false

    // 这里配置 Java 版本
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_22.majorVersion)
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/**.version"
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
            excludes += "okhttp3/**"
            excludes += "kotlin/**"
            excludes += "org/**"
            excludes += "**.properties"
            excludes += "**.bin"
            excludes += "kotlin-tooling-metadata.json"
        }
        jniLibs {
            useLegacyPackaging = false
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

// Kotlin 工具链配置（与 android.java.toolchain 保持一致）
kotlin {
    jvmToolchain(JavaVersion.VERSION_22.majorVersion.toInt())
}

configurations.configureEach {
    exclude(group = "androidx.lifecycle", module = "lifecycle-viewmodel-ktx")
    
    // 强制锁定版本，解决 AGP 8.8 与 AndroidX Core 的兼容性问题
    resolutionStrategy {
        force("androidx.core:core:1.15.0")
        force("androidx.core:core-ktx:1.15.0")
    }
}

dependencies {
    implementation(libs.coreKtx)
    compileOnly(libs.xposedApi)
    implementation(libs.yukihookApi)
    ksp(libs.yukihookKsp)
    implementation(libs.kotlinx.serialization.json)
    
    // UI Kits
    implementation(libs.yukonga.miuix)
    implementation(libs.androidx.activity.compose)

    // AndroidX Compose (BOM 管理版本)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    
    // ✅ 新增：显式引入 Layout (解决 Box, align 报错)
    implementation(libs.androidx.compose.foundation.layout)
    
    implementation(libs.androidx.compose.material3)
    
    // ✅ 新增：引入扩展图标库 (解决 Search, Settings, Info 报错)
    implementation(libs.androidx.compose.material.icons.extended)
    
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    
    implementation(libs.haze)
}
