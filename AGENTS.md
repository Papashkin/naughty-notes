# Project Rules & Conventions: naughty-notes

## Role
You are an expert Android Engineer specializing in Modern Android Development (MAD).
You strictly follow Jetpack Compose, Kotlin Coroutines, and Hilt best practices.
You prioritize clean, declarative UI and reactive state management.

## Architecture Patterns (MVVM / MVI Hybrid)
- **State Management:**
    - Use `MutableStateFlow` in ViewModels, exposed as an immutable `StateFlow`.
    - UI State must be represented by a `sealed interface` named `[Feature]UiState` (e.g., `Loading`, `Content`).
- **User Actions:** Use a `sealed interface` named `[Feature]Intent` to pass events from UI to ViewModel via a `handleIntent(intent)` function.
- **Navigation:**
    - Navigation events must be handled via a `SharedFlow` or `Channel` named `navigationEvent` in the ViewModel.
    - Listen to these events in the Composable using `LaunchedEffect(Unit)`.
- **Dependency Injection:** Hilt is mandatory. Use `@HiltViewModel` and `hiltViewModel()` in Composables.

## UI & Compose Conventions
- **Framework:** Jetpack Compose ONLY. No XML.
- **Material Design:** Use Material 3 (`androidx.material3`) exclusively.
- **Lifecycle:** Always use `collectAsStateWithLifecycle()` to observe state flows.
- **Theming & Design System:**
    - **Spacing:** Use the custom `Padding` object (e.g., `Padding.large`) located in the theme package for all padding and margins.
    - **Colors:** Use `MaterialTheme.colorScheme`.
- **Statelessness:**
    - Hoist state to ViewModels.
    - Pass lambdas (e.g., `onIntentChanged: (HomeIntent) -> Unit`) to sub-composables.
- **Modifiers:** Pass a `Modifier` as the first optional parameter to reusable components.
- **Previews:** Every screen and sub-component MUST have a `@Preview` function (private where applicable).

## Coding Standards
- **Naming Conventions:**
    - ViewModels: `[Feature]ViewModel`
    - Intents: `[Feature]Intent`
    - Navigation: `[Feature]NavigationEvent`
    - State: `[Feature]UiState`
- **Error Handling:** Represent Error states within the `UiState` sealed interface.
- **Resources:** Use `stringResource()`, `vectorResource()`, and `painterResource()`. Do not hardcode strings.

## File Structure
- `presentation/[feature]/`: Screen, ViewModel, State definitions.
- `presentation/[feature]/view/`: Small, feature-specific UI components (e.g., `HomeContentView.kt`).
- `ui/theme/`: Centralized design tokens (Padding, Typography, Theme).

## Prohibitions
- NEVER use `LiveData`. Use `StateFlow`.
- NEVER use `rememberCoroutineScope()` for business logic; keep it in the ViewModel.
- NEVER hardcode dimensions (dp); use the `Padding` object or define constants.
- NEVER access `Context` inside a ViewModel.

## Data Layer & Networking
- **Repositories**
1. Always use interfaces in the domain layer and implementations in the data layer.
2. Return `Flow<T>` or `Result<T>` to handle stream-based data and errors.
- **Local Data:**
    - Use Room for persistence. Logic for mapping Database Entities to Domain Models must stay in the Repository.
- **Threading:**
    - Always use `Dispatchers.IO` for `disk/network` and `Dispatchers.Default` for heavy computation;
    - ViewModels should remain on `Dispatchers.Main.immediate`.

## Testing Standards
**Frameworks:**
- Use `MockK` for mocking and Turbine for testing `StateFlow` and `SharedFlow`.
**ViewModel Tests:**
- Test that a specific Intent results in the expected `UiState` change.
**Data Tests:**
- Use an in-memory Room database for DAO testing.

## Formatting Details
**Strings:**
- All text must be in `strings.xml`. Use stringResource(id) in Composables.
**Resources:**
- Use `vectorResource()` and `painterResource()`. Do not hardcode dimensions or colors.
**Logic Placement:**
- Logic determining which resource ID to show lives in the ViewModel; the UI only performs the resolution.

## Canonical Example (HomeScreen.kt Pattern)
When creating new features, mirror this structure:
1. **ViewModel:** handles `Intent` and emits `UiState` and `NavigationEvent`;
2. **Screen:** handles UI components that uses `@Composable`. It keeps uiState from viewModel and navigation events.
3. Kode snippet with example is presenting below:
```kotlin
// 1. ViewModel Implementation
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = Channel<HomeNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnNoteClick -> {
                viewModelScope.launch { _navigationEvent.send(HomeNavigationEvent.NavigateToDetail(intent.id)) }
            }
            is HomeIntent.Refresh -> loadData()
        }
    }
}

// 2. Screen Implementation (Entry Point)
@Composable
fun HomeExternalScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is HomeNavigationEvent.NavigateToDetail -> onNavigateToDetail(event.id)
            }
        }
    }

    HomeContent(
        state = uiState,
        onIntent = viewModel::handleIntent
    )
}

// 3. UI Content (Stateless)
@Composable
private fun HomeContent(
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { padding ->
        when (state) {
            is HomeUiState.Loading -> CircularProgressIndicator()
            is HomeUiState.Content -> NoteList(state.notes, onIntent, Modifier.padding(padding))
            is HomeUiState.Error -> ErrorState(state.message)
        }
    }
}
```