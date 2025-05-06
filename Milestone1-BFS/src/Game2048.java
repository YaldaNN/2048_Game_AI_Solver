/**
 * The Game2048 program implements an application that reads the initial arrangements from a file
 * called "2048_in.txt". The file starts with N, number of test cases. Then there are 4N lines, 4
 * lines for each test case. On each line we have four numbers separated by comma. For empty
 * places we have zero. Then programs plays the 2048 game only for three moves, after each move,
 * it adds a 2 in the first empty spot while vertically scanning the board. It builds a tree with
 * scores, then uses BFS to traverse the tree, adds path of each node in data section of the node
 * and also keep adding the scores to find the maximum. Program outputs a file called "2048_out.txt".
 * This program for each test cases, print the maximum score and then three moves that resulted in
 * maximum score. We use L for left, R for right, U for up, and D for down and separate each value
 * (score and the moves) with a comma.
 * This program works for a specific input & output, it does not prompt user. Therefore, it does not
 * handle any exceptions.
 * Also, this program returns the path for the first maximum score, there could be another path with
 * the same maximum score. The first path depends on the sequence of actions, the sequence used in
 * this program is Left, Right, Up, and Down.
 *
 * @author  Yalda Nafisinia
 * @version 1.0
 */
import java.io.*;
import java.util.*;
public class Game2048 {
    // holds the number of test cases, the first line in input file
    private static int numberOfTests;

    // holds all the initial boards from input file
    private static ArrayList<int[][]> boards = new ArrayList<>();

    // holds all scores calculated after each possible move and the path to
    // that score, all as a string. Scores are saved in level order.
    private static ArrayList<String> scores = new ArrayList<>();

    // holds middle game possible boards
    private static ArrayList<int[][]> middleGameBoards = new ArrayList<>();

    // holds characters of the path to the maximum cumulated score for 1 board
    private static ArrayList<Character> maxPath = new ArrayList<>();

    // holds maximum score
    private static int maxScore = 0;

    // root node for creating tree
    private static Node root = null;

    // used for BFS traverse and search
    private static Queue<Node> q = new LinkedList<>();

    /**
     * Read files, creates boards, plays the game for all test cases, with BFS
     * finds the maximum score after 3 moves and then for the first case it writes
     * the file and then for the rest it appends the file.
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args) throws IOException{

        // call readFile method to start reading the input file
        readFile();

        // at this point boards are ready saved in boards array list, call play function
        // to start to play to find all possible moves and score
        play(boards.get(0));

        // hold size of the scores list, this is needed to create the tree as scores are saved
        // in level order, then call createTree method to build the tree
        int listSize = scores.size();
        createTree(scores, listSize);

        // start the BFS traversal and search tree for maximum cumulated score
        levelOrderTraversal(root);

        // write the max score and path in output file
        writeFile();

        // steps above are only for the first board in test cases input file
        // steps below are for rest of the test cases
        // these are separated because first time we WRITE the file but for
        // the rest we APPEND the file
        for (int i = 1; i < numberOfTests; i++){

            //clean scores, middle game boards, max score, max path, root,
            // and queue for more tests
            scores.removeAll(scores);
            middleGameBoards.removeAll(middleGameBoards);
            maxPath.removeAll(maxPath);
            maxScore = 0;
            root = null;
            q.removeAll(q);

            // play game, create tree, search tree, append output file with the result
            play(boards.get(i));
            listSize = scores.size();
            createTree(scores, listSize);
            levelOrderTraversal(root);
            appendFile();
        }
    }

    /**
     * The readFile method, read files from 2048_in.txt file. It read first line
     * first, then each 4 lines will be a board.
     * @throws IOException Handles exception for input and output file.
     */
    private static void readFile()throws IOException{

        // holds lines of file
        ArrayList<String> linesList = new ArrayList<>();

        // read file through scanner object
        File file = new File("2048_in.txt");
        Scanner inputFile = new Scanner(file);

        // read first line
        numberOfTests = Integer.parseInt(inputFile.nextLine());

        // read from 2nd line and add all of them in linesList array list
        while (inputFile.hasNext())
        {
            String line = inputFile.nextLine();
            linesList.add(line);
        }

        // close scanner
        inputFile.close();

        // pass linesList array list to processLines method to process each line
        processLines(linesList);
    }

