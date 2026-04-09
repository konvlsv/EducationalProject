package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun UserSearchTextField(
    searchText: String,
    onValueChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = searchText,
        onValueChange = { onValueChange(it) },
        label = { Text("Поиск контактов") },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        // Добавляем иконку лупы из стандартного набора (чтобы не зависеть от твоих drawable)
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onDeleteClick() }) {
                    Icon(Icons.Default.Clear, contentDescription = "Очистить")
                }
            }
        },
        // Настройка клавиатуры
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserSearchTextFieldPreview() {
    MyApplicationTheme() {
        UserSearchTextField(
            searchText = "Jo",
            onValueChange = {},
            onDeleteClick = {},
            modifier = Modifier
        )
    }
}