# Metropolitan Museum Of Art - Android Clean MVVM architecture

An Android app to browse through the gallery of the Metropolitan Museum of Art (MET) using their public API.

The solution is designed using the latest tools and libraries, following the MVVM, Clean Architecture, and Clean Code principles. The structure is multi-module, with the following key features:

* Written in Kotlin
* Uses MVVM Architecture, Clean Architecture, and Clean Code
* Room for data persistence and Retrofit for network requests
* Kotlin Coroutines for threading
* Hilt for dependency injection
* Material Design Library for UI design

### The basic flow looks like this :

<p align="center">
 <img src='https://github.com/davidramezani/MetropolitanMuseumOfArt/blob/main/images/data_flow.png' width='500'>
</p>

The Repository layer is responsible for managing data operations, including data storage and retrieval from multiple sources. It abstracts the data sources from the rest of the app, ensuring clean separation of concerns.

The domain layer acts as a bridge between the data and presentation layers, executing business logic in a separate thread from the UI. This helps to maintain a clean separation of concerns, improves performance, and ensures a smooth user experience.

### The following technologies are used in the project:
* Retrofit
* Glide
* Room
* Navigation Component
* Kotlin Coroutines
* Kotlin Flow
* ViewModel
* Hilt
* MVVM

## Future Work:
* Add UI Tests
* Add Other types of Exception For Network & Database Errors
* Display proper error messages related to exceptions
* Add filter Items for search API for a better result
