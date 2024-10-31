import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 31
        targetSdk = 34
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11" // Fix the assignment operator
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.core.ktx)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Correct way to add Lottie dependency
    implementation("com.airbnb.android:lottie:6.1.0") // Use quotes for string
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.json:json:20210307")

    // GSON converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
}
