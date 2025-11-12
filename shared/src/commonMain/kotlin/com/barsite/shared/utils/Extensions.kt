package com.barsite.shared.utils

fun Double.formatCurrency(): String {
    return "${Constants.CURRENCY_SYMBOL} ${this.formatNumber()}"
}

fun Double.formatNumber(): String {
    return String.format("%.2f", this)
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(this)
}

fun String.isValidPhone(): Boolean {
    // Cameroon phone number format: 6XXXXXXXX or +237XXXXXXXXX
    val phoneRegex = "^(\\+237)?[26]\\d{8}$".toRegex()
    return phoneRegex.matches(this)
}
