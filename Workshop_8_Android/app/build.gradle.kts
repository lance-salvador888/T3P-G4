plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.workshop_8_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.workshop_8_android"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\orangentle\\Documents\\GitHub\\T3P-G4\\Workshop_8_Android\\app\\lib",
        "include" to listOf("*.aar", "*.jar"),
    )))
    implementation(files("lib\\android-query.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}