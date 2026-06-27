package com.example.rateflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rateflow.data.local.dao.CurrencyDao
import com.example.rateflow.data.local.dao.RateDao
import com.example.rateflow.data.local.entity.CurrencyEntity

import com.example.rateflow.data.local.entity.OfficialRateEntity

@Database(
    entities = [
        CurrencyEntity::class,
        OfficialRateEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun rateDao(): RateDao
}
