pluginManagement {
    repositories {// 指定插件的仓库位置
        gradlePluginPortal()
        google()
        mavenCentral()
        // banner
        // maven { setUrl("https://s01.oss.sonatype.org/content/groups/public") }
        // BannerViewPager
        maven { setUrl("https://www.jitpack.io") }
        maven { setUrl ("https://maven.aliyun.com/repository/jcenter") }
        maven { setUrl ("https://maven.aliyun.com/repository/google") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://www.jitpack.io") }
        maven { setUrl ("https://maven.aliyun.com/repository/jcenter") }
        maven { setUrl ("https://maven.aliyun.com/repository/google") }
    }
}
rootProject.name = "WanAndroid"
include(":app")
include(":module_base")
include(":module_home")
include(":module_project")
include(":module_navigation")
include(":module_mine")
include(":module_common")

/**
 * 在Gradle5.0 版本之后，新加入了pluginManagement和dependencyResolutionManagement这两个功能，主要用于管理 Gradle 插件和依赖项的版本。
 */
