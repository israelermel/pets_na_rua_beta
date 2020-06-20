package br.com.vineivel.domain.model

data class User(
    var id: String? = "",
    val name: String = "",
    val email: String? = "",
    var profilePicture: String? = ""
)