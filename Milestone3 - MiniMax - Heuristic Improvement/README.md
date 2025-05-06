# 2048 Game AI Solver - Milestone 3: MiniMax Search Algorithm with Heuristic Improvement

In **Milestone 3**, I implemented the **MiniMax algorithm** for solving the **2048 Game** using a **Maximizing Current and Minimizing Next Score** approach. The goal of this milestone is to maximize the AI’s score while simulating an opponent (the **minimizer**) who tries to minimize the AI’s score by placing a **2 or 4** on the board at the worst possible location.

In this version, I also added a **heuristic improvement** to the **MiniMax algorithm** to improve performance. The heuristic was designed to focus not just on maximizing the current score but also on maximizing the number of **empty spaces** on the board. This change aimed to improve the chances of reaching the **2048 tile** by considering the state of the board after each move.

## Features

- **MiniMax Search Algorithm**:
    - Evaluates all possible moves to choose the one that maximizes the score, assuming the opponent places a tile in the worst possible spot.

- **Heuristic Improvement**:
    - Instead of just maximizing the score, the AI also focuses on **maximizing empty spaces** on the board, which allows for more potential moves and better future moves.

- **Depth of Search**:
    - The algorithm searches to **depth 3**, alternating between the **maximizer** (AI) and the **minimizer** (the opponent).

- **Game Play**:
    - The game continues until the board is full, no valid moves are left, or the **2048 tile** is achieved.
    - After the game ends, the **final score** and **board configuration** are printed.

## How It Works

1. **Starting State**:
    - The game starts with two **2's** placed randomly on the board.
    - The player can make one of four possible moves: **Up**, **Down**, **Left**, or **Right**.

2. **MiniMax Search with Heuristic**:
    - The AI evaluates all possible moves, considering both the **current score** and the **empty spaces** on the board.
    - The **minimizer** (opponent) places a **2 or 4** in the worst possible location for the AI to minimize its score.

3. **Game Termination**:
    - The game ends when no valid moves are available, or when the **2048 tile** is reached.

4. **Iteration and Output**:
    - The AI plays until it either wins or the game reaches a full board with no valid moves.
    - The final board state and score are printed.

## Usage Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/YaldaNN/2048_Game_AI_Solver
   cd 2048_Game_AI_Solver


2. **Compile the program**:
    - If you're using Java, compile the program:
      ```bash
      javac Game2048_m3_heuristic.java
      ```

3. **Run the program**:
    - After compilation, run the program:
      ```bash
      java Game2048_m3_heuristic
      ```

4. **Check the output**:
    - The final board state and the score will be printed to the console after the AI completes its moves, showing the best possible sequence of moves and the resulting score.

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
## Extra Credit: Heuristic for Improved Performance

### Heuristic Explanation:
To improve the performance of the AI, I **proposed and implemented a heuristic** that maximizes the **number of empty spaces** on the board instead of just focusing on maximizing the current score. This heuristic helps the AI make more strategic moves, potentially leading to a higher score by allowing for more future moves.

The heuristic is based on the assumption that having more empty spaces gives the AI more room to make strategic decisions and move tiles around, especially when higher-value tiles start merging.

### Heuristic Implementation:
After each move, the AI evaluates not only the **current score** but also considers the **number of empty spaces** remaining on the board.

The more empty spaces available, the higher the potential for future moves, leading to a higher score in the long run.

This heuristic guides the AI to select moves that leave more empty spaces, improving its ability to combine tiles and reach the **2048 tile**.

### Performance Comparison:
I ran **100 simulations** for both the original version and the heuristic-based version. The heuristic version achieved **higher scores** more frequently and reached the **2048 tile** **8 times** out of 100 runs, compared to the original version, which only achieved it **2 times**.

### Diagram of Heuristic Evaluation:
This diagram illustrates how the heuristic evaluates board states by prioritizing **empty spaces** over just maximizing the current score. The main goal of this heuristic is to **maximize the flexibility of future moves** by keeping more empty spaces on the board, which allows the AI to make better strategic decisions.

- **Empty Spaces Prioritization**: The AI evaluates all possible moves and selects the one that leaves more empty spaces, which allows for greater potential future moves.
- **Board State Evaluation**: The heuristic balances the immediate **current score** with the **number of empty spaces** remaining after each move.
- **Strategic Move Selection**: Moves that maximize long-term potential by leaving more empty spaces are prioritized, even if they don’t immediately maximize the score.
- **Comparison with Maximizing Score**: Unlike the traditional maximizing score approach, this heuristic emphasizes flexibility and maneuverability, enabling the AI to make better decisions in the long run.

![Screenshot 2025-05-06 at 3.21.49 PM.png](..%2F..%2F..%2F..%2FScreenshot%202025-05-06%20at%203.21.49%E2%80%AFPM.png)