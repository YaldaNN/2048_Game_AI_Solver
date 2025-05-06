/**
 * The Game2048 program implements an application that builds a 4 by 4 2048 game board, and fill
 * it with two 2 in random locations. For empty places we have zero. Then programs plays the 2048
 * game based on two algorithms, random local search and maximizing local search, after each move,
 * it adds a 2 or 4 in a random location, and keep going until board is full and there is no more
 * move, or 2048 is found. It plays 25 times for each algorithm and prints the boards with the
 * maximum score and boards with 2048 (if found any!), the path to that board and its score. We use L
 * for left, R for right, U for up, and D for down.
 * This program works for a specific input & output, it does not prompt user. Therefore, it does not
 * handle any exceptions.
 *
 * @author  Yalda Nafisinia
 * @version 2.0
 */

import java.util.ArrayList;
import java.util.Random;

public class Game2048_m2 {

    // holds all the initial boards from input file
    private static ArrayList<int[][]> boards = new ArrayList<>();

    // holds the current score in the game
    private static int currentScore = 0;

    // holds the path to the current score
    private static String path ="";

    // holds all paths to max score for each board played
    private static ArrayList<String> allPaths = new ArrayList<>();


    // holds all the max scores for all boards played
    private static ArrayList<Integer> scores = new ArrayList<>();

    // holds score associated with each move
    // get(0) is left move score, get(1) is right move score
    // get(2) is uo move score, get(3) is down move score
    private static int[] movesScores = new int[4];

    /**
     * Plays the game with both random hill climbing algorithm and maximized hill
     * climbing algorithm.
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args){

        randomHillClimbingAlgorithm();
        maximumHillClimbingAlgorithm();
    }

    /**
     * The randomHillClimbingAlgorithm method uses random moves method
     * to play the 2048 game.
     */
    private static void randomHillClimbingAlgorithm(){

        System.out.println("Random Local Search Algorithm, based on the hill-climbing algorithm:");

        // to hold number of games played with each program run
        int N = 0;

        // Play the game with random hill climbing algorithm 25 times
        while (N < 25){

            // first build board
            buildBoard();

            // keep playing if there are moves available and if 2048 is not found
            while (isMovesAvailable(boards.get(N)) && !is2048Exist(boards.get(N))) {

                // randomly move
                randomMoves(boards.get(N));

                // check after that random move, still moves available and 2048 not found?
                // if correct then, add a random 2 or 4 at in a random empty location
                if (isMovesAvailable(boards.get(N)) && !is2048Exist(boards.get(N))){
                    addRandom2or4(boards.get(N));
                }
            }

            // here board is full or 2048 is found, so we keep the current score and path in
            // scores list and allPath list and make current score and path empty for the next
            // board that is going to be played.
            scores.add(currentScore);
            currentScore = 0;
            allPaths.add(path);
            path = "";
            N++;
        }

        // print result of this algorithm
        printResult();

        // empty scores, current score, path, allPaths, and boards for the next algorithm.
        scores.removeAll(scores);
        currentScore = 0;
        allPaths.removeAll(allPaths);
        path = "";
        boards.removeAll(boards);
    }

    /**
     * The maximumHillClimbingAlgorithm method uses maximum score moves method
     * to play the 2048 game.
     */
    private static void maximumHillClimbingAlgorithm(){

        System.out.println("Maximum Local Search Algorithm, based on the hill-climbing algorithm:");

        // to hold number of games played with each program run
        int N = 0;

        // Play the game with maximized hill climbing algorithm 25 times
        while (N < 25){

            // first build board
            buildBoard();

            // keep playing if there are moves available and if 2048 is not found
            while (isMovesAvailable(boards.get(N)) && !is2048Exist(boards.get(N))) {

                // maximum score move
                maximumMoves(boards.get(N));

                // check after that random move, still moves available and 2048 not found?
                // if correct then, add a random 2 or 4 at in a random empty location
                if (isMovesAvailable(boards.get(N)) && !is2048Exist(boards.get(N))){
                    addRandom2or4(boards.get(N));
                }
            }

            // here board is full or 2048 is found, so we keep the current score and path in
            // scores list and allPath list and make current score and path empty for the next
            // board that is going to be played.
            scores.add(currentScore);
            currentScore = 0;
            allPaths.add(path);
            path = "";
            N++;
        }

        // print result of this algorithm
        printResult();
    }

