package com.barsite.android.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard")
    object Sales : Screen("sales")
    object Products : Screen("products")
    object Clients : Screen("clients")
    object Stock : Screen("stock")
}
