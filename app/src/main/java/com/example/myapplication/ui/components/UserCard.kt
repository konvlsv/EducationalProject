package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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

@Composable
fun UserCard(
    userModel: UserModel,
    onStatusChange: () -> Unit,
    onDelete: () -> Unit,
    onCardClick: (UserModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onCardClick(userModel)},
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Верхняя часть с инфо
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.outline_accessibility_24),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column{
                    Text(
                        text = "${userModel.firstName} ${userModel.lastName}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = if (userModel.isOnline) "В сети" else "Не в сети",
                        color = if (userModel.isOnline) Color(0xFF4CAF50) else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Кнопки управления
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onStatusChange,
                    modifier = Modifier.weight(1f) // Занимает половину места
                ) {
                    Text("Статус", maxLines = 1)
                }

                // Используем OutlinedButton для удаления, чтобы визуально кнопки отличались
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f) // Занимает вторую половину
                ) {
                    Text("Удалить", color = Color.Red)
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserCardPreview() {
    MyApplicationTheme() {
        val previewUserModel = getUsersList().first()
        UserCard(
            userModel = previewUserModel,
            onStatusChange = {},
            onCardClick = {},
            onDelete = {},
        )
    }
}