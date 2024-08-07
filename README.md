# My Local Restaurant Finder

Local Restaurant Finder
Project Description: This application will serve as a platform for users to discover local restaurants based on their current location. It will provide details about the restaurants such as name, address, ratings, and reviews.

Main Features and Functionalities:
- User Authentication: User registration and login functionality is implemented using Firebase Authentication or any other suitable method.
- Restaurant Listing: A list of restaurants based on the user's current location is displayed. This data is fetched from Yelp fusion web service using Retrofit.
- Restaurant Details: When a user selects a restaurant from the list, detailed information about the restaurant is displayed. This includes name, address, ratings, reviews, and images.
- Favorites: Users can mark restaurants as favorites. These favorites should be stored in a local database using Room (SQLite).
- Offline Support: The app can work offline and displays the favorite restaurants even when there is no internet connection.
- Configuration Changes: The app handles configuration changes like screen rotation, language change, etc., without losing any data or state.

Technical Requirements:
- Web Services: Retrofit and Repository pattern is used for network calls.
- MVVM Architecture: Model-View-ViewModel architecture is used for structuring the app UI.
- Data Binding: View Binding is used to bind UI components in layouts to data sources, while StateFlows are used for state management with Kotlin coroutines.
- Local Database: Room (SQLite) is used for local data storage.
- User Authentication: Firebase Authentication is used.
- Dependency Injection: Hilt is used for dependency injection if needed.
- Unit Testing: Unit tests for the business logic were written. 
- UI(Integration) Testing: UI tests were written using Espresso.
