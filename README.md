## Documentation

This project is structured into several modular classes to separate concerns between
string algorithms, utilities, user interface, and program control flow.

### Project Structure

```
VILLEZA_MP3
│
├── Utils
│   Helper functions 
│
├── StringMethods
│   Core object - Algorithms and String Input
│
├── UI
│   CLI menu display and user input
│
└── Main (VILLEZA_MP3)
    Program controller and execution loop
```

---

### Class Overview

#### `Utils`
Provides helper functions used by the algorithms.
- `isEmpty()` – validates that a string is not empty
- `clean()` – removes non-alphabetic characters and normalizes case
- `rmSpace()` – removes whitespace

---

#### `StringMethods`
Contains the **core string algorithms**.
Algorithms:
- `stringReverse()` – reverses a string  
- `isPalindrome()` – checks if a string reads the same forward and backward  
- `isAnagram()` – checks if two strings are permutations of each other  
- `wordCount()` – counts words in a string  
- `charCount()` – counts characters excluding spaces  
- `substringCount()` – counts occurrences of a substring  
- `toLower()` – converts text to lowercase  
- `toUpper()` – converts text to uppercase  
- `removeVowel()` – removes vowels from the string  
- `removeConsonants()` – removes consonants from the string  

---

#### `UI`
Responsible for the **command-line interface**.

Features:
- Displays the program menu
- Accepts user choices
- Handles string input
- Validates menu selections

---

#### `VILLEZA_MP3`
The **main program controller**.
- Runs the program loop
- Coordinates user input and algorithm execution
- Displays results
- Handles errors and program termination

---

### Documentation
More detailed explanations of the algorithms and implementation:

➡️ [Full Project Documentation](https://example.com/project-docs)