    /**
     * The processLines methods processes each line, which is string, from
     * the linesList array list, converts them to int and pass them to buildBoard
     * method to create the matrix.
     * @param linesList An array list of lines/strings from input file
     */
    private static void processLines(ArrayList<String> linesList){

        final int SIZE = 4;                                 // holds size of the board
        String [] temp;                                     // holds a temporary string array
        ArrayList<int[]> intList = new ArrayList<>();       // holds the converted integers


        // remove all commas, convert digits in string to int
        for (int i = 0; i < linesList.size(); i++){
            temp = linesList.get(i).split(",");
            int [] tempInt = new int[SIZE];

            for(int j = 0; j < SIZE; j++){
                tempInt[j] = Integer.parseInt(temp[j]);
            }

            // add each int in an integer array
            intList.add(tempInt);
        }

        // pass the integer array to buildBoard method to create the matrix
        buildBoard(intList);
    }

    /**
     * The buildBoard method gets the integer array and builds a 2D array, which
     * is board in 2048 game.
     * @param intList An array of integers to be added to board/matrix
     */
    private static void buildBoard(ArrayList<int[]> intList){

        int counter = 0;            // while loop stopper

        //create 4by4 boards for each test case and hold all of them in boards
        final int SIZE = 4;
        while (counter < numberOfTests * SIZE){

            int [][] board = new int[SIZE][SIZE];

            for (int row = 0; row < SIZE; row++){
                for (int col = 0; col < SIZE; col++){
                board[row][col] = intList.get(counter)[col];
                }
                counter++;
            }

            // hold each test cases as a board in boards
            boards.add(board);
        }
    }

    /**
     * The moveUp methods have all 2048 game rules for the action move up.
     * This method also save the score after moving up in scores global variable.
     * @param board The 4by4 board which needs to move up.
     */
    private static void moveUp(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;

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
            }
            else if (board[row][col] == board[row+1][col]) {
                sum = board[row][col] + board[row+1][col];
                score += sum;
                board[row][col] = sum;
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
            }
            else if (board[row][col] == board[row+2][col] && board[row+1][col]==0){
                sum = board[row][col] + board[row+2][col];
                score += sum;
                board[row][col] = sum;
                board[row+1][col] = board[row+3][col];
                board[row+2][col] = 0;
                board[row+3][col] = 0;
            }
            else if (board[row][col] == board[row+3][col] && board[row+1][col]==0 && board[row+2][col]==0){
                sum = board[row][col] + board[row+3][col];
                score += sum;
                board[row][col] = sum;
                board[row+3][col] = 0;
            }
            else if (board[row+1][col] == board[row+2][col]){
                sum = board[row+1][col] + board[row+2][col];
                score += sum;
                board[row+1][col] = sum;
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
            }
            else if (board[row+1][col] == board[row+3][col] && board[row+2][col]==0){
                sum = board[row+1][col] + board[row+3][col];
                score += sum;
                board[row+1][col] = sum;
                board[row+3][col] = 0;
            }
            else if (board[row+2][col] == board[row+3][col]){
                sum = board[row+2][col] + board[row+3][col];
                score += sum;
                board[row+2][col] = sum;
                board[row+3][col] = 0;
            }

