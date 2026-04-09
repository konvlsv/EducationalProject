package com.example.myapplication.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.models.UserModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.getUsersList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    user: UserModel,
    onBackButtonClick: () -> Unit,
    onStatusChange: (UserModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler { onBackButtonClick() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Авто-отступы между элементами
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Сделаем иконку побольше
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.outline_accessibility_24),
                    contentDescription = null,
                    modifier = Modifier.padding(24.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = user.firstName, style = MaterialTheme.typography.headlineLarge)
                Text(text = user.lastName, style = MaterialTheme.typography.headlineMedium, color = Color.Gray)
            }

            Card(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = if (user.isOnline) "В сети" else "Не в сети",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    color = if (user.isOnline) Color(0xFF4CAF50) else Color.Gray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Button(
                onClick = { onStatusChange(user) },
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text("Изменить статус")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserDetailScreenPreview() {
    val user = getUsersList().first()
    MyApplicationTheme(){
        UserDetailScreen(
            user = user,
            onBackButtonClick = {},
            onStatusChange = {},
        )
    }
}