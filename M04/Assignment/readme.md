# Reading List File Storage

## Introduction

For this assignment, we will take the Reading List app created in M01 and expanded in M02 and add a Room database.

## Instructions

#### Imports
We're going to use Room and ViewModel/LiveData for this project.
1. Go to your app's build.gradle file and add the imports for Room and Architecture components lifecycle and viewmodel.

#### Create the Entity, DAO, and Database files for your database.
We're going to set up a database storage to replace our SharedPreferences and File Storage backends.
1. Go to your data model(s) and add the annotations to make it work as an entity in Room. Make sure to pay attention to issues surrounding Primary Key and autoGeneration.
2. Set up a package for your database to hold the DAO and Database files.
3. Set up a DAO interface that includes all the methods you expect to call from your repository (you may need to iterate between this section and the next, that's ok!).
4. Set up a Room Database class that uses your DAO.

#### Create the repo that will call the database
In M02, we extracted an interface for storage and used it to seamlessly replace the SharedPreferences storage with a File Storage. We are going to use this interface to interact with our database.
1. Create a repository implementation of you storage interface.
2. Create a Room Database instance using the class you set up in the previous section.
3. Use the database instance in your implementation of the required interface functions.

### Add Async calls
If you try to use the Database as is, you might encounter errors because you cannot perform database operations on the main thread. You'll need to replace the repository interface calls with AsyncTasks.
1. Set up an AsyncTask for each call you make from the main thread.

### Add a ViewModel and LiveData
To make our code cleaner and more efficient and to handle configuration changes, we want to use a ViewModel and LiveData.
1. Create a package for your ViewModel.
2. Create a ViewModel. This ViewModel will use the repo you have created to fetch a LiveData object from the database. You should create a lazily instantiated member variable that returns the LiveData that we get from calling the database through a call to the repository.
3. In the ViewModel, set up calls for each call to the repo.
4. In your Activity, replace calls to the repo to calls to the ViewModel.
5. Replace your update code in your Activity with an observer that observes the LiveData member variable.

#### Challenge
Try one or more of the following:
- Mock out an API that mimics the database operations. Figure out how you would use the repository to make calls to the API and put the results in your database.
- Try the API approach out on one of the read-only API's we have used in the past, such as the Age of Empires II API at [https://age-of-empires-2-api.herokuapp.com/docs/]
- We have been relying on the repo living in the Application. Try moving it to the ViewModel. What, if anything, changes? Is this a reasonable approach?
