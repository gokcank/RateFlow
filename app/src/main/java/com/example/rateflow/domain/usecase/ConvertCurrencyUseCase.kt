package com.example.rateflow.domain.usecase

import com.example.rateflow.domain.model.ConversionResult
import com.example.rateflow.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(amount: Double, fromCurrency: String, toCurrency: String): Result<ConversionResult> {
        return try {
            val fromCode = fromCurrency.uppercase()
            val toCode = toCurrency.uppercase()

            if (fromCode == toCode) {
                return Result.success(ConversionResult(amount, fromCode, toCode, amount, 1.0, true))
            }

            // Try to use TCMB rates first
            val officialRatesResult = repository.getOfficialRates(forceRefresh = false)
            val officialRates = officialRatesResult.rates

            val fromOfficial = officialRates.find { it.code == fromCode }
            val toOfficial = officialRates.find { it.code == toCode }

            val tryCode = "TRY"

            // 1. Base is TRY, Target is Official
            if (fromCode == tryCode && toOfficial != null) {
                val sellingRate = toOfficial.forexSelling ?: toOfficial.forexBuying ?: throw Exception("Rate missing for $toCode")
                val rate = 1.0 / sellingRate
                val result = amount * rate
                return Result.success(ConversionResult(amount, fromCode, toCode, result, rate, true))
            }

            // 2. Base is Official, Target is TRY
            if (toCode == tryCode && fromOfficial != null) {
                val buyingRate = fromOfficial.forexBuying ?: fromOfficial.forexSelling ?: throw Exception("Rate missing for $fromCode")
                val result = amount * buyingRate
                return Result.success(ConversionResult(amount, fromCode, toCode, result, buyingRate, true))
            }

            // 3. Both are Official
            if (fromOfficial != null && toOfficial != null) {
                // from -> TRY -> to
                val buyingRate = fromOfficial.forexBuying ?: fromOfficial.forexSelling ?: throw Exception("Rate missing for $fromCode")
                val sellingRate = toOfficial.forexSelling ?: toOfficial.forexBuying ?: throw Exception("Rate missing for $toCode")
                
                val rate = buyingRate / sellingRate
                val result = amount * rate
                return Result.success(ConversionResult(amount, fromCode, toCode, result, rate, true))
            }

            throw Exception("Kur verisi bulunamadı: $fromCode veya $toCode")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
