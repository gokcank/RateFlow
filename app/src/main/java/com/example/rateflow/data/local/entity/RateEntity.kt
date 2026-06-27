package com.example.rateflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "official_rates")
data class OfficialRateEntity(
    @PrimaryKey val code: String,
    val name: String,
    val forexBuying: Double?,
    val forexSelling: Double?,
    val banknoteBuying: Double?,
    val banknoteSelling: Double?,
    val crossRateUSD: Double?,
    val date: String,
    val lastUpdated: Long
)

