package com.barsite.shared.utils

object Constants {
    // App Configuration
    const val APP_NAME = "Barsite"
    const val APP_VERSION = "1.0.0"
    
    // Currency
    const val CURRENCY = "XAF" // Central African CFA franc
    const val CURRENCY_SYMBOL = "FCFA"
    
    // Default Values
    const val DEFAULT_PAGE_SIZE = 20
    const val MIN_STOCK_ALERT_DAYS = 7
    const val CREDIT_GRACE_PERIOD_DAYS = 30
    
    // Table Names
    const val TABLE_UTILISATEURS = "utilisateurs"
    const val TABLE_PRODUITS = "produits"
    const val TABLE_CLIENTS = "clients"
    const val TABLE_COMMANDES = "commandes"
    const val TABLE_STOCKS = "stocks"
    const val TABLE_FOURNISSEURS = "fournisseurs"
    const val TABLE_CREDITS = "credits"
    
    // Shared Preferences Keys
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
}
