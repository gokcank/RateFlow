package com.example.rateflow.data.remote.tcmb.dto

data class TcmbCurrencyDto(
    val code: String,
    val name: String,
    val forexBuying: Double?,
    val forexSelling: Double?,
    val banknoteBuying: Double?,
    val banknoteSelling: Double?,
    val crossRateUSD: Double?
)

data class TcmbRatesResponseDto(
    val date: String,
    val currencies: List<TcmbCurrencyDto>
)
