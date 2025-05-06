/**
 * The Game2048 program implements an application that builds a 4 by 4 2048 game board, and fill
 * it with two 2 in random locations. For empty places we have zero. Then program plays the 2048
 * game based on minimax algorithm, player is the maximizer, player here is computer, and minimizer
 * trys to put random 2 or 4 in a location which causes minimum possible scores. This program searches
 * to depth 3, which means we have maximizer, minimizer and maximizer again. Based on this prediction,
 * the maximizer at depth 1 will choose max score and algorithm chooses the move that cause the max
 * score and board moves. Then, a random 2 or 4 will be added in a random location. After number is
 * added, this board will be passed to minimax algorithm again to based on predictions; next move
 * get chosen, and we keep going until board is full and there is no more move, or 2048 is found.
 * I used L for left, R for right, U for up, and D for down.
 * This program works for a specific input & output, it does not prompt user. Therefore, it does not
 * handle any exceptions.
 *
 * @author  Yalda Nafisinia
 * @version 3.0
 */

import java.util.ArrayList;
import java.util.Random;
public class Game2048_m4_heuristic {
    // holds all the initial boards from input file
    private static int[][] currentBoard;

    // holds the current score in the game
    private static int currentScore = 0;

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

        // Generate the initial board with two 2 on board at random locations
        buildBoard();

        // keep playing if there are moves available and if 2048 is not found
        while (isMovesAvailable(currentBoard) && !is2048Exist(currentBoard)) {

            // minimax algorithm
            minimax();

            // check after move, still moves available and 2048 not found?
            // if correct then, add a random 2 or 4 at in a random empty location
            if (isMovesAvailable(currentBoard) && !is2048Exist(currentBoard)){
                addRandom2or4();
            }
        }

