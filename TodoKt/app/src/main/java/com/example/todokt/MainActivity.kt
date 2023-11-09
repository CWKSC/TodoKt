package com.example.todokt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todokt.ui.mainpage.MainPage
import com.example.todokt.ui.theme.TodoKtTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoKtTheme {
                // A surface container using the 'background' color from the theme
                MainPage()
            }
        }
    }
}

