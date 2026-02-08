plugins {
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    
    // ❌ 删除下面这一行 (因为你删除了 agp-lib 的定义)
    // alias(libs.plugins.agp.lib) apply false 

    // ❌ 删除下面这一行 (因为你删除了 jetbrains-compose 的定义)
    // alias(libs.plugins.jetbrains.compose) apply false

    // 保留其他插件 (如果有的话，例如 lsplugin)
    alias(libs.plugins.lsplugin.apksign) apply false
    alias(libs.plugins.lsplugin.resopt) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.compose.compiler) apply false
}
