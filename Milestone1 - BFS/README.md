# 2048 Game AI Solver - Milestone 1: BFS-based Move Selection

In **Milestone 1**, I built the first version of the **2048 Game AI Solver** from scratch. This AI solver uses **Breadth-First Search (BFS)** to evaluate the best possible moves given a partially filled 4x4 grid. The goal is to select the first three moves that maximize the total score.

## Features

- **BFS-based Move Selection**: Evaluates all possible moves and calculates the resulting score for each of the first three moves.
- **Move Options**: The AI can choose from four possible moves — **Left (L)**, **Right (R)**, **Up (U)**, and **Down (D)**.
- **Simplified Tile Placement**: A "2" tile is added to the first available position (found using a vertical scan from top to bottom).
- **Score Calculation**: The AI selects the sequence of three moves that maximizes the total score (`S1 + S2 + S3`).

## Input and Output

- **Input**: Reads the initial board configuration from `2048_in.txt`. Each test case consists of a 4x4 grid, where `0` represents an empty space.
- **Output**: Prints the maximum score and corresponding sequence of moves for each test case to `2048_out.txt`.

### Example:

#### **Input (`2048_in.txt`)**
```bash
5
2,4,4,0
16,0,0,0
4,8,0,0
2,8,0,0
2,2,0,0
0,4,16,4
0,0,0,2
8,32,32,0
0,0,0,4
0,2,32,0
16,0,8,0
2,0,4,4
0,0,0,0
8,2,2,4
256,256,0,0
4,0,0,0
1024,0,0,0
0,0,0,0
0,0,0,1024
0,0,0,0
```
#### **Output (`2048_out.txt`)**
```bash
60,R,D,U
80,R,U,U
60,L,U,L
544,L,L,L
2052,L,U,L
```

## How It Works

1. Starts with an initial 4x4 grid (from `2048_in.txt`).
2. Simulates the four possible moves: **Left (L)**, **Right (R)**, **Up (U)**, **Down (D)**, and computes the resulting board and score.
3. After each move, a "2" tile is added to the first available empty space (following a vertical scan).
4. Evaluates the three best moves and calculates the total score (`S1 + S2 + S3`).
5. Selects the sequence of moves that maximizes the total score.

## Usage Instructions
1. **Clone the repository**:
   ```bash
   git clone https://github.com/YaldaNN/2048_Game_AI_Solver.git
   cd 2048_Game_AI_Solver
2. **Prepare the input file**:  
   Ensure that your `2048_in.txt` file contains the board configurations for the test cases.

3. **Compile and run the program**:
    - If you're using Java, compile the program:
      ```bash
      javac Game2048.java
      ```
    - Then, run the program:
      ```bash
      java Game2048
      ```

4. **Check the output**:  
   The results will be written to `2048_out.txt`, containing the maximum score and the best sequence of moves for each test case.
