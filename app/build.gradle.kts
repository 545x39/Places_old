import dependencies.Application.APPLICATION_ID
import dependencies.Application.COMPILE_SDK
import dependencies.Application.MIN_SDK
import dependencies.Application.TARGET_SDK
import dependencies.Application.VERSION_CODE
import dependencies.Application.VERSION_NAME
import dependencies.Versions.APPCOMPAT_VERSION
import dependencies.Versions.CORE_VERSION
import dependencies.Versions.COROUTINES_VERSION
import dependencies.Versions.DAGGER_VERSION
import dependencies.Versions.ESPRESSO_VERSION
import dependencies.Versions.GSON_VERSION
import dependencies.Versions.INTERCEPTOR_VERSION
import dependencies.Versions.JUNIT_EXT_VERSION
import dependencies.Versions.JUNIT_VERSION
import dependencies.Versions.LIFECYCLE_EXT_VERSION
import dependencies.Versions.LIFECYCLE_VERSION
import dependencies.Versions.MATERIAL_VERSION
import dependencies.Versions.NAVIGATION_VERSION
import dependencies.Versions.PLAY_CORE_VERSION
import dependencies.Versions.PREFERENCE_VERSION
import dependencies.Versions.RETROFIT_VERSION
import dependencies.Versions.ROOM_VERSION
import dependencies.Versions.TIMBER_VERSION
import dependencies.Versions.WORK_MANAGER_VERSION

//<editor-fold defaultstate="collapsed" desc="PLUGINS">
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ANDROID">
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

    //<editor-fold defaultstate="collapsed" desc="BUILD TYPES">
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    //</editor-fold">

    //<editor-fold defaultstate="collapsed" desc="COMPILE OPTIONS">
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    //</editor-fold">

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
//        viewBinding = true
        dataBinding = true
    }

    setDynamicFeatures(setOf(":map", ":search", ":settings", ":objectdetails"))
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="DEPENDENCIES">
dependencies {
    /** ANDROID CORE */
    implementation("androidx.core:core-ktx:$CORE_VERSION")
    implementation("androidx.appcompat:appcompat:$APPCOMPAT_VERSION")
    implementation("com.google.android.material:material:$MATERIAL_VERSION")
    /** VIEW MODEL AND LIVE DATA */
    implementation("androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION")
    /** DAGGER */
    implementation("com.google.dagger:dagger:$DAGGER_VERSION")
    kapt("com.google.dagger:dagger-compiler:$DAGGER_VERSION")
    kapt("com.google.dagger:dagger-android-processor:$DAGGER_VERSION")
    ////
    kaptAndroidTest("com.google.dagger:dagger-compiler:$DAGGER_VERSION")
    androidTestAnnotationProcessor("com.google.dagger:dagger-compiler:$DAGGER_VERSION")
    /** ROOM */
    implementation("androidx.room:room-runtime:$ROOM_VERSION")
    kapt("androidx.room:room-compiler:$ROOM_VERSION")
    implementation("androidx.room:room-ktx:$ROOM_VERSION")
    //Both RxJava dependencies seem to be required for usage with androidx
//    implementation("androidx.room:room-rxjava2:$ROOM_VERSION")
    /** RETROFIT */
    implementation("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:$INTERCEPTOR_VERSION")
    implementation("com.squareup.retrofit2:converter-scalars:$RETROFIT_VERSION")
    /** GSON */
    implementation("com.google.code.gson:gson:$GSON_VERSION")
    /** Coroutine lifecycle extensions */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_EXT_VERSION")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_EXT_VERSION")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_EXT_VERSION")
    /** TIMBER */
    implementation("com.jakewharton.timber:timber:$TIMBER_VERSION")
    /** COROUTINES */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION")
    /** WORK MANAGER (+ coroutines) */
    implementation("androidx.work:work-runtime-ktx:$WORK_MANAGER_VERSION")
    /** PREFERENCES */
    implementation("androidx.preference:preference-ktx:$PREFERENCE_VERSION")
    /** NAVIGATION COMPONENT */
    implementation("androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION")
    implementation("androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION")
    api("androidx.navigation:navigation-dynamic-features-fragment:$NAVIGATION_VERSION")
    /** PLAY CORE */
    api("com.google.android.play:core:$PLAY_CORE_VERSION")
    /** TESTING */
    testImplementation("junit:junit:$JUNIT_VERSION")
    androidTestImplementation("androidx.test.ext:junit:$JUNIT_EXT_VERSION")
    androidTestImplementation("androidx.test.espresso:espresso-core:$ESPRESSO_VERSION")
    /** ROOM */
    implementation("androidx.room:room-runtime:$ROOM_VERSION")
    kapt("androidx.room:room-compiler:$ROOM_VERSION")
    implementation("androidx.room:room-ktx:$ROOM_VERSION")
}
//</editor-fold>