# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Needed for minify to work properly for integrators (else can cause a classDuplicationError with different dependencies)
-repackageclasses 'ch.opentransportdata.ojp'
-allowaccessmodification
-keeppackagenames doNotKeepAThing

-keep class ch.opentransportdata.ojp.domain.model.** { *; }
-keepclasseswithmembers class ch.opentransportdata.ojp.domain.usecase.Initializer { *; }
-keep class ch.opentransportdata.ojp.OjpSdk{ *; }
-keep class ch.opentransportdata.ojp.data.dto.converter.** { *; }
-keep class ch.opentransportdata.ojp.data.dto.response.** { *; }
-keep class ch.opentransportdata.ojp.data.dto.request.tir.** { *; }

## Joda Time 2.3
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

# Parceler rules Source: https://github.com/johncarl81/parceler#configuring-proguard
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio
-dontwarn java.nio.file.*
-dontwarn okio.**

# Retrofit 2.X  https://square.github.io/retrofit/
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}