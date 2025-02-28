plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.pavlig43.retromeetdata"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.retrofit)
    api(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.androidx.annotation)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit.adapters.result)

    implementation(libs.javax.inject)
    implementation(projects.retromeetcommon)

    implementation(libs.androidx.paging.runtime)


    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.kts)
    implementation(libs.androidx.room.paging)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.java.websocket)
    implementation(libs.kotlinx.datetime)



}
