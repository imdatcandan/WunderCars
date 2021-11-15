How did I approach the task?

As a challenge it was not so difficult to implement an architecture but as UX was quite detailed
that's why I prefer to implement compose UI for better performance when we compare data binding and so on

What architecture-layers did I create and why?

I follow Android architecture guideline that's why I used MVVM and some jetpack components
because it is easy to apply separation of concern and no need to have view reference like MVP that helps me for memory usage

I prefer divide the layers below to apply separation of concern and single responsibility principle to create a clean MVVM architecture like readable, testable and maintainable
Data -> RepositoryImpl, Repository Interface, ApiModels(Nullable Network Data)
Domain -> Usecase, DomainModels(Nonnull Domain Data) and DomainModelMapper
Presentation -> Views and ViewModel, UiModels(Nonnull processed UI Data) and UiModelMapper, UIState -> Success, Error, Loading

I used the tech stack and libraries;
* Fully Kotlin
* MVVM Architecture
* Flow
* Retrofit for network
* Koin for DI
* Coroutines for async operations
* MockK for Unit Tests
* Jetpack Compose for UI
* Sealed class for Resource
* ModelMapper to convert ApiModel to DomainModel and DomainModel to UiModel
* Coil for Image loading
* Basic Error Handling
* Navigation architecture component to go to ImagePreviewScreen

What would I do if I had more time?

Better UI implementation
More detailed unit tests for each single unit
Basic UI test
