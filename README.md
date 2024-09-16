# Tetris - Java Game (MVC Architecture + JUnit Tests)

## Overview

This repository contains a Tetris game implemented in Java using the **Model-View-Controller (MVC)** design pattern. The project also includes unit tests to ensure the correctness of the game logic and mechanics, built with **JUnit**.

## Features

- **MVC Architecture**: The game is structured following the MVC pattern, separating the core logic (Model), user interaction (Controller), and graphical display (View) for better modularity and maintainability.
- **Java Swing GUI**: The game interface is created using Java Swing, providing a simple and interactive 2D graphical experience.
- **JUnit Testing**: Core methods and game logic are thoroughly tested using JUnit, ensuring the stability and reliability of the game.
- **Score and Level System**: Tracks and displays the player's score and level progression.
- **Speed Progression**: Game speed increases as the player's score surpasses certain thresholds.
- **Game Over Screen**: Displays a _Game Over_ message with options to quit the game.

## Project Structure

- **Model**: Manages the game state, including the game board, current figure, score, and level.
- **View**: Handles rendering of the game board, current figure, score, and other UI elements using Java Swing.
- **Controller**: Manages user input, such as keyboard actions for moving and rotating figures.

## Technologies Used

- **Java 21**: The game is implemented using Java 21.
- **Swing**: Used for creating the graphical user interface (GUI).
- **JUnit 4**: Used for unit testing the game's functionality.