        // print result of this algorithm
        printBoard(currentBoard);

    }

    /**
     * The minimax method uses the minimax algorithm to find the
     * max score after minimizer puts the random 2 or 4 at worst
     * location to minimize score of maximizer, depth = 3, meaning
     * algorithm has maximizer, minimizer, maximizer
     */
    private static void minimax(){

        // to hold temporary scores
        ArrayList<Integer> tempScores = new ArrayList<>();

        // to hold min scores, passed from minimizer
        ArrayList<Integer> minScores = new ArrayList<>();

        // generate all 4 possible moves and save them in tempMaxBoards
        ArrayList<int[][]> tempMaxBoards = moves(currentBoard);

        // keep score of each 4 possible moves in tempScores
        for (int i = 0; i < 4; i++){
            tempScores.add(i, movesScores[i]);
        }

        // pass all 4 possible moves separately to minimizer to find the min score
        // then when min is returned from minimizer, save it in minScores so the
        // maximizer can choose the max of returned min scores
        for (int i = 0; i < tempMaxBoards.size(); i++)
        {
            minScores.add(minimizer(tempMaxBoards.get(i)));
        }

        // final scores of each move is the result of initial move score that was saved
        // in tempScores and returned minScore
        int[] finalScoreOfEachMove = new int[4];
        for (int i = 0; i < 4; i++){
            finalScoreOfEachMove[i] = tempScores.get(i) + minScores.get(i);
        }

        // Test Print
        /*
        printBoard(boards.get(0));
        for (int m=0; m<minScores.size();m++){
            System.out.println(minScores.get(m));
        }
        System.out.println();
        for (int m=0; m<minScores.size();m++){
            System.out.println(tempScores.get(m));
        }
        System.out.println();
        for (int m=0; m<minScores.size();m++){
            System.out.println(finalScoreOfEachMove[m]);
        }
        */

        // find the max and indexMax of returned min scores
        int max = finalScoreOfEachMove[0];
        int maxIndex = 0;
        for (int i = 1; i < finalScoreOfEachMove.length; i++){
            if (finalScoreOfEachMove[i] > max){
                max = finalScoreOfEachMove[i];
                maxIndex = i;
            }
        }

        // clear movesScores
        empty_movesScores();
        // take the move associated with max score
        if (maxIndex == 0){
            moveLeft(currentBoard);
            currentScore = currentScore + movesScores[0];
        } else if (maxIndex == 1) {
            moveRight(currentBoard);
            currentScore = currentScore + movesScores[1];
        } else if (maxIndex == 2) {
            moveUp(currentBoard);
            currentScore = currentScore + movesScores[2];
        } else {
            moveDown(currentBoard);
            currentScore = currentScore + movesScores[3];
        }

        // Test Print
        /*
        System.out.println("max  "+max);
        System.out.println();
        System.out.println("maxIndex  "+maxIndex);
        System.out.println();
         */
    }

    /**
     * The moves method calls all 4 possible moves and saves the board
     * after each move in middleGameBoards to keep all the possibilities.
     * @param board The board that we are playing and testing.
     */
    private static ArrayList<int[][]> moves(int[][] board){
        // temporary array to hold temporary boards
        int[][] tempBoard;
        ArrayList<int[][]> temps = new ArrayList<>();

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        empty_movesScores();

        // left move
        tempBoard = deepCopy2DArray(board);
        moveLeft(tempBoard);
        temps.add(tempBoard);

        // right move
        tempBoard = deepCopy2DArray(board);
        moveRight(tempBoard);
        temps.add(tempBoard);

        // up move
        tempBoard = deepCopy2DArray(board);
        moveUp(tempBoard);
        temps.add(tempBoard);

        // down move
        tempBoard = deepCopy2DArray(board);
        moveDown(tempBoard);
        temps.add(tempBoard);

        return temps;
    }

    /**
     * The maximizer method chose the move which results in maximum of
     * scores. In case of draw, it picks random.
     * @param board to move
     */
    private static int maximizer(int[][] board){

        // temporary array to hold temporary boards
        int[][] tempBoard;
        int emptyCellCounter = 0;
        int [] emptyCellNum = new int[4];

        // because after each move a score is saved in moveScores, then after
        // checking each move, we should clear it.
        empty_movesScores();

        // left move
        tempBoard = deepCopy2DArray(board);
        moveLeft(tempBoard);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (tempBoard[i][j] == 0)
                    emptyCellCounter++;
            }
        }
        emptyCellNum[0] = emptyCellCounter;
        emptyCellCounter = 0;

        // right move
        tempBoard = deepCopy2DArray(board);
        moveRight(tempBoard);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (tempBoard[i][j] == 0)
                    emptyCellCounter++;
            }
        }
        emptyCellNum[1] = emptyCellCounter;
        emptyCellCounter = 0;

        // up move
        tempBoard = deepCopy2DArray(board);
        moveUp(tempBoard);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (tempBoard[i][j] == 0)
                    emptyCellCounter++;
            }
        }
        emptyCellNum[2] = emptyCellCounter;
        emptyCellCounter = 0;

        // down move
        tempBoard = deepCopy2DArray(board);
        moveDown(tempBoard);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (tempBoard[i][j] == 0)
                    emptyCellCounter++;
            }
        }
        emptyCellNum[3] = emptyCellCounter;


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

        int maxEmptyCell;
        maxEmptyCell = emptyCellNum[0];
        for (int i = 1; i < 4; i++){
            if (emptyCellNum[i] > maxEmptyCell){
                // save the maximum score in max and save the index in indexMax
                // different index belongs to different moves
                maxEmptyCell = emptyCellNum[i];
            }
        }

        return maxEmptyCell;
    }

    /**
     * The minimizer methods generates all possible states with 2 and 4.
     * Then pass each possible board to maximizer (depth 3 here) to return max
     * to min then min will return the result to main.
     * @param board the board to be checked
     * @return min score
     */
    private static int minimizer(int[][] board){

        // to hold temporary boards for this method
        ArrayList<int[][]> tempBoards = new ArrayList<>();

        // to hold temporary 2D arrays after copying
        int[][] tempBoard;

        // generate all possible boards with adding 2 and 4
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                tempBoard = deepCopy2DArray(board);
                if (tempBoard[row][col] == 0) {
                    tempBoard[row][col] = 2;
                    tempBoards.add(tempBoard);
                    tempBoard = deepCopy2DArray(board);
                    tempBoard[row][col] = 4;
                    tempBoards.add(tempBoard);
                }
            }
        }

        // Test print
        /*
        for (int p=0;p<tempBoards.size();p++){
             printBoard(tempBoards.get(p));
              System.out.println();
        }
        //System.out.println(tempBoards.size());
        */

        // find the maximum of each boards of depth 3
        ArrayList<Integer> maxScores = new ArrayList<>();
        for (int i = 0; i < tempBoards.size(); i++){
            maxScores.add(maximizer(tempBoards.get(i)));
        }

        // finding the min score in maxScores at depth 2
        int min = maxScores.get(0);
        for (int i = 1; i < maxScores.size(); i++){
            if (maxScores.get(i) < min){
                min = maxScores.get(i);
            }
        }

        return min;
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
        currentBoard = deepCopy2DArray(board);
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
     */
    private static void addRandom2or4(){

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
            if (currentBoard[i][j] == 0){
                currentBoard[i][j] = randomInput;
                check = true;
            }
        }
    }

    /**
     * The printBoard method prints all elements of the passed board.
     * @param board the board to print
     */
    private static void printBoard(int[][] board){

        System.out.println("****************************************");
        System.out.println();

        if (is2048Exist(currentBoard)){
            System.out.println("You WON! 2048 is on board!");
            System.out.println();
        } else {
            System.out.println("You LOST! Board is full without 2048!");
            System.out.println();
        }

        System.out.println("Final state of the board:");
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board.length; col++){
                System.out.print(board[row][col]+"  ,  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Score: " + currentScore);
        System.out.println();
        System.out.println("****************************************");
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
