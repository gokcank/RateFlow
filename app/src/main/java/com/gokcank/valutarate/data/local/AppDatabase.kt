package com.gokcank.valutarate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gokcank.valutarate.data.local.dao.CurrencyDao
import com.gokcank.valutarate.data.local.dao.RateDao
import com.gokcank.valutarate.data.local.entity.CurrencyEntity

import com.gokcank.valutarate.data.local.entity.OfficialRateEntity

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
