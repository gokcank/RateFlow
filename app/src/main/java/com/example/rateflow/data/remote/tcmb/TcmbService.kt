package com.example.rateflow.data.remote.tcmb

import com.example.rateflow.data.remote.tcmb.dto.TcmbRatesResponseDto
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TcmbService(
    private val client: OkHttpClient,
    private val xmlParser: TcmbXmlParser
) {
    fun getTodayRates(): TcmbRatesResponseDto {
        val request = Request.Builder()
            .url("https://www.tcmb.gov.tr/kurlar/today.xml")
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            response.body?.byteStream()?.let { inputStream ->
                return xmlParser.parse(inputStream)
            } ?: throw IOException("Empty response body")
        }
    }

    fun getHistoricalRates(yearMonth: String, dayMonthYear: String): TcmbRatesResponseDto {
        // Example: https://www.tcmb.gov.tr/kurlar/202601/05012026.xml
        val url = "https://www.tcmb.gov.tr/kurlar/$yearMonth/$dayMonthYear.xml"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            response.body?.byteStream()?.let { inputStream ->
                return xmlParser.parse(inputStream)
            } ?: throw IOException("Empty response body")
        }
    }
}
