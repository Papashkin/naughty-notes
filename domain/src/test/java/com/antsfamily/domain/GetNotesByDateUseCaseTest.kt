package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.repository.NoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertFailsWith

@RunWith(MockitoJUnitRunner::class)
class GetNotesByDateUseCaseTest {

    private val repository: NoteRepository = Mockito.mock(NoteRepository::class.java)

    private lateinit var getNotesByDateUseCase: GetNotesByDateUseCase

    @Before
    fun setUp() {
        getNotesByDateUseCase = GetNotesByDateUseCase(repository)
    }

    @Test
    fun `get moves success`() = runTest {
        val date = LocalDate.now()
        Mockito
            .`when`(repository.getNotesByDate(date))
            .thenReturn(USE_CASE_SUCCESS_NOTES)
        val result = getNotesByDateUseCase.invoke(date)

        assert(result.size == 2)
    }

    @Test
    fun `get moves failure`() = runTest {
        val date = LocalDate.now()
        Mockito
            .`when`(repository.getNotesByDate(date))
            .thenThrow(RuntimeException("error occurred"))

        assertFailsWith<RuntimeException> {
            getNotesByDateUseCase.invoke(date)
        }
    }

    companion object {
        private val USE_CASE_SUCCESS_NOTES =listOf(
            NoteModel(
                3643,
                LocalDate.of(2025, Month.JULY, 12),
                PracticeType.ANAL,
                PracticeLocation.CAR,
                isProtected = true,
                hasOrgasm = false,
                hasPartnerOrgasm = false,
                painRate = 2,
                rate = 4,
                personalNote = ""
            ),
            NoteModel(
                2452,
                LocalDate.of(2025, Month.JULY, 22),
                PracticeType.VAGINAL,
                PracticeLocation.SHOWER,
                isProtected = true,
                hasOrgasm = false,
                hasPartnerOrgasm = true,
                painRate = 2,
                rate = 4,
                personalNote = "That was something crazy"
            )
        )
    }
}