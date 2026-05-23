package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.NoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import java.time.Month

@RunWith(MockitoJUnitRunner::class)
class GetNoteByIdUseCaseTest {

    private val repository: NoteRepository = Mockito.mock(NoteRepository::class.java)

    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setUp() {
        getNoteByIdUseCase = GetNoteByIdUseCase(repository)
    }

    @Test
    fun `get moves success`() = runTest {
        Mockito
            .`when`(repository.getNoteById(id = Mockito.anyInt()))
            .thenReturn(USE_CASE_SUCCESS_NOTE)

        val result = getNoteByIdUseCase(3643)

        assert(result is UseCaseResult.Success)
    }

    @Test
    fun `get moves failure`() = runTest {
        Mockito
            .`when`(repository.getNoteById(id = Mockito.anyInt()))
            .thenThrow(RuntimeException("error occurred"))

        val result = getNoteByIdUseCase(3643)

        assert(result is UseCaseResult.Error)
    }

    companion object {
        private val USE_CASE_SUCCESS_NOTE = NoteModel(
            3643,
            LocalDate.of(2025, Month.JULY, 12),
            listOf(PracticeType.ANAL),
            PracticeLocation.CAR,
            isProtected = true,
            hasOrgasm = false,
            hasPartnerOrgasm = false,
            experienceRate = 0.5f,
            personalNote = ""
        )
    }
}