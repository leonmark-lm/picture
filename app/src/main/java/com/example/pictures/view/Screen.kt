package com.example.pictures.view

sealed class Screen(val route : String) {
    object MainScreen : Screen("main_screen")
    object PictureScreen : Screen("picture_screen")

    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach {
                arg -> append("/$arg")
            }
        }
    }
}