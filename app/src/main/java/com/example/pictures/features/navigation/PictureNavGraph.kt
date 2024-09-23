package com.example.pictures.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pictures.features.view.HomePage.HomeScreen
import com.example.pictures.features.view.HomePage.HomeScreenNavDestination
import com.example.pictures.features.view.AddPicturePage.AddPictureScreen
import com.example.pictures.features.view.AddPicturePage.AddPictureScreenNavDestination
import com.example.pictures.features.view.EditPicturePage.EditPictureScreen
import com.example.pictures.features.view.EditPicturePage.EditPictureScreenNavDestination
import com.example.pictures.features.view.PicturePage.PictureScreen
import com.example.pictures.features.view.PicturePage.PictureScreenNavDestination

@Composable
fun PictureNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = HomeScreenNavDestination.route){
        composable(route = HomeScreenNavDestination.route) {
            HomeScreen(
                navToAddPictureScreen = {
                    navController.navigate(AddPictureScreenNavDestination.route)
                },
                navToPictureScreen = {
                    navController.navigate("${PictureScreenNavDestination.route}/$it")
                }
            )
        }
        composable(route = PictureScreenNavDestination.routeWithArgs,
            arguments = listOf(navArgument(PictureScreenNavDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            PictureScreen(
                navToEditPictureScreen = {
                    navController.navigate("${EditPictureScreenNavDestination.route}/$it")
                },
                navToHomeScreen = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = AddPictureScreenNavDestination.route) {
            AddPictureScreen(
                navToHomeScreen = {
                    navController.navigate(HomeScreenNavDestination.route)
                }
            )
        }
        composable(route = AddPictureScreenNavDestination.routeWithArgs,
            arguments = listOf(navArgument(AddPictureScreenNavDestination.itemUriArg) {
                type = NavType.StringType
            })) {
            AddPictureScreen(
                navToHomeScreen = {
                    navController.navigate(HomeScreenNavDestination.route)
                }
            )
        }
        composable(route = EditPictureScreenNavDestination.routeWithArgs,
            arguments = listOf(navArgument(EditPictureScreenNavDestination.itemIdArg) {
                type = NavType.IntType
            })) {
            EditPictureScreen(
                navToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }

    }
}