package com.example.shoe_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoe_shop.ui.screens.RegisterAccountScreen
import com.example.shoe_shop.ui.theme.Shoe_ShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Shoe_ShopTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterAccountScreen()
                }
            }
        }
    }
}