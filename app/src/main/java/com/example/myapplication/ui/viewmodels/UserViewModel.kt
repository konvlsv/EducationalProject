package com.example.myapplication.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.ui.models.UserModel
import com.example.myapplication.utils.getMockUsersList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private var _fullList: List<UserModel> by mutableStateOf(listOf())

    // Текст поиска
    var searchText by mutableStateOf("")
        private set

    val filteredList by derivedStateOf {
        val query = searchText.trim()
        _fullList.filter {
            it.firstName.contains(query, ignoreCase = true) ||
                    it.lastName.contains(query, ignoreCase = true)
        }.sortedWith(
            compareBy(
                { !it.isOnline }, // Сначала те, кто Online (true станет false и уйдет вверх)
                { it.firstName.lowercase() },  // Затем по имени
                { it.lastName.lowercase() }  // Затем по фамилии
            )
        )
    }

    var userToDelete by mutableStateOf<UserModel?>(null)
        private set

    var selectedUser by mutableStateOf<UserModel?>(null)
        private set

    var isAddingUser by mutableStateOf(false)
        private set

    // 1. Инициализация (загрузка данных при старте)
    init {
        loadUsers()
    }

    private fun loadUsers() {
        try {
            val json = sharedPref.getString("users_list", null)
            if (json != null) {
                val type = object : TypeToken<List<UserModel>>() {}.type
                _fullList = gson.fromJson(json, type)
            } else {
                _fullList = getMockUsersList()
                saveUsers()
            }
        } catch (e: Exception) {
            // Если данные повредились, загружаем дефолтный список
            _fullList = getMockUsersList()
            saveUsers()
        }
    }

    // 2. Метод сохранения (вызывай его при каждом изменении)
    private fun saveUsers() {
        val json = gson.toJson(_fullList)
        sharedPref.edit {
            putString("users_list", json)
        }
    }

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
        saveUsers()
    }

    fun deleteUser(user: UserModel) {
        _fullList = _fullList.filter { it.id != user.id }
        userToDelete = null // На всякий случай зануляем здесь тоже
        saveUsers()
    }

    fun addUser(firstName: String, lastName: String) {
        _fullList = _fullList + UserModel(
            id = _fullList.maxOfOrNull { it.id }?.plus(1) ?: 1,
            firstName = firstName,
            lastName = lastName,
            isOnline = false
        )
        isAddingUser = false
        saveUsers()
    }

    fun onShowAddUserDialog() {
        isAddingUser = true
    }

    fun onDismissAddUserDialog() {
        isAddingUser = false
    }
}