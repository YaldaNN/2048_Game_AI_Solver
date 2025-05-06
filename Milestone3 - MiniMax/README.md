# 2048 Game AI Solver - Milestone 2: Hill-Climbing AI for 2048 Game

In **Milestone 2**, I implemented two local search algorithms—**Random Hill Climbing** and **Maximizing Hill Climbing**—to solve the **2048 Game** AI. The goal of this milestone was to evaluate possible moves that maximize the score or achieve the **2048 tile** using a local search approach.

## Features

- **Random Hill Climbing**:
    - Randomly generates next possible states by evaluating each move (up, down, left, right).
    - Selects random moves based on the calculated score and repeats the process.
    - Continues until the game terminates or reaches the maximum score.

- **Maximizing Hill Climbing**:
    - Evaluates all possible next states and selects the one with the maximum score.
    - After each move, a "2" or "4" tile is added randomly to the board.
    - Continues until the game terminates or reaches the maximum score.

- **Game Play**:
    - The game is played **25 iterations** per algorithm to find the best possible results.
    - The iteration with the **highest score** or the **2048 tile** achieved is considered the best result.

## How It Works

1. **Starting State**:
    - The 2048 game starts with two **2's** placed randomly on the board.
    - The player can make one of four possible moves: **Up**, **Down**, **Left**, or **Right**.

2. **Random Hill Climbing**:
    - Randomly evaluates possible moves (up, down, left, right).
    - After each move, a "2" or "4" tile is added to a random empty space on the board.
    - The algorithm continues until either the game terminates or the **2048 tile** is reached.

3. **Maximizing Hill Climbing**:
    - For each possible move, it calculates the current and next scores (including the addition of a "2" or "4").
    - The move with the highest score is selected.
    - The algorithm repeats this until the game ends or the **2048 tile** is achieved.

4. **Game Termination**:
    - The game ends when no valid moves are available or when the **2048 tile** is reached.

5. **Iteration and Output**:
    - For each algorithm, the game is played **25 times**.
    - The best result, either with the highest score or achieving the **2048 tile**, is saved and printed as the final output.

## Usage Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/YaldaNN/2048_Game_AI_Solver.git
   cd 2048_Game_AI_Solver

2. **Compile the program**:
    - If you're using Java, compile the program:
      ```bash
      javac Game2048_m2.java
      ```

3. **Run the program**:
    - After compilation, run the program:
      ```bash
      java Game2048_m2
      ```
4. **Check the output**:
    - The results of both **Random Hill Climbing** and **Maximizing Hill Climbing** will be printed to the console, showing:
        - **The best score** achieved.
        - **The sequence of moves** taken to reach the best score or 2048 tile.

