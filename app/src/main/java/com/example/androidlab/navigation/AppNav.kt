package com.example.androidlab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidlab.ui.feature.address.AddressSearchScreen
import com.example.androidlab.ui.feature.auth.login.LoginScreen
import com.example.androidlab.ui.feature.auth.signup.SignUpScreen
import com.example.androidlab.ui.feature.home.HomeScreen

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "signUp"
    const val HOME = "home"
    const val ADDR_SEARCH = "addrSearch"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login" // 기존 값 유지
    ) {
        composable("login") {
            LoginScreen(
                onSignUp = { navController.navigate("signup") },
                onForgotPassword = {
                    // TODO: 비번 찾기 화면 열기 (없으면 일단 no-op)
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true } // 로그인 화면 제거
                        launchSingleTop = true
                    }
                }
            )
        }

        // 나머지 그래프는 기존 그대로
        composable("signup") {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onClickAddressSearch = {
                    navController.navigate(Routes.ADDR_SEARCH)
                },
                navController = navController
            )
        }
        composable("home") { HomeScreen() }
        
        composable(Routes.ADDR_SEARCH) {
            AddressSearchScreen(
                onBack = { navController.popBackStack() },
                onSelect = { picked ->
                    // 선택 결과 이전 화면으로 전달
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("picked_addr", picked)
                    navController.popBackStack()
                }
            )
        }
    }
}