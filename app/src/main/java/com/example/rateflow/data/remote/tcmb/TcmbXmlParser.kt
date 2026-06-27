package com.example.rateflow.data.remote.tcmb

import android.util.Xml
import com.example.rateflow.data.remote.tcmb.dto.TcmbCurrencyDto
import com.example.rateflow.data.remote.tcmb.dto.TcmbRatesResponseDto
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class TcmbXmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): TcmbRatesResponseDto {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readTarihDate(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readTarihDate(parser: XmlPullParser): TcmbRatesResponseDto {
        parser.require(XmlPullParser.START_TAG, ns, "Tarih_Date")
        val date = parser.getAttributeValue(null, "Tarih") ?: ""
        val currencies = mutableListOf<TcmbCurrencyDto>()

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "Currency") {
                currencies.add(readCurrency(parser))
            } else {
                skip(parser)
            }
        }
        return TcmbRatesResponseDto(date, currencies)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readCurrency(parser: XmlPullParser): TcmbCurrencyDto {
        parser.require(XmlPullParser.START_TAG, ns, "Currency")
        val code = parser.getAttributeValue(null, "CurrencyCode") ?: ""
        var name = ""
        var forexBuying: Double? = null
        var forexSelling: Double? = null
        var banknoteBuying: Double? = null
        var banknoteSelling: Double? = null
        var crossRateUSD: Double? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "Isim" -> name = readText(parser)
                "ForexBuying" -> forexBuying = readDouble(parser)
                "ForexSelling" -> forexSelling = readDouble(parser)
                "BanknoteBuying" -> banknoteBuying = readDouble(parser)
                "BanknoteSelling" -> banknoteSelling = readDouble(parser)
                "CrossRateUSD" -> crossRateUSD = readDouble(parser)
                else -> skip(parser)
            }
        }
        return TcmbCurrencyDto(
            code = code,
            name = name,
            forexBuying = forexBuying,
            forexSelling = forexSelling,
            banknoteBuying = banknoteBuying,
            banknoteSelling = banknoteSelling,
            crossRateUSD = crossRateUSD
        )
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDouble(parser: XmlPullParser): Double? {
        var result: Double? = null
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text.toDoubleOrNull()
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    companion object {
        private val ns: String? = null
    }
}
