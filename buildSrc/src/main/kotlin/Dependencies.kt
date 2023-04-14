object BuildVersion {
    const val compileSdk = 33
    const val minSdk = 28
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
}
object Versions{
    // 也可以把下面Libs中的version全部抽取到这里
}

object ProjectStructure{
    // true - 项目整体运行  false - 单组件运行
    const val isMainModule = false
}

object Libs {
    private const val junitVersion = "4.13.2"
    private const val testJunitVersion = "1.1.5"
    private const val testEspressoVersion = "3.5.1"
    
    private const val androidxCoreKtxVersion = "1.8.0"
    private const val androidxAppcompatVersion = "1.5.1"
    private const val materialVersion = "1.8.0"
    private const val constraintlayoutVersion = "2.1.4"
    private const val utilCodeVersion = "1.31.0"
    private const val androidCommonVersion = "4.2.15"
    private const val lifecycleRuntimeVersion = "2.5.1"
    private const val lifecycleLivedataVersion = "2.4.1"
    private const val lifecycleViewmodelVersion = "2.5.1"
    private const val dataStoreVersion = "1.0.0"
    private const val retrofit2Version = "2.9.0"
    private const val retrofit2AdapterVersion = "0.9.2"
    private const val okhttp3LoggInterceptorVersion = "4.10.0"
    private const val retrofit2ConverterGsonVersion = "2.9.0"
    private const val bannerVersion = "2.2.2"
    private const val viewpager2Version = "1.0.0"
    private const val coilVersion = "2.3.0"
    
    const val junit = "junit:junit:$junitVersion"
    const val testJunit = "androidx.test.ext:junit$testJunitVersion"
    const val testEspresso = "androidx.test.espresso:espresso-core:$testEspressoVersion"
    
    const val androidxCoreKtx = "androidx.core:core-ktx:$androidxCoreKtxVersion"
    const val androidxAppcompat = "androidx.appcompat:appcompat:$androidxAppcompatVersion"
    const val material = "com.google.android.material:material:$materialVersion"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintlayoutVersion"
    const val utilCode = "com.blankj:utilcodex:$utilCodeVersion" // AndroidUtilCode
    const val androidCommon = "cn.trinea.android.common:trinea-android-common:$androidCommonVersion" // android-common-lib 是 Trinea 大神收集的一些开发通用的缓存, 公共 View 以及一些常用工具类
    // lifecycle liveData viewModel
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleLivedataVersion"
    const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewmodelVersion"
    // datastore
    const val dataStore = "androidx.datastore:datastore-preferences:$dataStoreVersion"
    // retrofit
    const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofit2Version"
    const val retrofit2Adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofit2AdapterVersion"
    const val okhttp3LoggInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttp3LoggInterceptorVersion"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:$retrofit2ConverterGsonVersion"
    // banner
    const val banner = "io.github.youth5201314:banner:$bannerVersion"
    // viewpager2
    const val viewpager2 = "androidx.viewpager2:viewpager2:$viewpager2Version"
    const val coil = "io.coil-kt:coil:$coilVersion"
}
/**
 * 使用buildSrc构建版本的缺点：
 * 1.buildSrc 依赖更新将重新构建整个项目，项目越大，重新构建的时间就越长，造成不必要的时间浪费。
 * 2.依赖版本更新不会提示。
 * 可以使用 Gradle 插件来自动更新依赖库的版本号，如 Versions 插件。
 * 在项目的根目录下的 build.gradle 文件中添加以下代码：
 * buildscript {
 *      repositories {
 *      mavenCentral()
 *   }
 *   dependencies {
 *      classpath "com.github.ben-manes:gradle-versions-plugin:0.36.0"
 *   }
 * }

 *  apply plugin: "com.github.ben-manes.versions"
 *  然后，在 app 模块的 build.gradle 文件中使用以下命令来检查依赖库的版本更新：
 *  ./gradlew dependencyUpdates
 *  这个命令会输出当前项目中所有依赖库的版本信息，并提示哪些依赖库可以更新。
 */