plugins {
    id("com.android.application") version "8.4.0" apply false
    id("com.android.library") version "8.4.0" apply false
    kotlin("android") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    alias(libs.plugins.com.android.test) apply false
}

