package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ft_hangouts.ui.navigation.AppNavigation
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ft_hangoutsTheme {
                AppNavigation()
            }
        }
    }
}
