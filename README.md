# GatorLibrary Management System

## Overview

GatorLibrary is a software system designed to efficiently manage books, patrons, and borrowing operations. The system is implemented using a **Red-Black Tree** for book storage and a **Min-Heap** for managing book reservations. The goal is to enhance operational efficiency and provide a user-friendly platform for both library staff and patrons.

## Features

- Store and manage books using a **Red-Black Tree**.
- Support efficient **book insertions, deletions, and searches**.
- Allow patrons to **borrow and return books**.
- Handle **book reservations** using a **Binary Min-Heap**, prioritizing patrons based on priority level and reservation time.
- Provide analytics through a **Color Flip Count** feature, which tracks color changes in the Red-Black Tree during insertions and deletions.

## Technologies Used

- **Programming Language:** Java
- **Development Environment:** Visual Studio Code
- **Supported Operating Systems:** Windows, macOS

## Operations Overview

The system supports the following operations:

1. **PrintBook(bookID)** - Displays details of a specific book.
2. **PrintBooks(bookID1, bookID2)** - Displays details of books within a given ID range.
3. **InsertBook(bookID, bookName, authorName, availabilityStatus, borrowedBy, reservationHeap)** - Adds a book to the system.
4. **BorrowBook(patronID, bookID, patronPriority)** - Allows a patron to borrow a book or place a reservation if the book is unavailable.
5. **ReturnBook(patronID, bookID)** - Handles book returns and assigns the book to the next patron in the reservation list if applicable.
6. **DeleteBook(bookID)** - Removes a book and notifies reserved patrons.
7. **FindClosestBook(targetID)** - Finds the closest book by ID.
8. **ColorFlipCount()** - Tracks color changes in the Red-Black Tree.

## How to Run the Program

1. Unzip the project folder.
2. Open a terminal and navigate to the project directory.
3. Compile the program using:

   ```sh
   javac GatorLibrary.java
4. Run the program using:
```sh
Copy
Edit
java GatorLibrary
```
5. The output will be saved in output_file.txt.

## Project Structure
The project consists of five main files:  

1. GatorLibrary.java - Main entry point that processes user commands.
2. MinHeap.java - Implements a Min-Heap for managing book reservations.
3. RedBlackTree.java - Implements a Red-Black Tree for efficient book storage.
4. Patron.java - Defines the Patron class with reservation attributes.
5. Book.java - Defines the Book class with attributes and operations.

