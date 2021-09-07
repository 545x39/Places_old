import dependencies.Application.APPLICATION_ID
import dependencies.Application.COMPILE_SDK
import dependencies.Application.MIN_SDK
import dependencies.Application.TARGET_SDK
import dependencies.Application.VERSION_CODE
import dependencies.Application.VERSION_NAME
import dependencies.Versions.LIFECYCLE_VERSION

//<editor-fold defaultstate="collapsed" desc="PLUGINS">
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
//</editor-fold>


android {

//<editor-fold defaultstate="collapsed" desc="DEFAULT CONFIG">
    defaultConfig {
        applicationId = APPLICATION_ID
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        versionCode = VERSION_CODE
        versionName = VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        compileSdk = COMPILE_SDK
    }
//</editor-fold">
    buildTypes {
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    setDynamicFeatures(setOf(":map", ":search", ":settings", ":objectdetails"))
}

dependencies {
    ////VERSIONS
    val nav_version = "2.3.5"
    val dagger_version = "2.38.1"
    //noinspection SpellCheckingInspection
    val coroutines_version = "1.5.2-native-mt"
    val work_manager_version = "2.7.0-beta01"
    val preference_version = "1.1.1"
    val room_version = "2.3.0"
    val retrofit_version = "2.9.0"
    val gson_version = "2.8.6"
    val play_core_version = "1.10.1"
    val timber_version = "5.0.1"
    /**CORE */
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    /** VIEW MODEL AND LIVE DATA */
    implementation("androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION")
    /** DAGGER */
    implementation("com.google.dagger:dagger:$dagger_version")
    kapt("com.google.dagger:dagger-compiler:$dagger_version")
    kapt("com.google.dagger:dagger-android-processor:$dagger_version")
    /** ROOM */
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    //Both RxJava dependencies seem to be required for usage with androidx
//    implementation("androidx.room:room-rxjava2:$room_version")
    /** RETROFIT */
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
    /** GSON */
    implementation("com.google.code.gson:gson:$gson_version")
    //
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    /** Coroutine lifecycle extensions */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    /** TIMBER */
    implementation("com.jakewharton.timber:timber:$timber_version")
    /** COROUTINES */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    /** WORK MANAGER (+ coroutines) */
    implementation("androidx.work:work-runtime-ktx:$work_manager_version")
    /** PREFERENCES */
    implementation("androidx.preference:preference-ktx:$preference_version")
    /** NAVIGATION COMPONENT */
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    api("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    /** PLAY CORE */
    api("com.google.android.play:core:$play_core_version")
    /** TESTING */
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}