package com.example.androidlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.androidlab.navigation.AppNavGraph
import com.example.androidlab.ui.theme.AdoptTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdoptTheme {
                val nav = rememberNavController()
                AppNavGraph(navController = nav)
            }
        }
    }
}
// 모바일 로그인용
//sksekrhfo123@naver.com
//1234
