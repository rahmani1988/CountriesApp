plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    kotlin("kapt") // todo change that to alias
    id("kotlin-parcelize") // todo change that to alias

}

android {
    namespace = "com.reza.feature.home"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
}

dependencies {

    // projects
    implementation(project(":core:threading"))
    implementation(project(":core:networking"))
    implementation(project(":core:common"))
    implementation(project(":core:systemdesign"))
    testImplementation(project(":core:testing:unit"))

    implementation(libs.javax.inject)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.google.material)

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.activity.compose)
    implementation(libs.material3)
    implementation(libs.material)
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
    implementation(libs.hilt.navigation.compose)

    // ui test
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}