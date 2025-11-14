package com.example.androidlab.data.auth.mapper

import com.example.androidlab.core.constants.Region
import com.example.androidlab.data.auth.local.db.UserEntity
import com.example.androidlab.domain.auth.entity.User

fun UserEntity.toDomain(): User {
    val safeRegion = runCatching { Region.valueOf(region) }.getOrDefault(Region.SEOUL)
    return User(
        id = id,
        email = email,
        displayName = displayName,
        username = username,
        phone = phone,
        region = safeRegion
    )
}