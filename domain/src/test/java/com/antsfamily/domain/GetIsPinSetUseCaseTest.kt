package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIsPinSetUseCaseTest {

    private val repository: SettingsRepository = Mockito.mock(SettingsRepository::class.java)

    private lateinit var getIsPinSetUseCase: GetIsPinSetUseCase

    @Before
    fun setUp() {
        getIsPinSetUseCase = GetIsPinSetUseCase(repository)
    }

    @Test
    fun `get moves success`() = runTest {
        Mockito
            .`when`(repository.isPinCodeSet())
            .thenReturn(false)

        val result = getIsPinSetUseCase()

        assert(result is UseCaseResult.Success)
    }

    @Test
    fun `get moves failure`() = runTest {
        Mockito
            .`when`(repository.isPinCodeSet())
            .thenThrow(RuntimeException("error occurred"))

        val result = getIsPinSetUseCase()

        assert(result is UseCaseResult.Error)
    }
}