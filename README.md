# Android Clean MVVM architecture


The solution is built in a multi-module structure and attempts to use the latest tools and libraries. In
a summary:

* Entirely written in Kotlin.
* Uses MVVM Architecture, Clean Architecture, Clean Code.
* Uses Room as data persistence, Retrofit for network request handling.
* Uses Kotlin Coroutines throughout threading.
* Uses Hilt for dependency injection
* Uses Material design Library

### The basic flow looks like this :

<p align="center">
 <img src='https://user-images.githubusercontent.com/45559398/172233712-a350738b-453d-415d-a9e2-71838dad82d5.png' width='500'>
</p>

The Repository layer handles data operations. The app's data comes from a few different sources -
data is stored (either remotely or in a local cache for offline use), and the repository modules are
responsible for handling all data operations and abstracting the data sources from the rest of the
app.

The app uses an offline first algorithm. If online data can't be requested, but offline data is
available, use the offline data.

A lightweight domain layer sits between the data layer and the presentation layer and handles
discrete pieces of business logic off the UI thread.
### Technologies which i used in this project:
* Retrofit
* Glide
* Room
* Navigation Component
* Kotlin Coroutines
* Kotlin Flow
* ViewModel
* Hilt
* MVVM

## Sum up

For the purpose of providing an overview of my solution, I have only included general information in
this document