            if (board[row+2][col] == 0){
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
            }
            if (board[row+1][col] == 0){
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
            }
            if (board[row][col] == 0) {
                board[row][col] = board[row+1][col];
                board[row+1][col] = board[row+2][col];
                board[row+2][col] = board[row+3][col];
                board[row+3][col] = 0;
            }
        }

        // save the score and path after moving up in scores
        scores.add(score + "U");
    }

    /**
     * The moveDown methods have all 2048 game rules for the action move down.
     * This method also save the score after moving down in scores global variable.
     * @param board The 4by4 board which needs to move down.
     */
    private static void moveDown(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;

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
            }
            else if (board[row+3][col] == board[row+2][col]) {
                sum = board[row+3][col] + board[row+2][col];
                score += sum;
                board[row+3][col] = sum;
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
            }

            else if (board[row+3][col] == board[row+1][col] && board[row+2][col]==0){
                sum = board[row+3][col] + board[row+1][col];
                score += sum;
                board[row+3][col] = sum;
                board[row+2][col] = board[row][col];
                board[row+1][col] = 0;
                board[row][col] = 0;
            }
            else if (board[row+3][col] == board[row][col] && board[row+2][col]==0 && board[row+1][col]==0){
                sum = board[row+3][col] + board[row][col];
                score += sum;
                board[row+3][col] = sum;
                board[row][col] = 0;
            }

            else if (board[row+2][col] == board[row+1][col]){
                sum = board[row+2][col] + board[row+1][col];
                score += sum;
                board[row+2][col] = sum;
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
            }
            else if (board[row+2][col] == board[row][col] && board[row+1][col]==0){
                sum = board[row+2][col] + board[row][col];
                score += sum;
                board[row+2][col] = sum;
                board[row][col] = 0;
            }
            else if (board[row+1][col] == board[row][col]){
                sum = board[row+1][col] + board[row][col];
                score += sum;
                board[row+1][col] = sum;
                board[row][col] = 0;
            }

            if (board[row+1][col] == 0){
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
            }
            if (board[row+2][col] == 0){
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
            }
            if (board[row+3][col] == 0) {
                board[row+3][col] = board[row+2][col];
                board[row+2][col] = board[row+1][col];
                board[row+1][col] = board[row][col];
                board[row][col] = 0;
            }
        }
        // save the score and path after moving down in scores
        scores.add(score + "D");
    }

    /**
     * The moveRight methods have all 2048 game rules for the action move right.
     * This method also save the score after moving right in scores global variable.
     * @param board The 4by4 board which needs to move right.
     */
    private static void moveRight(int[][] board){

        int sum;                // holds sum after each possible move
        int score = 0;

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
            }
            else if (board[row][col+3] == board[row][col+2]) {
                sum = board[row][col+3] + board[row][col+2];
                score += sum;
                board[row][col+3] = sum;
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
            }
            else if (board[row][col+3] == board[row][col+1] && board[row][col+2]==0){
                sum = board[row][col+3] + board[row][col+1];
                score += sum;
                board[row][col+3] = sum;
                board[row][col+2]= board[row][col];
                board[row][col+1] = 0;
                board[row][col] = 0;
            }
            else if (board[row][col+3] == board[row][col] && board[row][col+2]==0 && board[row][col+1]==0){
                sum = board[row][col+3] + board[row][col];
                score += sum;
                board[row][col+3] = sum;
                board[row][col] = 0;
            }
            else if (board[row][col+2] == board[row][col+1]){
                sum = board[row][col+2] + board[row][col+1];
                score += sum;
                board[row][col+2] = sum;
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
            }
            else if (board[row][col+2] == board[row][col] && board[row][col+1]==0){
                sum = board[row][col+2] + board[row][col];
                score += sum;
                board[row][col+2] = sum;
                board[row][col] = 0;
            }
            else if (board[row][col+1] == board[row][col]){
                sum = board[row][col+1] + board[row][col];
                score += sum;
                board[row][col+1] = sum;
                board[row][col] = 0;
            }
            if (board[row][col+1] == 0){
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
            }
            if (board[row][col+2] == 0){
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
            }
            if (board[row][col+3] == 0) {
                board[row][col+3] = board[row][col+2];
                board[row][col+2] = board[row][col+1];
                board[row][col+1] = board[row][col];
                board[row][col] = 0;
            }
        }
        // save the score and path after moving right in scores
        scores.add(score + "R");
    }

    /**
     * The moveLeft methods have all 2048 game rules for the action move left.
     * This method also save the score after moving left in scores global variable.
     * @param board The 4by4 board which needs to move left.
     */
    private static void moveLeft(int[][] board){
        int sum;            // holds sum after each possible move
        int score = 0;

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
            }
            else if (board[row][col] == board[row][col+1]) {
                sum = board[row][col] + board[row][col+1];
                score += sum;
                board[row][col] = sum;
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
            }
            else if (board[row][col] == board[row][col+2] && board[row][col+1]==0){
                sum = board[row][col] + board[row][col+2];
                score += sum;
                board[row][col] = sum;
                board[row][col+1] = board[row][col+3];
                board[row][col+2] = 0;
                board[row][col+3] = 0;
            }
            else if (board[row][col] == board[row][col+3] && board[row][col+1]==0 && board[row][col+2]==0){
                sum = board[row][col] + board[row][col+3];
                score += sum;
                board[row][col] = sum;
                board[row][col+3] = 0;
            }
            else if (board[row][col+1] == board[row][col+2]){
                sum = board[row][col+1] + board[row][col+2];
                score += sum;
                board[row][col+1] = sum;
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
            }
            else if (board[row][col+1] == board[row][col+3] && board[row][col+2]==0){
                sum = board[row][col+1] + board[row][col+3];
                score += sum;
                board[row][col+1] = sum;
                board[row][col+3] = 0;
            }
            else if (board[row][col+2] == board[row][col+3]){
                sum = board[row][col+2] + board[row][col+3];
                score += sum;
                board[row][col+2] = sum;
                board[row][col+3] = 0;
            }

            if (board[row][col+2] == 0){
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
            }
            if (board[row][col+1] == 0){
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
            }
            if (board[row][col] == 0) {
                board[row][col] = board[row][col+1];
                board[row][col+1] = board[row][col+2];
                board[row][col+2] = board[row][col+3];
                board[row][col+3] = 0;
            }
        }
        // save the score and path after moving left in scores
        scores.add(score + "L");

    }

    /**
     * The addNumbers methods adds number 2 in the first empty spot. It finds the empty spot
     * after vertically scanning the passed board to it.
     * @param board The boards that 2 needs to be added to it.
     * @return The board after adding 2.
     */
    private static int[][] addNumbers(int[][] board){

        // Vertically scan to find the first empty spot
        // Break both loops when it is found and 2 is added to it.
        outerloop:
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                if (board[row][col] == 0) {
                    board[row][col] = 2;
                    break outerloop;
                }
            }
        }
        // return the board after adding 2 to it
        return board;
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

    /**
     * The play method starts the game. This method has layers and codes
     * related to each move of 3 moves.
     * @param board The board of current game to play
     */
    private static void play(int[][] board){

        // all possibilities for the first move
        moves(board);

        // all possibilities for the second move
        for (int i = 0; i < 4; i++)
            moves(middleGameBoards.get(i));

        // all possibilities for the third move
        int size = middleGameBoards.size();
        for (int j = 4; j < size; j++)
            moves(middleGameBoards.get(j));
    }

    /**
     * The moves method calls all 4 possible moves and saves the board
     * after each move in middleGameBoards to keep all the possibilities.
     * @param board The board that we are playing and testing.
     */
    private static void moves(int[][] board){
        int[][] tempBoard;

        // left move
        tempBoard = deepCopy2DArray(board);
        moveLeft(tempBoard);
        tempBoard = addNumbers(tempBoard);
        middleGameBoards.add(tempBoard);

        // right move
        tempBoard = deepCopy2DArray(board);
        moveRight(tempBoard);
        tempBoard = addNumbers(tempBoard);
        middleGameBoards.add(tempBoard);

        // up move
        tempBoard = deepCopy2DArray(board);
        moveUp(tempBoard);
        tempBoard = addNumbers(tempBoard);
        middleGameBoards.add(tempBoard);

        // down move
        tempBoard = deepCopy2DArray(board);
        moveDown(tempBoard);
        tempBoard = addNumbers(tempBoard);
        middleGameBoards.add(tempBoard);
    }

    /**
     * Node class is used in tree creation. Each node has 4 children while
     * holding data as string. The string data has score af the move following by
     * the first letter of action. For example if moving up resulted in score 4,
     * the data in the node as string will be 4U.
     */
    private static class Node
    {
        String data;
        Node left;
        Node right;
        Node up;
        Node down;
    }

    /**
     * The newNode method creates a new node.
     * @param value The string value has score af the move following by
     * the first letter of action. For example if moving up resulted in score 4,
     * the data in the node as string will be 4U.
     * @return n the new node
     */
    private static Node newNode(String value)
    {
        Node n = new Node();
        n.data = value;
        n.left = null;
        n.right = null;
        n.up = null;
        n.down = null;
        return n;
    }

    /**
     * The createTree method mainly calls insertValue method for all
     * elements in the list.
     * @param list The scores related to the current board.
     * @param listSize The size of the scores list
     */
    private static void createTree(ArrayList<String> list,
                                   int listSize)
    {
        // assign root
        insertValue("00");

        // call insertValue to add nodes and create the tree
        for (int i = 0; i < listSize; i++)
            insertValue(list.get(i));
    }

    /**
     * The insertValue gets the data of each node, adds the node to the
     * queue and creates the tree from the queue. All nodes will use same
     * queue.
     * @param value The string value of each node
     */
    private static void insertValue(String value)
    {
        // create new node and add value as data to it
        Node node = newNode(value);

        // check root
        if (root == null)
            root = node;

        // The left child of the current Node is used if it is available.
        else if (q.peek().left == null)
            q.peek().left = node;

        // The right child of the current Node is used if it is available.
        else if (q.peek().right == null)
            q.peek().right = node;

        // The up child of the current Node is used if it is available.
        else if (q.peek().up == null)
            q.peek().up = node;

        // The down child of the current Node is used if it is available.
        // Since the left, right, and up children of this node has already been used,
        // the Node is popped from the queue after using its down child.
        else
        {
            q.peek().down = node;
            q.remove();
        }

        // Whenever a new Node is added to the tree, its address is pushed into the
        // queue. So that its children Nodes can be used later.
        q.add(node);
    }

    /**
     * The levelOrderTraversal methods starts from root to travers the tree using
     * BFS. Also checks scores in each node and cumulates them to find the maximum
     * score.
     * @param root
     */
    private static void levelOrderTraversal(Node root)
    {
        if (root == null)
            return;

        // Standard level order traversal code using queue
        // Create a queue
        Queue<Node> q = new LinkedList<>();
        // Enqueue root
        q.add(root);

        while (!q.isEmpty())
        {
            int n = q.size();

            // If this node has children
            while (n > 0) {

                // TEST
                //Print head of queue for testing
                //Node peekedNode = q.peek();
                //System.out.print(peekedNode.data + " ||| ");

                // Enqueue all children of peeked node, pass the node data to number method
                // to extract number from the string and find max score
                if (q.peek().left != null) {
                    q.add(q.peek().left);
                    q.peek().left.data = q.peek().data + "," + q.peek().left.data;
                    number(q.peek().left.data);
                }
                if (q.peek().right != null) {
                    q.add(q.peek().right);
                    q.peek().right.data = q.peek().data + "," + q.peek().right.data;
                    number(q.peek().right.data);
                }
                if (q.peek().up != null){
                    q.add(q.peek().up);
                    q.peek().up.data = q.peek().data + "," + q.peek().up.data;
                    number(q.peek().up.data);
                }
                if (q.peek().down != null){
                    q.add(q.peek().down);
                    q.peek().down.data = q.peek().data + "," + q.peek().down.data;
                    number(q.peek().down.data);
                }
                q.remove();
                n--;
            }
            // TEST
            // Print new line between two levels
            // System.out.println();
        }
    }

    /**
     * The number methods takes the node data as string s, extracts the numbers
     * and adds them up to find the final score. If the sum is more than the saved
     * maxScore it updates the maxScore and calls path() method to find its path.
     * @param s The string containing the current node data.
     */
    private static void number(String s){

        int tempNum;            // holds a temporary number
        String temp = "0";      // holds a temporary string
        int sum = 0;            // holds sum of all numbers present in the string

        // read each character in input string
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // if current character is a digit
            if (Character.isDigit(ch))
                temp += ch;

            // if current character is an alphabet
            else {
                // increment sum by number found earlier, if any
                sum += Integer.parseInt(temp);

                // reset temporary string
                temp = "0";
            }
        }
        // atoi(temp.c_str()) takes care of trailing numbers
        tempNum = sum + Integer.parseInt(temp);

        // if the temporary number is bigger than the previous number save in the maxScore
        // change the maxScore and call path method to find the path of new maxScore.
        if (tempNum > maxScore){
            maxScore = tempNum;
            path(s);
        }
    }

    /**
     * The path method takes the data in the node that was known as maxScore, process the
     * string, removes commas and extract letters.
     * @param s The string data of the maxScore node.
     */
    private static void path(String s){

        // clean maxPath from previous path
        if (!maxPath.isEmpty())
            maxPath.removeAll(maxPath);

        // Extract characters from string and save them in maxPath.
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == 'L' || s.charAt(i) == 'R' || s.charAt(i) == 'U' || s.charAt(i) == 'D'){
                maxPath.add(s.charAt(i));
            }
        }
    }

    /**
     * The writeFile method writes the maximum score and its path according to the
     * requested format in an output file called 2048_out.txt.
     * @throws IOException Handles exception for input and output file.
     */
    private static void writeFile() throws IOException {
        String temp = "";

        // write via scanner
        PrintWriter writer = new PrintWriter("2048_out.txt");

        // the format requested by the assignment
        writer.print(maxScore + ",");
        for (int i = 0; i < maxPath.size(); i++){
            temp = temp + maxPath.get(i) + ",";
        }
        if(temp.length()!=0){
            temp = temp.substring(0, (temp.length()-1));
        }
        writer.print(temp);
        writer.close();
    }

    /**
     * The appendFile method appends the new maximum score and its path according to the
     * requested format in an output file called 2048_out.txt.
     * This method is used for more than 1 test cases.
     * @throws IOException Handles exception for input and output file.
     */
    private static void appendFile() throws IOException {
        String temp = "";

        FileWriter writer = new FileWriter("2048_out.txt", true);
        PrintWriter outputFile = new PrintWriter(writer);
        outputFile.println();
        outputFile.print(maxScore + ",");
        for (int i = 0; i < maxPath.size(); i++){
            temp = temp + maxPath.get(i) + ",";
        }
        temp = temp.substring(0, (temp.length()-1));
        outputFile.print(temp);
        outputFile.close();
    }
}