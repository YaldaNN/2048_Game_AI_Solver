# 2048 Game AI Solver - Complete Project

This repository contains the implementation of an AI solver for the **2048 Game** across **three milestones**. The project develops an intelligent system that can play the **2048 game** using various **search algorithms** and **heuristics**. Each milestone progressively improves the AI's performance by introducing new algorithms, optimizing existing ones, and enhancing the AI's strategic decision-making.

## Project Structure

The project is divided into three milestones, each demonstrating different techniques used to solve the **2048 game** AI. Each folder contains the code for a specific milestone, along with explanations, improvements, and performance results.

### **Milestone 1: BFS-based Move Selection**
In **Milestone 1**, I implemented a **Breadth-First Search (BFS)** algorithm to evaluate the best possible moves given a partially filled 4x4 grid. The goal was to select the first three moves that maximize the total score.

- **Algorithm Used**: BFS for evaluating the first three moves.
- **Goal**: Maximize the score by evaluating potential moves.
- **Result**: The algorithm evaluates all possible moves and chooses the optimal ones based on the total score.


### **Milestone 2: Hill-Climbing AI for 2048 Game**
**Milestone 2** introduced the **hill-climbing algorithm** to solve the **2048 game** AI. The algorithm evaluates possible moves and selects the one with the best outcome based on random and maximizing strategies.

- **Algorithm Used**: **Random Hill Climbing** and **Maximizing Hill Climbing** algorithms.
- **Goal**: Improve decision-making by evaluating moves based on maximizing the score.
- **Result**: AI selects the best sequence of moves that leads to the highest score.



### **Milestone 3: MiniMax Algorithm**
In **Milestone 3**, I implemented the **MiniMax algorithm** with the **Maximizing Current and Minimizing Next Score** approach. This algorithm evaluates all possible moves to maximize the score, assuming the opponent places a **2 or 4** tile in the worst possible location.

#### **MiniMax (Original Version)**:
- **Algorithm Used**: **MiniMax algorithm** for decision-making with depth-based search.
- **Goal**: Maximize the score by evaluating possible moves with a **depth of 3** (Maximizer, Minimizer, Maximizer).
- **Result**: The AI selects the optimal move by simulating the best possible outcomes for itself, while the opponent minimizes its chances.



#### **MiniMax with Heuristic Improvement (Modified Version)**:
In this modified version of **Milestone 3**, I introduced a **heuristic improvement** to the **MiniMax algorithm**. The heuristic was designed to not only maximize the current score but also to prioritize **maximizing the number of empty spaces** on the board. This allows the AI to make more **strategic moves**, potentially improving its ability to reach the **2048 tile**.

- **Algorithm Used**: **MiniMax algorithm** with an additional heuristic for maximizing **empty spaces**.
- **Goal**: Improve decision-making by considering both the current score and the flexibility of future moves (empty spaces).
- **Result**: The AI selects moves that not only maximize the score but also leave more empty spaces, improving its long-term ability to make better moves.



## How to Use This Repository

1. **Clone the repository**:
   ```bash
   git clone https://github.com/YaldaNN/2048_Game_AI_Solver.git
   cd 2048_Game_AI_Solver
2. **For each milestone**:
   - Navigate to the corresponding folder.
   - Follow the **Usage Instructions** in each **README.md** for the respective milestone.

3. **Compile and run** the programs:
   - For each milestone, compile and run the program as per the instructions provided in the milestone’s **README.md**.

