package br.com.vineivel.login.presentation

class LoginRepository {

    data class Response(
        val data: String = "",
        val isSuccessful: Boolean = false,
        val code: Int = 0,
        val errorMessage : String
    )

    fun loginRequest(userBo: UserBo): Response {
        return Response("teste", false, 500, "Houve um erro")

    }



}