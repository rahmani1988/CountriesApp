plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.apollographql.apollo3") version "3.8.2" // todo: put it in version catalog
    alias(libs.plugins.hilt.android)
    kotlin("kapt") // todo change that to alias
}

android {
    namespace = "com.reza.networking"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"https://countries.trevorblades.com/graphql\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Apollo
    implementation(libs.apollo)

    // OkHttpLogging
    implementation(libs.okhttp.logging)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
}

apollo {
    service("service") {
        packageName.set("com.reza")

        // Enable test builder generation
        generateTestBuilders.set(true)
    }
}