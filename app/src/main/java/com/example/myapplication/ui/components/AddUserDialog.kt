package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AddUserDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit,
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text = "Добавить пользователя")
        },
        text = {
            Column {
                TextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    },
                    label = { Text("Имя") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    trailingIcon = {
                        if (firstName.isNotEmpty()) {
                            IconButton(onClick = { firstName = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    },
                    // Настройка клавиатуры
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                TextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    },
                    label = { Text("Фамилия") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    trailingIcon = {
                        if (lastName.isNotEmpty()) {
                            IconButton(onClick = { lastName = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    },
                    // Настройка клавиатуры
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (firstName.isNotBlank() || lastName.isNotBlank()) {
                        onConfirmation(firstName, lastName)
                    }
                }
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddUserDialogPreview() {
    MyApplicationTheme {
        AddUserDialog(
            onDismissRequest = {},
            onConfirmation = { _, _ -> },
        )
    }
}