    /**
     * The buildBoard method builds a 4by4 2D array, and adds two 2
     * at random locations. The rest of element will be zero.
     */
    private static void buildBoard(){

        // create 4by4 boards for each test case
        final int SIZE = 4;
        int [][] board = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++)
                board[row][col] = 0;
        }

        // call randomIndex method to pick random index for adding 2
        // at a random location
        int i = randomIndex();
        int j = randomIndex();
        board[i][j] = 2;

        // find a 2nd random empty location to add the second 2 at the board
        boolean check = false;
        while (!check){
            i = randomIndex();
            j = randomIndex();
            if (board[i][j] == 0){
                board[i][j] = 2;
                check = true;
            }
        }

        // add the board to the boards list
        boards.add(board);
    }

    /**
     * The randomIndex method picks a random number from {0,1,2,3} array
     * to be used as an index.
     * @return int a random number
     */
    private static int randomIndex(){

        // holding index options, only one of these numbers are possible
        int[] index = {0,1,2,3};

        // random object
        Random rand = new Random();

        // pick a random number from index array
        int randomNumber = rand.nextInt(index.length);
        int randomIndex = index[randomNumber];

        return randomIndex;
    }

    /**
     * The isMoveAvailable method checks the board to see if any of 4 possible
     * moves is available or not.
     * @param board to check
     * @return true if any moves available
     */
    private static boolean isMovesAvailable(int[][]board){

        // check all possible moves
        boolean hasMoves = moveUp(board) || moveDown(board) || moveLeft(board) || moveRight(board);

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        empty_movesScores();

        return hasMoves;
    }

    /**
     * The empty_movesScore method clears the movesScores variable.
     */
    private static void empty_movesScores(){

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        for (int i = 0; i < 4; i++)
            movesScores[i] = 0;
    }

    /**
     * The is2048Exist method searches the board for 2048 number.
     * @param board to check
     * @return true if 2048 is in the board.
     */
    private static boolean is2048Exist(int[][] board){

        // scan through the board
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                if (board[row][col] == 2048)
                    return true;
            }
        }
        return false;
    }

    /**
     * The randomMoves method randomly moves if current score + next score
     * is not zero.
     * @param board to move
     */
    private static void randomMoves(int[][] board){

        // temporary board to hold temporary moves
        int[][] tempBoard;

        // to hold current score + next score
        int[] totalPossibleScores = new int[4];

        // to hold random index
        int randomIndex;

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        empty_movesScores();

        // left move
        tempBoard = deepCopy2DArray(board);
        moveLeft(tempBoard);


        // right move
        tempBoard = deepCopy2DArray(board);
        moveRight(tempBoard);

        // up move
        tempBoard = deepCopy2DArray(board);
        moveUp(tempBoard);


        // down move
        tempBoard = deepCopy2DArray(board);
        moveDown(tempBoard);

        // calculate all possible scores
        for (int i = 0; i < 4; i++){
            totalPossibleScores[i] = currentScore + movesScores[i];
        }

        // in case all the moves result in zero score, pick random
        // this step is important for the first moves with many empty cells
        if (totalPossibleScores[0]==0 && totalPossibleScores[1]==0 &&
                totalPossibleScores[2]==0 && totalPossibleScores[3]==0) {
            randomIndex = randomIndex();

            if (randomIndex == 0){
                moveLeft(board);
                path += "L, ";
            }
            else if (randomIndex == 1){
                moveRight(board);
                path += "R, ";
            }
            else if (randomIndex == 2){
                moveUp(board);
                path += "U, ";
            }
            else{
                moveDown(board);
                path += "D, ";
            }
        }
        // otherwise, pick a random non-zero score move
        else {
            // index 0 belongs to left move score, 1 to right move score,
            // 2 to up move score, 3 to down move score
            boolean check = false;
            while (!check){
                randomIndex = randomIndex();
                if (totalPossibleScores[randomIndex] != 0){
                    check = true;

                    if (randomIndex == 0){
                        moveLeft(board);
                        path += "L, ";
                        currentScore += movesScores[0];
                    }
                    else if (randomIndex == 1){
                        moveRight(board);
                        path += "R, ";
                        currentScore += movesScores[1];
                    }
                    else if (randomIndex == 2){
                        moveUp(board);
                        path += "U, ";
                        currentScore += movesScores[2];
                    }
                    else{
                        moveDown(board);
                        path += "D, ";
                        currentScore += movesScores[3];
                    }
                }
            }
        }
    }

    /**
     * The moveUp methods have all 2048 game rules for the action move up.
     * This method also save the score after moving up in scores global variable.
     * @param board The 4by4 board which needs to move up.
     * @return true if moved up
     */
    private static boolean moveUp(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;
        boolean moved = false;

        for (int col = 0; col < 4; col++){
            int row = 0;
            if (board[row][col] == board[row+1][col] && board[row+2][col] == board[row+3][col]){
                sum = board[row][col] + board[row+1][col];
                score += sum;
                board[row][col] = sum;
                sum = board[row+2][col] + board[row+3][col];
                score += sum;
                board[row+1][col] = sum;
                board[row+2][col] = 0;
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row+1][col]) {
                sum = board[row][col] + board[row+1][col];
                score += sum;
                board[row][col] = sum;
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row+2][col] && board[row+1][col]==0){
                sum = board[row][col] + board[row+2][col];
                score += sum;
                board[row][col] = sum;
                board[row+1][col] = board[row+3][col];
                board[row+2][col] = 0;
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row+3][col] && board[row+1][col]==0 && board[row+2][col]==0){
                sum = board[row][col] + board[row+3][col];
                score += sum;
                board[row][col] = sum;
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row+1][col] == board[row+2][col]){
                sum = board[row+1][col] + board[row+2][col];
                score += sum;
                board[row+1][col] = sum;
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row+1][col] == board[row+3][col] && board[row+2][col]==0){
                sum = board[row+1][col] + board[row+3][col];
                score += sum;
                board[row+1][col] = sum;
                board[row+3][col] = 0;
                moved = true;
            }
            else if (board[row+2][col] == board[row+3][col]){
                sum = board[row+2][col] + board[row+3][col];
                score += sum;
                board[row+2][col] = sum;
                board[row+3][col] = 0;
                moved = true;
            }

            if (board[row+2][col] == 0){
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
                moved = true;
            }
            if (board[row+1][col] == 0){
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
                moved = true;
            }
            if (board[row][col] == 0) {
                board[row][col] = board[row+1][col];
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
                moved = true;
            }
        }

        movesScores[2] = score;
        return moved;
    }

    /**
     * The moveDown methods have all 2048 game rules for the action move down.
     * This method also save the score after moving down in scores global variable.
     * @param board The 4by4 board which needs to move down.
     * @return true if moved down
     */
    private static boolean moveDown(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;
        boolean moved = false;

        for (int col = 0; col < 4; col++){
            int row = 0;

            if (board[row+3][col] == board[row+2][col] && board[row+1][col] == board[row][col]){
                sum = board[row+3][col] + board[row+2][col];
                score += sum;
                board[row+3][col] = sum;
                sum = board[row][col] + board[row+1][col];
                score += sum;
                board[row+2][col] = sum;
                board[row+1][col] = 0;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row+3][col] == board[row+2][col]) {
                sum = board[row+3][col] + board[row+2][col];
                score += sum;
                board[row+3][col] = sum;
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }

            else if (board[row+3][col] == board[row+1][col] && board[row+2][col]==0){
                sum = board[row+3][col] + board[row+1][col];
                score += sum;
                board[row+3][col] = sum;
                board[row+2][col] = board[row][col];
                board[row+1][col] = 0;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row+3][col] == board[row][col] && board[row+2][col]==0 && board[row+1][col]==0){
                sum = board[row+3][col] + board[row][col];
                score += sum;
                board[row+3][col] = sum;
                board[row][col] = 0;
                moved = true;
            }

            else if (board[row+2][col] == board[row+1][col]){
                sum = board[row+2][col] + board[row+1][col];
                score += sum;
                board[row+2][col] = sum;
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row+2][col] == board[row][col] && board[row+1][col]==0){
                sum = board[row+2][col] + board[row][col];
                score += sum;
                board[row+2][col] = sum;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row+1][col] == board[row][col]){
                sum = board[row+1][col] + board[row][col];
                score += sum;
                board[row+1][col] = sum;
                board[row][col] = 0;
                moved = true;
            }

            if (board[row+1][col] == 0){
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            if (board[row+2][col] == 0){
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            if (board[row+3][col] == 0) {
                board[row+3][col] = board[row+2][col];
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
        }

        movesScores[3] = score;
        return moved;
    }

    /**
     * The moveRight methods have all 2048 game rules for the action move right.
     * This method also save the score after moving right in scores global variable.
     * @param board The 4by4 board which needs to move right.
     * @return true if moved right
     */
    private static boolean moveRight(int[][] board){

        int sum;                // holds sum after each possible move
        int score = 0;
        boolean moved = false;

        for (int row = 0; row < 4; row++){
            int col = 0;
            if (board[row][col+3] == board[row][col+2] && board[row][col+1] == board[row][col]){
                sum = board[row][col+3] + board[row][col+2];
                score += sum;
                board[row][col+3] = sum;
                sum = board[row][col] + board[row][col+1];
                score += sum;
                board[row][col+2] = sum;
                board[row][col+1] = 0;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+3] == board[row][col+2]) {
                sum = board[row][col+3] + board[row][col+2];
                score += sum;
                board[row][col+3] = sum;
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+3] == board[row][col+1] && board[row][col+2]==0){
                sum = board[row][col+3] + board[row][col+1];
                score += sum;
                board[row][col+3] = sum;
                board[row][col+2]= board[row][col];
                board[row][col+1] = 0;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+3] == board[row][col] && board[row][col+2]==0 && board[row][col+1]==0){
                sum = board[row][col+3] + board[row][col];
                score += sum;
                board[row][col+3] = sum;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+2] == board[row][col+1]){
                sum = board[row][col+2] + board[row][col+1];
                score += sum;
                board[row][col+2] = sum;
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+2] == board[row][col] && board[row][col+1]==0){
                sum = board[row][col+2] + board[row][col];
                score += sum;
                board[row][col+2] = sum;
                board[row][col] = 0;
                moved = true;
            }
            else if (board[row][col+1] == board[row][col]){
                sum = board[row][col+1] + board[row][col];
                score += sum;
                board[row][col+1] = sum;
                board[row][col] = 0;
                moved = true;
            }
            if (board[row][col+1] == 0){
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            if (board[row][col+2] == 0){
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
            if (board[row][col+3] == 0) {
                board[row][col+3] = board[row][col+2];
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
                moved = true;
            }
        }

        movesScores[1] = score;
        return moved;
    }

    /**
     * The moveLeft methods have all 2048 game rules for the action move left.
     * This method also save the score after moving left in scores global variable.
     * @param board The 4by4 board which needs to move left.
     * @return true if moved left
     */
    private static boolean moveLeft(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;
        boolean moved = false;

        for (int row = 0; row < 4; row++){
            int col = 0;
            if (board[row][col] == board[row][col+1] && board[row][col+2] == board[row][col+3]){
                sum = board[row][col] + board[row][col+1];
                score += sum;
                board[row][col] = sum;
                sum = board[row][col+2] + board[row][col+3];
                score += sum;
                board[row][col+1] = sum;
                board[row][col+2] = 0;
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row][col+1]) {
                sum = board[row][col] + board[row][col+1];
                score += sum;
                board[row][col] = sum;
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row][col+2] && board[row][col+1]==0){
                sum = board[row][col] + board[row][col+2];
                score += sum;
                board[row][col] = sum;
                board[row][col+1] = board[row][col+3];
                board[row][col+2] = 0;
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col] == board[row][col+3] && board[row][col+1]==0 && board[row][col+2]==0){
                sum = board[row][col] + board[row][col+3];
                score += sum;
                board[row][col] = sum;
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col+1] == board[row][col+2]){
                sum = board[row][col+1] + board[row][col+2];
                score += sum;
                board[row][col+1] = sum;
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col+1] == board[row][col+3] && board[row][col+2]==0){
                sum = board[row][col+1] + board[row][col+3];
                score += sum;
                board[row][col+1] = sum;
                board[row][col+3] = 0;
                moved = true;
            }
            else if (board[row][col+2] == board[row][col+3]){
                sum = board[row][col+2] + board[row][col+3];
                score += sum;
                board[row][col+2] = sum;
                board[row][col+3] = 0;
                moved = true;
            }

            if (board[row][col+2] == 0){
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
                moved = true;
            }
            if (board[row][col+1] == 0){
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
                moved = true;
            }
            if (board[row][col] == 0) {
                board[row][col] = board[row][col+1];
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
                moved = true;
            }
        }

        movesScores[0] = score;
        return moved;
    }


    /**
     * The addRandom2or4 method adds a random 2 or 4 in random empty location.
     * @param board the board to add 2 or 4 to
     */
    private static void addRandom2or4(int[][] board){

        // to hold indexes of random location
        int i;
        int j;

        // array to pick 2 or 4 from
        int[] numbers = {2,4};

        // random object and random pick from numbers array
        Random rand = new Random();
        int randomNumber = rand.nextInt(numbers.length);
        int randomInput = numbers[randomNumber];

        // pick a random location and add that picked 2 or 4
        boolean check = false;
        while (!check){
            i = randomIndex();
            j = randomIndex();
            if (board[i][j] == 0){
                board[i][j] = randomInput;
                check = true;
            }
        }
    }

    /**
     * The printResult method print the result of the all the games played.
     * This method print max score and its board and path of each algorithm played.
     */
    private static void printResult(){

        // after game is played 25 times, we check all boards to see if there are any 2048 in any boards
        // if yes print that board
        int indexMax = 0;
        for (int c = 0; c < boards.size(); c++){
            if (is2048Exist(boards.get(c))){
                indexMax = c;
                System.out.println("We have 2048 in one of the boards:");
                printBoard(boards.get(indexMax));
                System.out.println("Score associated with the board with 2048 = " + scores.get(c));
                System.out.println("Path to this board: " + allPaths.get(indexMax));
                System.out.println("2048 game is played " + boards.size() + " times.");
                indexMax = 0;
                break;
            }
        }

        // look for the maximum score in the saved played boards
        int max = scores.get(0);
        for(int k = 1; k < scores.size(); k++){
            if (scores.get(k) > max){
                max = scores.get(k);
                indexMax = k;
            }
        }

        // We also print the maximum score, its board and its path
        System.out.println("Board with the highest score:");
        printBoard(boards.get(indexMax));
        System.out.println("Highest score = " + max);
        System.out.println("Path to this board: " + allPaths.get(indexMax));
        System.out.println("2048 game is played " + boards.size() + " times.");
        System.out.println("*************************************************************************" +
                "****************************************************************************************" +
                "************************************************************************");
        System.out.println("*************************************************************************" +
                "*****************************************************************************************" +
                "***********************************************************************");
        System.out.println();
    }

    /**
     * The printBoard method prints all elements of the passed board.
     * @param board the board to print
     */
    private static void printBoard(int[][] board){
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                System.out.print(board[row][col]+"  ,  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * The maximumMoves method chose the move which results in maximum of
     * current score + next score.
     * @param board to move
     */
    private static void maximumMoves(int[][] board){

        // temporary array to hold temporary boards
        int[][] tempBoard;

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        empty_movesScores();

        // left move
        tempBoard = deepCopy2DArray(board);
        moveLeft(tempBoard);


        // right move
        tempBoard = deepCopy2DArray(board);
        moveRight(tempBoard);

        // up move
        tempBoard = deepCopy2DArray(board);
        moveUp(tempBoard);


        // down move
        tempBoard = deepCopy2DArray(board);
        moveDown(tempBoard);


        // find the maximum score after 4 possible moves
        int max;
        max = movesScores[0];
        for (int i = 1; i < 4; i++){
            if (movesScores[i] > max){
                // save the maximum score in max and save the index in indexMax
                // different index belongs to different moves
                max = movesScores[i];
            }
        }

        // add the maximum score index in maxScoresIndex array list
        ArrayList<Integer> maxScoresIndex = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            if (movesScores[i] == max){
                maxScoresIndex.add(i);
            }
        }
        // randomly choose one of the max scores
        // for example if left move results 16, right 16, up 8, down 8
        // only left move and right move are added in maxScoresIndex array list
        // one of the left or right move will be selected by steps below randomly
        Random rand = new Random();
        int indexMax =  maxScoresIndex.get(rand.nextInt(maxScoresIndex.size()));

        if (indexMax == 0){
            moveLeft(board);
            path = path + "L, ";
            currentScore = currentScore + movesScores[0];
        }
        else if (indexMax == 1){
            moveRight(board);
            path = path + "R, ";
            currentScore = currentScore + movesScores[1];
        }
        else if (indexMax == 2){
            moveUp(board);
            path = path + "U, ";
            currentScore = currentScore + movesScores[2];
        }
        else{
            moveDown(board);
            path = path + "D, ";
            currentScore = currentScore + movesScores[3];
        }
    }

    /**
     * The deepCopy2DArray gets a deep copy from the original passed array.
     * This method helps to find all possible boards after all possible moves
     * without changing the original board.
     * @param original The board that should be copied.
     * @return Copy of the board.
     */
    private static int[][] deepCopy2DArray(int[][] original) {
        final int SIZE = 4;
        int[][] newArray = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++)
                newArray[i][j] = original[i][j];
        }
        return newArray;
    }
}