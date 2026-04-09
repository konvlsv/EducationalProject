package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.models.UserModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.getUsersList

@Composable
fun UsersLazyColumn(
    list: List<UserModel>,
    onStatusChange: (UserModel) -> Unit,
    onDelete: (UserModel) -> Unit,
    onCardClick: (UserModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = list,
            key = { it.id } // Использование ID — идеальное решение!
        ) { user ->
            UserCard(
                userModel = user,
                onStatusChange = {
                    onStatusChange(user)
                },
                onDelete = {
                    onDelete(user)
                },
                onCardClick = {
                    onCardClick(user)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UsersLazyColumnPreview(){
    MyApplicationTheme() {
        UsersLazyColumn(
            list = getUsersList(),
            onStatusChange = {},
            onDelete = {},
            onCardClick = {},
        )
    }
}