# 2048 Game AI Solver - Milestone 3: MiniMax Search Algorithm for 2048 Game

In **Milestone 3**, I implemented a **MiniMax algorithm** for solving the **2048 Game**. This algorithm uses a **Maximizing Current and Minimizing Next Score** approach, where the player (the AI) tries to maximize their score, while the opponent (simulated by the program) minimizes the AI's score by placing a **2 or 4** on the board at the worst possible location.

The algorithm searches to a **depth of 3**, where the AI makes its move, the opponent places a number, and then the AI again chooses the best move based on the predicted state of the game. The AI uses a **DFS search** pattern to evaluate all possible moves and chooses the one that maximizes the score assuming the worst-case scenario.

## Features

- **Maximizing Current and Minimizing Next Score**:
    - The current score is the score that results from one valid move, and the next score is the minimum score possible after a valid move and adding a tile (either 2 or 4).

- **MiniMax Search Algorithm**:
    - The algorithm assumes the AI (the maximizer) chooses a move, and then the opponent (the minimizer) places a 2 or 4 in a random location to minimize the AI’s score.
    - This process is repeated for 3 steps (Maximizer, Minimizer, Maximizer).

- **Depth of Search**:
    - The algorithm searches to **depth 3**: Maximizer at depth 1, Minimizer at depth 2, and Maximizer again at depth 3.
    - At depth 1, the AI selects the move that maximizes the expected score, assuming the opponent places the tile in the worst possible position.

- **Game Play**:
    - The game continues until the board is full, no valid moves are left, or the **2048 tile** is achieved.
    - At the end of the game, the **final score** and **board configuration** are printed.

## How It Works

1. **Starting State**:
    - The 2048 game starts with two **2's** placed randomly on the board.
    - The player can make one of four possible moves: **Up**, **Down**, **Left**, or **Right**.

2. **MiniMax Search**:
    - For each move, the program evaluates the **current and next score** using a **depth-first search (DFS)** approach.
    - The AI (Maximizer) tries to maximize its score, and the opponent (Minimizer) places a **2 or 4** in a location that minimizes the AI’s score.
    - The algorithm recursively evaluates all possible moves to find the best option.

3. **Game Termination**:
    - The game ends when no valid moves are available, or when the **2048 tile** is reached.

4. **Iteration and Output**:
    - The program continues making moves until the game ends or 2048 is achieved.
    - The final board state and score are printed.

## Usage Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/YaldaNN/2048_Game_AI_Solver.git
   cd 2048_Game_AI_Solver

2. **Compile the program**:
    - If you're using Java, compile the program:
      ```bash
      javac Game2048_m3.java
      ```

3. **Run the program**:
    - After compilation, run the program:
      ```bash
      java Game2048_m3
      ```
4. **Check the output**:
    - The results of the AI’s moves will be printed to the console, showing the final board configuration and the score after completing the game.

### Example Output

#### **Winning Scenario (2048 achieved)**:
```bash
*******************************************

You WON! 2048 is on the board!

Final state of the board:
16  ,  2048  ,  2  ,  32  ,
0  ,  32  ,  8  ,  8  ,
0  ,  0  ,  2  ,  0  ,
0  ,  0  ,  0  ,  0  ,

Score:  10976

*******************************************
```

#### **Losing Scenario (No more valid moves)**:
```bash
*******************************************

You LOST! Board is full without 2048!

Final state of the board:
2  ,  1024  ,  4  ,  2  ,
128  ,  512  ,  16  ,  128  ,
16  ,  8  ,  128  ,  8  ,
4  ,  2  ,  4  ,  2  ,

Score:  8244

*******************************************
```