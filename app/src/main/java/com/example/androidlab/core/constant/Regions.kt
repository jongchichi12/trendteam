package com.example.androidlab.core.constants

enum class Region(val label: String) {
    SEOUL("서울"),
    CHUNGBUK("충북"),
    JEONBUK("전북");

    companion object {
        val items: List<Region> = values().toList()
        fun fromLabel(label: String): Region =
            items.firstOrNull { it.label == label } ?: SEOUL
    }
}