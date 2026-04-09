package com.example.myapplication.ui.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.models.UserModel
import com.example.myapplication.utils.getUsersList

class UserViewModel : ViewModel() {
    private var _fullList by mutableStateOf(getUsersList())

    // Текст поиска
    var searchText by mutableStateOf("")
        private set

    val filteredList by derivedStateOf {
        _fullList.filter {
            it.firstName.contains(searchText.trim(), ignoreCase = true) ||
                    it.lastName.contains(searchText.trim(), ignoreCase = true)
        }.sortedWith(
            compareBy(
                { !it.isOnline }, // Сначала те, кто Online (true станет false и уйдет вверх)
                { it.firstName },  // Затем по имени
                { it.lastName }  // Затем по фамилии
            )
        )
    }

    var userToDelete by mutableStateOf<UserModel?>(null)
        private set

    var selectedUser by mutableStateOf<UserModel?>(null)
        private set

    fun onCardClick(user: UserModel) {
        selectedUser = user
    }

    fun onUserDetailScreenBackClick() {
        selectedUser = null
    }


    fun onDeleteClick(user: UserModel) {
        userToDelete = user
    }

    fun onDismissDialog() {
        userToDelete = null
    }

    fun onSearchChange(newText: String) {
        searchText = newText
    }

    fun toggleStatus(user: UserModel) {
        // 1. Обновляем основной список
        _fullList = _fullList.map {
            if (it.id == user.id) it.copy(isOnline = !it.isOnline) else it
        }

        // 2. ВАЖНО: Обновляем выбранного пользователя, чтобы экран деталей перерисовывался
        if (selectedUser?.id == user.id) {
            selectedUser = _fullList.find { it.id == user.id }
        }
    }

    fun deleteUser(user: UserModel) {
        _fullList = _fullList.filter { it.id != user.id }
        userToDelete = null // На всякий случай зануляем здесь тоже
    }

    fun addUser(firstName: String, lastName: String) {
        _fullList = _fullList + UserModel(
            id = _fullList.maxOfOrNull { it.id }?.plus(1) ?: 1,
            firstName = firstName,
            lastName = lastName,
            isOnline = false
        )
    }
}