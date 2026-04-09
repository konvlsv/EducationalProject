package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.components.EmptySearchResult
import com.example.myapplication.ui.components.UserDeleteDialog
import com.example.myapplication.ui.components.UserSearchTextField
import com.example.myapplication.ui.components.UsersLazyColumn
import com.example.myapplication.ui.models.UserModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodels.UserViewModel
import com.example.myapplication.utils.getUsersList

// 1. Эта функция для связи с логикой (ViewModel)
@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel()
) {
    // 1. Если во ViewModel есть юзер на удаление — показываем диалог
    viewModel.userToDelete?.let { user ->
        UserDeleteDialog(
            name = user.firstName,
            onDismissRequest = { viewModel.onDismissDialog() },
            onConfirmation = {
                viewModel.deleteUser(user)
                viewModel.onDismissDialog()
            },
        )
    }
    // Вызываем "чистую" функцию и передаем в неё данные из ViewModel
    UserListContent(
        searchText = viewModel.searchText,
        filteredList = viewModel.filteredList,
        onSearchChange = { viewModel.onSearchChange(it) },
        onStatusChange = { viewModel.toggleStatus(it) },
        // Теперь здесь просто передаем юзера во ViewModel
        onDelete = { user -> viewModel.onDeleteClick(user) },
        onCardClick = { user -> viewModel.onCardClick(user) },
        modifier = modifier
    )
}

// 2. Эта функция только для отображения (её легко показать в Preview)
@Composable
fun UserListContent(
    searchText: String,
    filteredList: List<UserModel>,
    onSearchChange: (String) -> Unit,
    onStatusChange: (UserModel) -> Unit,
    onDelete: (UserModel) -> Unit,
    onCardClick: (UserModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        UserSearchTextField(
            searchText = searchText,
            onValueChange = onSearchChange,
            onDeleteClick = { onSearchChange("") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        if (filteredList.isEmpty()) {
            EmptySearchResult()
        } else {
            UsersLazyColumn(
                list = filteredList,
                onStatusChange = onStatusChange,
                onDelete = onDelete,
                onCardClick = { onCardClick(it) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

// 3. Теперь Preview работает идеально!
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserListContentPreview() {
    MyApplicationTheme {
        UserListContent(
            searchText = "",
            filteredList = getUsersList(), // Берем тестовые данные
            onSearchChange = {},
            onStatusChange = {},
            onDelete = {},
            onCardClick = {},
        )
    }
}