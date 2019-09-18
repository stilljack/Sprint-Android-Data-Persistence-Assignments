# SQL Database

## Introduction

This project will have you use db-fiddle to create a small SQL database and interact with it. To turn this assignment in, I'd like you to do 2 things. First, fork this repo and add two text files to it using the "create new file" button. One file will be the code in the left panel of db-fiddle, the other will be the code on the right. The second thing would be to send the URL to the latest version of your db-fiddle to your TL in a DM on Slack so they can review it.

## Instructions

### Part 1 - Setup

Navigate to db-filddle, create a new sqlite instance

1. Navigate to [https://www.db-fiddle.com/]
2. In the dropdown on the top left, select SQLite v3.26

### Part 2 - Schema

Build a schema for a db using 2 tables

1. In the panel on the left (Schema SQL), write the definition for your first table.

2. Write the query to create your PLAYERS table

   1. Write the structure of the query by writing CREATE TABLE ();
   2. Inside of the parenthesis, add columns for each of your player stats
   3. You must include columns for first name (TEXT), last name (TEXT), AGE (INTEGER), and id (PRIMARY KEY), you may include any other values you would like to

3. Click Update, then run to make sure your query works

4. Build the create table query to create your TEAMS table

   > This table should to have more than just the team's name to add value for the table.

5. Click Update, then run to make sure your queries work

### Part 3 - Add Data

1. In the panel on the right, write queries to insert data into your table.
   1. Write the structure of the query, INSERT INTO players () VALUES ()
   2. Add the column names into the insert parameters, be sure to separate them by commas
   3. Add the column values into the values parameters, be sure to separate them and that they are in the same order as the column names
2. Click Run to make sure your queries work
3. Repeat step 1 multiple times for each table to add test data

### Part 4 - Reading Data

1. Move your insert queries to the left panel.

   > Remember, the left panel is run with the update button to build your DB. It will reset the database and the run button will run queries and 

2. Click update to reset and update your DB

3. In the right panel, select all the data from one of your tables by using the query SELECT * FROM _table_name_

4. Click Run to make sure your queries work

5. Select a single column from your table by replacing the * in the SELECT statement with a column name

6. Click Run to make sure your queries work

7. Select a single element by putting their id in a WHERE clause

8. Click Run to make sure your queries work

9. Select a group of records by writing a broader WHERE clause

10. Click Run to make sure your queries work

### Part 5 - Updating Data

1. Write queries to update a value in each of your tables using a combination of UPDATE, SET, and WHERE clauses
2. Click Run to make sure your queries work

### Part 6 - Removing Data

1. Write a query to delete an item from one of your tables using a query with a DELETE, FROM, and WHERE clause.
2. Click Run to make sure your queries work

## Challenge

### Joining Tables

1. In the CREATE query for your players table, add a FOREIGN KEY column for the id of the team the player belongs to.

> The syntax for adding a foreign key is the same as any other field, however, there must be a line at the end of the CREATE TABLE which binds the foreign key. The structure is `FOREIGN KEY (_foreign_key_name_in_current_table_) REFERENCES _foreign_table_(_key_name_in_foreign_table)`

2. Update all your insert queries for that table to include this FOREIGN KEY

   > You'll want to be sure to add all the teams first to make sure you have their ids to use

3. Write queries to SELECT values from both columns, FROM your main table (the one that references the other), and JOIN the second table ON the foreign key with the other table's key

   > You'll want to use the `table_name.` to distinguish between tables

4. Click Run to make sure your queries work
