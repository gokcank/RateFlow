package com.gokcank.valutarate.di

import android.content.Context
import androidx.room.Room
import com.gokcank.valutarate.data.local.AppDatabase
import com.gokcank.valutarate.data.local.dao.CurrencyDao
import com.gokcank.valutarate.data.local.dao.RateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rateflow_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao = database.currencyDao()

    @Provides
    fun provideRateDao(database: AppDatabase): RateDao = database.rateDao()
}
