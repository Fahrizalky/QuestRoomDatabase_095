package com.example.myaactv7.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myaactv7.ui.view.mahasiswa.DestinasiInsert
import com.example.myaactv7.ui.view.mahasiswa.InsertMhsView
import com.example.myaactv7.viewmodel.DetailMhsView
import com.example.myaactv7.viewmodel.HomeMhsView
import com.example.myaactv7.viewmodel.UpdateMhsView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route) {
        composable(
            route = DestinasiHome.route
        ){
            HomeMhsView(
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println(
                        "PengeloaHalaman: nim = $nim"
                    )
                },
                onAddMhs = {
                    navController.navigate(DestinasiInsert.route)

                },
                modifier = modifier
            )
        }

        composable(
            route =  DestinasiInsert.route
        ){
            InsertMhsView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM){
                    type = NavType
                }
            )
        ){
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailMhsView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}$it")

                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.NIM) {
                    type = NavType.StringType
                }
            )
        ){
            UpdateMhsView(
                onBack = {
                    navController.popBackStack()
                },
                OnNavigation = {
                    navController.popBackStack()
                },
                modifier = Modifier,
            )
        }
    }
}