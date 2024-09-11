##---------------Begin: proguard configuration for SQLCipher  ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit  ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-dontwarn kotlinx.**


##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier

-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

#-------------------------------------------------
# JetPack Navigation
# This fixes: Caused by: androidx.fragment.app.Fragment$InstantiationException: Unable to instantiate fragment androidx.navigation.fragment.NavHostFragment: make sure class name exists
#-------------------------------------------------
-keepnames class androidx.navigation.fragment.NavHostFragment

-dontwarn com.sun.javadoc.Doclet

-dontwarn java.lang.invoke.StringConcatFactory

-keep class java.lang.invoke.** {*;}

# Keep MovieRepository and its members
-keep class com.benaya.core.data.dataSource.MovieRepository { *; }
-keep class com.benaya.core.** { *; }

# Keep Resource classes and their members
-keep class com.benaya.core.data.dataSource.Resource$Error { *; }
-keep class com.benaya.core.data.dataSource.Resource$Loading { *; }
-keep class com.benaya.core.data.dataSource.Resource$Success { *; }
-keep class com.benaya.core.data.dataSource.Resource { *; }

# Keep DataSource classes and their members
-keep class com.benaya.core.data.dataSource.local.LocalDataSource { *; }
-keep class com.benaya.core.data.dataSource.remote.RemoteDataSource { *; }
-keep class com.benaya.core.data.dataSource.remote.retrofit.ApiServices { *; }
-keep class com.benaya.core.data.dataSource.local.room.MovieDao { *; }

# Keep DI modules and their providers
-keep class  com.benaya.core.di.** { *; }
-keep class  com.benaya.core.di.DatabaseModule { *; }
-keep class  com.benaya.core.di.DatabaseModule_ProvideDatabaseFactory { *; }
-keep class  com.benaya.core.di.DatabaseModule_ProvideMovieDaoFactory { *; }
-keep class  com.benaya.core.di.NetworkModule { *; }
-keep class  com.benaya.core.di.NetworkModule_ProvideApiServiceFactory { *; }
-keep class  com.benaya.core.di.NetworkModule_ProvideOkHttpClientFactory { *; }

# Keep domain classes and their members
-keep class  com.benaya.core.domain.model.Movie { *; }
-keep class  com.benaya.core.domain.repository.IMovieRepository { *; }
-keep class  com.benaya.core.domain.usecase.MovieInteractor { *; }
-keep class  com.benaya.core.domain.usecase.MovieUseCase { *; }

# Keep UI classes and their members
-keep class com.benaya.core.presentation.MovieAdapter { *; }
-keep class com.benaya.core.presentation.MovieAdapter$OnItemClickCallback { *;}

# Keep utility classes
-keep class com.benaya.core.utils.AppExecutors { *; }
-keep class com.benaya.core.utils.DarkMode { *; }

# Keep Hilt components
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponentManager { *; }
-keep class * implements dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep classes with @Inject constructors
-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

# Keep Hilt Android classes (for projects using Hilt with Android)
-keep class dagger.hilt.android.** { *; }
-keep interface dagger.hilt.android.** { *; }

# Keep classes annotated with @HiltAndroidApp, @AndroidEntryPoint, etc.
-keep @dagger.hilt.android.HiltAndroidApp class *
-keep @dagger.hilt.android.AndroidEntryPoint class *
-keep @dagger.hilt.InstallIn class *
-keep @dagger.Module class *
-keep @dagger.hilt.components.SingletonComponent class *
-keep @dagger.hilt.android.components.ActivityComponent class *
-keep @dagger.hilt.android.components.FragmentComponent class *

# Keep classes with @Module annotations
-keep @dagger.Module class * { *; }

# Keep classes with @Provides annotations
-keep class * {
    @dagger.Provides <methods>;
}

# Keep annotations
-keepattributes *Annotation*