package realaof.realhon.realha.cryptocurrencymini.util

import java.text.DecimalFormat
import kotlin.math.pow

const val NUMBER_WITH_COMMA_AND_DOLLAR_SIGN_5DECIMAL = "\$#,##0.00000"
const val NUMBER_WITH_COMMA_5_DECIMAL = "#,##0.00000"
const val NUMBER_WITH_COMMA_AND_DOLLAR_2DECIMAL = "\$#,##0.00"
const val NUMBER_WITH_2DECIMAL = "0.00"

const val CURRENCY_MILLION = "Million"
const val CURRENCY_BILLION = "Billion"
const val CURRENCY_TRILLION = "Trillion"

//n + 1
const val MILLION = 7
const val BILLION = 10
const val TRILLION = 11

fun String?.toMoneyFormat(pattern: String): String =
    DecimalFormat(pattern).format(this?.orZero()?.toDouble().orZero())

fun Float?.toMoneyFormat(pattern: String): String =
    DecimalFormat(pattern).format(this.orZero())

fun String?.toMoneyCurrency(): String {
    val money = this?.split(".")
    val length = money?.get(0).orZero().length.orZero()

    val currency = when {
        length in MILLION..<BILLION -> CURRENCY_MILLION
        length in BILLION..<TRILLION -> CURRENCY_BILLION
        length >= TRILLION -> CURRENCY_TRILLION
        else -> ""
    }

    val result = when {
        length in MILLION..<BILLION -> this?.toDouble().orZero() / 10.0.pow(MILLION - 1)
        length in BILLION..<TRILLION -> this?.toDouble().orZero() / 10.0.pow(BILLION - 1)
        length >= TRILLION -> this?.toDouble().orZero() / 10.0.pow(TRILLION - 1)
        length == 0 -> 0.0
        else -> this.orZero().toDouble().orZero()
    }

    return DecimalFormat("$NUMBER_WITH_2DECIMAL $currency")
        .format(result)
}

fun String?.orZero(): String = if (this.isNullOrEmpty()) "0.0" else this