package com.example.weather

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class WeatherAppGlideModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false
}