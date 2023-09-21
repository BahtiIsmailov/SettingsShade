package com.example.customstatusbar.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text

@Composable
fun Screen1() {
    Box(modifier = Modifier
        .fillMaxSize() , contentAlignment = Alignment.Center
    ) {
        Text(text = "Экран номер 1", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Screen2() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Экран номер 2", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Screen3() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Экран номер 3", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Screen4() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Экран номер 4", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}
