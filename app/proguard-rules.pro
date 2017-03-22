# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/DANGLV/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-keep class com.flurry.** { *; }
-dontwarn com.flurry.**

-keep class com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**


-keep class com.jakewharton:butterknife-compiler.** { *; }
-keep class com.github.PhilJay.** { *; }
-keep class com.mobeta.android.dslv.** { *; }
-keep class com.android.support.** { *; }
-keep class org.jsoup:jsoup.** { *; }
-keep class pl.droidsonroids.gif.** { *; }
-keep class com.pnikosis.** { *; }
-keep class com.balysv.** { *; }
-keep class com.jakewharton:butterknife.** { *; }
-keep class com.yayandroid.** { *; }


-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keepattributes Signature
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class com.chartboost.** { *; }
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keepclassmembers class **.R$* {
    public static <fields>;
}

-printmapping mapping.txt
