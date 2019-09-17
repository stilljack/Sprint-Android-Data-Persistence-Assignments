# Reading List File Storage

## Introduction

For this assignment, we will take the Reading List app created in M01 and replace the SharedPreferences backend with a File Storage backend.

## Instructions

#### Extract the repository interface
In the M01 Assignment, you added persistence using `SharedPreferences`. For this assignment, we want to generalize storage to an interface so that our storage method is flexible, and we can instantiate either a SharedPreferences data store or a File storage data store only by changing which class in instantiated, but without changing all the places where we create, update, and read objects. We will store our objects in a single .json file per object.

1. Look at the places in your code where you are using `SharedPreferences` to come up with a list of functions that describe the operations you must perform to store your data. This might be things like `createBook`, etc.
2. Create an interface that describes these operations.

#### Move the `SharedPreferences` code into its own class.
If your interface for the above step is well-specified, you should be able to move all your `SharedPreferences` code into its own class.
1. Create a class to handle SharedPreferences operations. This class should extend the interface you extracted in the previous step.
2. Remove the `preferences` object in your `MainActivity` and replace it with a member variable representing the new class. Make sure the type of this variable is the new interface you created.
3. Implement all the existing `SharedPreferences` functionality in the new class. This should allow you to move all the constants and calls to `SharedPreferences` out of your UI classes. If you are unable to do so, revisit the previous step and consider if you need to add more functions to the interface.

It's a good idea to make sure this is all working before moving on.

#### Create a class to handle File Storage

1. Create a class that extends your interface.
2. In this class, you will use Files to store your objects. You can decide in what format you want to save data, but using text files that store each object in JSON format may be easiest.
3. Implement the interface methods to store and retreive your model objects.

#### Replace `SharedPreferences` with Files
1. In the places where you are using the `SharedPreferences` implementation of your interface, instantiate a the File Storage based implementation instead. If your interface fully describes your storage needs, you should be able to do this with minimal changes, just a line or two of code.

#### Challenge
- Suppose you wanted to create a new implementation of your interface that uses a Retrofit operations instead of file storage or preferences. What are the challenges?
- Try like [mockable.io] to create a simple mocked API for your datastore, and use that to save and read your Book entities.
