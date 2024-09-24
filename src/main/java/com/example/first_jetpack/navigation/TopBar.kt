package com.example.first_jetpack.navigation


import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import com.example.first_jetpack.R

@Composable
fun TopBar(
    title: String,
    onMenuClick: () -> Unit
){
    TopAppBar(
        title = {
            Text(text = title, fontSize = 20.sp)
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu")
            }
        },
        elevation = 4.dp
    )
}
