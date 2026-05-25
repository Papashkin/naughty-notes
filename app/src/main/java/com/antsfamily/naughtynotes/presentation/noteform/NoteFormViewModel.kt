package com.antsfamily.naughtynotes.presentation.noteform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetNoteByIdUseCase
import com.antsfamily.domain.SaveOrUpdateNoteUseCase
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.model.toType
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceRate
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
import com.antsfamily.naughtynotes.presentation.noteform.model.LocationChipGridState
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeLocationItem
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeTypeItem
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.NoteChip
import com.antsfamily.naughtynotes.presentation.util.CREATE_NOTE_NOTE_LENGTH_MAX
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel(assistedFactory = NoteFormViewModel.Factory::class)
class NoteFormViewModel @AssistedInject constructor(
    private val saveOrUpdateNoteUseCase: SaveOrUpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    @Assisted("dateEpoch") private val dateEpoch: Long,
    @Assisted("noteId") private val noteId: Int?,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("dateEpoch") dateEpoch: Long,
            @Assisted("noteId") noteId: Int?
        ): NoteFormViewModel
    }

    private val _state: MutableStateFlow<NoteFormUiState> =
        MutableStateFlow(NoteFormUiState.Loading)
    val state: StateFlow<NoteFormUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

    private val _noteSaveSnackBarEvent: MutableSharedFlow<NoteFormType> = MutableSharedFlow()
    val noteSaveSnackBarEvent: SharedFlow<NoteFormType> = _noteSaveSnackBarEvent

    private val selectedDate: LocalDate by lazy { LocalDate.ofEpochDay(dateEpoch) }

    init {
        if (noteId == null) {
            setCreateNoteDefaultState()
        } else {
            setupEditNoteContent(noteId)
        }
    }

    fun onIntent(intent: NoteFormIntent) {
        when (intent) {
            is NoteFormIntent.SetNote -> setNote(intent.note)
            is NoteFormIntent.SetNoteChipSelectionChanged -> setChipSelected(
                intent.type,
                intent.isSelected
            )

            is NoteFormIntent.SetPracticeLocation -> setPracticeLocation(intent.location, intent.isSelected)
            is NoteFormIntent.SetLocationGridState -> setPracticeLocationState(intent.state)
            is NoteFormIntent.SetPracticeType -> setPracticeType(intent.type, intent.isSelected)
            is NoteFormIntent.SaveButtonClick -> onSaveButtonClick()
            is NoteFormIntent.SetExperienceRate -> setExperienceRate(intent.rate)
        }
    }

    private fun setupEditNoteContent(noteId: Int) = viewModelScope.launch {
        val result = getNoteByIdUseCase(noteId)
        when (result) {
            is UseCaseResult.Success -> handleSuccessNoteResult(result.data)
            is UseCaseResult.Error -> handleErrorPinResult(result.exception)
        }
    }

    private fun handleSuccessNoteResult(note: NoteModel?) {
        note?.let {
            val experienceType = ExperienceType
                .entries
                .first { type -> it.experienceRate in type.minValue..type.maxValue }

            val experienceRate = ExperienceRate(experienceType, it.experienceRate)

            val types = PracticeType.entries.map { type ->
                PracticeTypeItem(type, type in it.types)
            }

            val locations = PracticeLocation.entries.map { location ->
                PracticeLocationItem(location, location == it.location)
            }

            _state.value = NoteFormUiState.Content(
                formType = NoteFormType.EDIT,
                date = selectedDate,
                types = types,
                locationGridState = LocationChipGridState.COLLAPSED,
                locations = locations,
                experienceRate = experienceRate,
                note = it.personalNote,
                isSaveButtonEnabled = true,
                isSaveButtonLoadingVisible = false,
                chips = getNoteChips(
                    isProtected = it.isProtected,
                    hasOrgasm = it.hasOrgasm,
                    hasPartnerOrgasm = it.hasPartnerOrgasm,
                )
            )
        }
    }

    private fun handleErrorPinResult(e: Exception) {
        _state.value = NoteFormUiState.Error(e.toType())
    }

    fun setExperienceRate(rate: Float) {
        val experienceType = ExperienceType.entries.first { rate in it.minValue..it.maxValue }
        val experienceRate = ExperienceRate(experienceType, rate)
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(experienceRate = experienceRate)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    private fun setNote(note: String) {
        if (note.length > CREATE_NOTE_NOTE_LENGTH_MAX) return

        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(note = note)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    private fun setPracticeType(type: PracticeType, isSelected: Boolean) {
        _state.update { currentState ->
            if (currentState is NoteFormUiState.Content) {
                val types = currentState.types.map { practice ->
                    if (practice.type == type) practice.copy(isSelected = isSelected) else practice
                }
                currentState.copy(types = types)
            } else {
                currentState
            }
        }
        checkSaveButtonAvailability()
    }

    private fun setPracticeLocation(location: PracticeLocation, isSelected: Boolean) {
        _state.update { currentState ->
            if (currentState is NoteFormUiState.Content) {
                val locations = currentState.locations
                    .map { practice ->
                        if (practice.location == location) {
                            practice.copy(isSelected = isSelected)
                        } else {
                            practice.copy(isSelected = false)
                        }
                    }
                    .sortedByDescending { it.isSelected }
                currentState.copy(locations = locations)
            } else {
                currentState
            }
        }
        checkSaveButtonAvailability()
    }

    private fun setPracticeLocationState(locationState: LocationChipGridState) {
        _state.update { currentState ->
            if (currentState is NoteFormUiState.Content) {
                currentState.copy(locationGridState = locationState)
            } else {
                currentState
            }
        }
        checkSaveButtonAvailability()
    }

    private fun setChipSelected(type: ChipType, isSelected: Boolean) {
        _state.update {
            if (it !is NoteFormUiState.Content) return@update it

            val updatedChips = (_state.value as NoteFormUiState.Content).chips.map { chip ->
                if (chip.type == type) {
                    chip.copy(isSelected = isSelected)
                } else {
                    chip
                }
            }

            it.copy(chips = updatedChips)

        }
        checkSaveButtonAvailability()
    }

    private fun checkSaveButtonAvailability() {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> {
                    val isFormFilledEnough = checkFormFilledEnough(it)
                    it.copy(isSaveButtonEnabled = isFormFilledEnough)
                }
                else -> it
            }
        }
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        (_state.value as? NoteFormUiState.Content)?.let { state ->
            _state.value = state.copy(isSaveButtonLoadingVisible = true)

            val isActivityProtected =
                state.chips.first { it.type == ChipType.PROTECTION }.isSelected
            val hasOrgasm = state.chips.first { it.type == ChipType.ORGASM }.isSelected
            val hasPartnerOrgasm =
                state.chips.first { it.type == ChipType.PARTNER_ORGASM }.isSelected

            val result = saveOrUpdateNoteUseCase(
                id = noteId,
                date = state.date,
                types = state.types.filter { it.isSelected }.map { it.type },
                location = state.locations.first { it.isSelected }.location,
                isProtected = isActivityProtected,
                hasOrgasm = hasOrgasm,
                hasPartnerOrgasm = hasPartnerOrgasm,
                experienceRate = state.experienceRate.value,
                personalNote = state.note,
            )

            when (result) {
                is UseCaseResult.Error -> handleSaveNoteErrorResult(result.exception)
                is UseCaseResult.Success -> handleSaveNoteSuccessResult(state.formType)
            }
        }
    }

    private suspend fun handleSaveNoteSuccessResult(formType: NoteFormType) {
        (_state.value as? NoteFormUiState.Content)?.let { state ->

            _noteSaveSnackBarEvent.emit(formType)

            _state.value = if (noteId == null) {
                NoteFormUiState.Content.Default.copy(date = selectedDate)
                //TODO rework success mechanism to allow back navigation after note's changes are saved
            } else {
                state.copy(isSaveButtonLoadingVisible = false)
            }
        }
    }

    private fun handleSaveNoteErrorResult(e: Exception) {
        _state.value = NoteFormUiState.Error(e.toType())
    }

    private fun setCreateNoteDefaultState() {
        _state.value = NoteFormUiState.Content.Default.copy(date = selectedDate)
    }

    private fun getNoteChips(
        isProtected: Boolean,
        hasOrgasm: Boolean,
        hasPartnerOrgasm: Boolean,
    ): List<NoteChip> =
        ChipType.entries.map { type ->
            NoteChip(
                type = type,
                isSelected = when (type) {
                    ChipType.PROTECTION -> isProtected
                    ChipType.ORGASM -> hasOrgasm
                    ChipType.PARTNER_ORGASM -> hasPartnerOrgasm
                },
            )
        }

    private fun checkFormFilledEnough(state: NoteFormUiState.Content): Boolean {
        val isExperienceRateGood = state.experienceRate.value > 0f
        val isAtLeastOnePracticeSelected = state.types.any { it.isSelected }
        val isLocationAcceptable = state.locations.any { it.isSelected }
        return isExperienceRateGood
                && isAtLeastOnePracticeSelected
                && isLocationAcceptable
    }
}
