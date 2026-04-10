package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AddUserDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit,
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    // Вычисляем валидность напрямую (без remember), чтобы она обновлялась при каждом вводе символа
    // Теперь ошибка (isError) будет показываться, только если пользователь начал писать, но стер или не дописал
    val isFirstNameError = firstName.isNotEmpty() && firstName.length < 3
    val isLastNameError = lastName.isNotEmpty() && lastName.length < 3

    // Кнопка активна, если оба поля заполнены корректно
    val isFormValid = firstName.length >= 3 && lastName.length >= 3

    // Создаем объект для управления фокусом
    val focusRequester = remember { FocusRequester() }

    // Этот блок сработает один раз при запуске диалога
    LaunchedEffect(Unit) {
        focusRequester.requestFocus() // Просим фокус
    }

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
                    isError = isFirstNameError,
                    supportingText = {
                        if (isFirstNameError) {
                            Text("Имя должно содержать не менее 3 символов")
                        }
                    },
                    label = { Text("Имя") },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
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
                    isError = isLastNameError,
                    supportingText = {
                        if (isLastNameError) {
                            Text("Фамилия должна содержать не менее 3 символов")
                        }
                    },
                    label = { Text("Фамилия") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        if (lastName.isNotEmpty()) {
                            IconButton(onClick = { lastName = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Очистить")
                            }
                        }
                    },
                    // Настройка клавиатуры
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isFormValid) onConfirmation(firstName.trim(), lastName.trim())
                        }
                    )
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(firstName.trim(), lastName.trim())
                },
                enabled = isFormValid
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