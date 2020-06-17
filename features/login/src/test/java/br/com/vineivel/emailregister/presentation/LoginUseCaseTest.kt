package br.com.vineivel.emailregister.presentation

import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    private fun <T> anyObject() : T = Mockito.any<T>()

    @Mock
    lateinit var repository: LoginRepository

    lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        initMocks(this)
        useCase = LoginUseCase(repository)
    }

    @After
    fun tearDown() {
    }


    @Test
    fun teste() {
        //*** Given ***//
        val userName = "teste"
        val password = "123"

        val response = LoginRepository.Response(
            data = "teste",
            isSuccessful = false,
            code= 500,
            errorMessage ="Houve um erro"
        )

        val userBo = UserBo(
            userName, password
        )

        given(repository.loginRequest(anyObject())).willReturn(response)

        //*** When ***//
        useCase.login(userBo)

        //*** Then ***//
        argumentCaptor<UserBo>().apply {
            Mockito.verify(repository, Mockito.times(1)).loginRequest(capture())
            val userBoCapture = firstValue
            Assert.assertEquals(userBoCapture.userName, "teste")
            Assert.assertEquals(userBoCapture.password, "123")
        }
    }

    @Test
    fun login() {

        //*** Given ***//
        val userName = "teste"
        val password = "123"

        val response = LoginRepository.Response(
            data = "teste",
            isSuccessful = false,
            code= 500,
            errorMessage ="Houve um erro"
        )

        val userBo = UserBo(
            userName, password
        )

        given(repository.loginRequest(anyObject())).willReturn(response)

        //*** When ***//
        val resultState = useCase.login(userBo)

        //*** Then ***//
        Assert.assertEquals(resultState, LoginUseCase.LoginResult.ServerError("Houve um erro"))
    }
}