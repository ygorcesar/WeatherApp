package com.challenge.networking

import kotlinx.serialization.json.Json

val jsonBuilder = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}