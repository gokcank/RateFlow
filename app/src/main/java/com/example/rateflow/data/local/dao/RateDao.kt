package com.example.rateflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rateflow.data.local.entity.OfficialRateEntity

@Dao
interface RateDao {
    @Query("SELECT * FROM official_rates ORDER BY code ASC")
    suspend fun getOfficialRates(): List<OfficialRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOfficialRates(rates: List<OfficialRateEntity>)

    @Query("DELETE FROM official_rates")
    suspend fun clearOfficialRates()
}
