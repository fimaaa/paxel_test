#-dontpreverify
-repackageclasses
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends androidx.multidex.MultiDexApplication.*
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep class * extends androidx.fragment.app.Fragment{}
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-keepclassmembers class android.arch.* { *; }
-keep class android.arch.* { *; }
-dontwarn android.arch.**
####--------Retrofit------####
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep public class kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.* { public *; }
####--------OKHTTP----------####
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn okhttp3.internal.platform.ConscryptPlatform
####----Kotlin-Reflect----####
-dontwarn kotlin.reflect.jvm.internal.**
-keep class kotlin.reflect.jvm.internal.* { *; }
####----Coroutine----####
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
-keep class kotlin.* { *; }
#-keepnames class kotlinx.coroutines.test.internal.TestMainDispatcherFactory {}
-keepnames class kotlinx.coroutines.test.TestCoroutineContextKt {}
-keepclassmembernames class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}
####--------GSON---------####
-keepattributes Signature
-dontwarn sun.misc.**
-keep class com.google.gson.stream.* { *;}
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
####--------Crashlytics---------####
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.* { *; }
-dontwarn com.crashlytics.**
####--------Model---------####
-keepclassmembers class com.example.paxeltest.data.model.*{*;}
####--------- Google ---------------####
-keep class com.google.ads.** # Don't proguard AdMob classes
-dontwarn com.google.ads.** # Temporary workaround for v6.2.1. It gives a warning that you can ignore