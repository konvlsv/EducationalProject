package com.example.myapplication.utils

import com.example.myapplication.ui.models.UserModel

fun getMockUsersList(): List<UserModel> {
    return listOf(
        UserModel(1, "John", "Doe", true),
        UserModel(2, "Jane", "Smith", false),
        UserModel(3, "Bob", "Johnson", true),
        UserModel(4, "Alice", "Williams", false),
        UserModel(5, "Charlie", "Brown", true),
        UserModel(6, "Eva", "Davis", false),
        UserModel(7, "David", "Miller", true),
        UserModel(8, "Grace", "Wilson", false),
        UserModel(9, "Frank", "Taylor", true),
        UserModel(10, "Helen", "Anderson", false),
        UserModel(11, "Ivan", "Martinez", true),
        UserModel(12, "Julia", "Garcia", false),
        UserModel(13, "Kevin", "Lopez", true),
        UserModel(14, "Mia", "Hernandez", false),
        UserModel(15, "Oliver", "Gonzalez", true),
        UserModel(16, "Sophia", "Perez", false),
    )
}