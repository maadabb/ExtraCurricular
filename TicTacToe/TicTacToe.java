import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.math.*;
import java.util.*;
import java.util.List;

/**
 * A Class for a Modified Tic Tac Toe game
 * (for GUI purposes this class extends Canvas for drawing loop)
 * Allows user ot play a Tic Tac Toe game on boards with lengths of 3,4, or 5 for width/height.
 * A win is 3 in a row.
 * There is an implemented AI.
 * To complete this game @student will complete and test the functions required.
 *
 */
public class TicTacToe extends Canvas {

    //GAME PIECE CONSTANTS (public for visibility for JUnit Test)
    /**
     * No piece in board
     */
    public static final int EMPTY = 0;
    /**
     * Tic Tac Toe piece X
     */
    public static final int X = 1;
    /**
     * Tic Tac Toe piece O
     */
    public static final int O = 2;




    /**
     * Creates a 2d integer array in which the tik tak toe game can be played on.
     * All empty spots are represented by a 0
     *
     * @param row the rows inputed by the user
     * @param col the amount of columns inputed by the user
     * @return a 2d integer array named board which is where the game will be played
     */
    public static int[][] createBoard(int row, int col) {
        int[][] board = new int[row][col];
        for (int[] a : board) {
            for (int x : a) {
                System.out.print(x + "\t");
            }
            System.out.print("\n");
        }
        return board;
    }


    /**
     * Takes a board and returns the number of rows in that board
     *
     * @param board a 2d integer array in which the game will be played
     * @return a integer value of the number of rows in the 2d integer array board
     */
    public static int rowsIn(int[][] board) {
        int numrow = board.length;
        return numrow;
    }

    /**
     * Takes a board and returns the number of columns in that board
     *
     * @param board a 2d integer array in which the game will be played
     *              board
     */
    public static int columnsIn(int[][] board) {
        int numcol = board[0].length;
        return numcol;
    }


    /**
     * Checks if the player can place their piece at a specified location.
     *
     * @param board a 2d integer array in which the game will be played
     * @param row   a integer value that represent the number of rows in the board
     * @param col   a integer value that represent the number of columns in the board
     * @return false if the indicated spot is not empty (not 0)
     */
    public static boolean canPlay(int[][] board, int row, int col) {
        boolean canplay = false;
        if (board[row][col] == (EMPTY)) {
            canplay = true;

        }
        return canplay;
    }


    /**
     * Allows a player to place their piece at a specified location
     *
     * @param board a 2d integer array represting the tik tak toe board
     * @param row   an integer value representing number of rows
     * @param col   an integer value representing number of columns
     * @param piece the piece the player will play (X or O)
     */
    public static void play(int[][] board, int row, int col, int piece) {
        board[row][col] = piece;
    }


    /**
     * Checks to see if a 2d integer array is full (check if the tik tak toe board is full)
     *
     * @param board a 2d integer array that represents the tik tak toe board
     * @return true if the board is full
     */
    public static boolean full(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }

        }
        return true;

    }

    /**
     * Check to see if a player gets three in a row going horizontally
     *
     * @param board a 2d integer array which represents the tik tak toe board
     * @param row   an integer which represents the number of rows
     * @param piece The piece to look for 3 in a row for any row
     * @return false if a player does not get three in a row
     */
    public static boolean wininRow(int[][] board, int row, int piece) {
        int counter = 0;
        for (int i = 0; i < board[row].length; i++) {
            if (board[row][i] == piece) {
                counter += 1;
                if (counter == 3) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }

        return false;
    }




    /**
     * Check to see if a player gets three in a row going vertically
     *
     * @param board a 2d integer array which represents the tic tak toe board
     * @param col   an integer which represents the number of columns
     * @param piece The piece to look for 3 in a row for any column
     * @return false if a player does not get three in a row
     */
    public static boolean wininColumn(int[][] board, int col, int piece) {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == piece) {
                counter += 1;
                if (counter == 3) {
                    return true;
                }
            } else {
                counter = 0;
            }

        }
        return false;
    }




    /**
     * Check to see if a player gets three in a row diagonally. (This one is backlash therefore going from top left to bot right)
     *
     * @param board
     * @param piece The piece to look for 3 in a row for BS Diagonal
     * @return false if a player does not get three in a row
     */
    public static boolean winInDiagonalBS(int[][] board, int piece) {
        //top left corner to bottom right corner
        for (int row = 0; row < board.length - 2; row++) {
            for (int col = 0; col < board[row].length - 2; col++) {
                if (board[row][col] == piece && board[row + 1][col + 1] == piece && board[row + 2][col + 2] == piece)
                    return true;
            }
        }
        return false;

    }



    /**
     * Check to see if a player gets three in a row diagonally. (This one is forwardslash therefore going from top right to bot left)
     *
     * @param board a 2d integer array which represents the tik tak toe board
     * @param piece The piece to look for 3 in a row for FS Diagonal
     * @return false if a player does not get three in a row
     */
    public static boolean winInDiagonalFS(int[][] board, int piece) {
        for (int row = 0; row < board.length - 2; row++) {
            for (int col = 0; col < board[row].length - 2; col++) {
                if (board[row][col + 2] == piece && board[row + 1][col + 1] == piece && board[row + 2][col] == piece)
                    return true;
            }
        }
        return false;
    }


    /**
     * User will be asked if they want hints in the game. If the user does this function will give the user a hint when he is one away from winning.
     *
     * @param board a 2d integer array which represents the tik tak toe board
     * @param piece The piece placed by the user. We need 3 in a row of this piece
     * @return returns -1 for both row and column if no hint is found
     */
    public static int[] hint(int[][] board, int piece) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == EMPTY) {
                    play(board, row, col, piece);
                    if (won(board, piece)) {
                        board[row][col] = EMPTY;
                        return new int[]{row, col};
                    } else {
                        board[row][col] = EMPTY;
                    }

                }
            }
        }
        return new int[]{-1, -1};
    }



    /**
     * Is there a win in given board in any row of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for 3 in a row for any row
     * @return True if there is 3 in any row, False otherwise
     */
    private static boolean winInAnyRow(int[][] board, int piece) {
        for (int row = 0; row < board.length; row++) {
            if (wininRow(board, row, piece)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is there a win in given board in any column of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for 3 in a row for any column
     * @return True if there is 3 in any column, False otherwise
     */
    private static boolean winInAnyColumn(int[][] board, int piece) {
        for (int col = 0; col < board[0].length; col++) {
            if (wininColumn(board, col, piece)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is there a win in given board in any diagonal of board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to look for 3 in a row for any diagonal
     * @return True if there is 3 in any diagonal /\, False otherwise
     */
    private static boolean winInAnyDiagonal(int[][] board, int piece) {
        return winInDiagonalBS(board, piece) || winInDiagonalFS(board, piece);
    }

    /**
     * Has the given piece won the board
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @param piece The piece to check for a win
     * @return True if piece has won
     */
    private static boolean won(int[][] board, int piece) {
        return winInAnyRow(board, piece) || winInAnyColumn(board, piece) || winInAnyDiagonal(board, piece);
    }

    /**
     * This function determines if the game is complete due to a win or tie by either player
     *
     * @param board The 2D array board of size rows (dimension 1) and columns (dimension 2)
     * @return True if game is complete, False otherwise
     */
    private static boolean isGameOver(int[][] board) {
        return full(board) || won(board, X) || won(board, O);
    }





    //GAME BOARD SIZE (pixels)
    private static final int BOARD_SIZE = 600;
    private static final int WIDTH = BOARD_SIZE;
    private static final int HEIGHT = BOARD_SIZE;
    //DRAWING CONSTANTS
    //stroke of lines
    private static final int STROKE_SIZE = 10;
    private static final Stroke STROKE = new BasicStroke(STROKE_SIZE);
    //size of font
    private static final int FONT_SIZE = 50;
    private static final Font FONT = new Font("Times", Font.BOLD, FONT_SIZE);
    //border sizes
    private static final int X_O_PIXELS_BORDER = 15;
    private static final int TEXT_BORDER = 1;
    //colours
    private static final Color BGD_COLOUR = Color.white;
    private static final Color BOARD_COLOUR = Color.black;
    private static final Color HINT_COLOUR = Color.orange;
    private static final Color WIN_COLOUR = Color.green;
    private static final Color LOSE_COLOUR = Color.red;
    private static final Color TIE_COLOUR = Color.blue;
    //GAME DIFFICULTY PROMPT CONSTANTS
    private static final String DIFF_PROMPT_DEF = "Difficulties:\n" +
            "\t0\tAI plays randomly\n" +
            "\t1\tAI looks at its own and your next play\n" +
            "\t2\tAI looks two moves ahead for each player\n" +
            "\t3\tAI looks three moves ahead for each player";
    private static final String DIFF_PROMPT_3X3 = DIFF_PROMPT_DEF + "\n\t4\tAI looks ahead to end of game\n" +
            "\t\t(Note a difficulty of 4 uses an AI algorithm that may slow down some computers and you will have to wait.";
    private static final int MIN_AI = 0;
    private static final int MAX_AI = 3;
    private static final int MAX_AI_3X3 = 4;
    // GAME CONSTANTS
    private static final int MIN_BOARD_SIZE = 3;
    private static final int MAX_BOARD_SIZE = 5;
    //BOARD GAME VARIABLES
    //Getting input from user (keyboard)
    private static final Scanner scanner = new Scanner(System.in);
    //Getting input from user (GUI)
    private static final Integer[] mouse = new Integer[]{0, 0};
    //DRAWING GAME STATE VARIABLES
    private static Canvas canvas;
    private static Color draw_board_colour = BOARD_COLOUR;
    private static boolean draw_game_over = false;
    private static int draw_end_game_type = 0;
    private static int draw_winner_piece = EMPTY;
    private static boolean draw_hint = false;
    private static int draw_hint_piece = EMPTY;
    private static int[] draw_hint_location = null;
    //The board of the current game
    private static int[][] board;



    /**
     * Start Tic Tac Toe game
     *
     * @param args No arguments expected for this game
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            System.err.println("Program accepts no arguments!");
            System.exit(1);
        }
        try {
            // Get size of board from user and create the 2D array that is the board
            board = createBoard(inputRows(), inputColumns());
            //Setup GUI window
            setupWindow();
            //Run game loop
            run();
        } catch (Exception e) {
            System.out.println("Exception occurred running program!");
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Run the Tic Tac Toe game loop
     */
    private static void run() {

        //GET GAME SETUP
        // Get difficulty from the user (only 3x3 board allows full AI == 4)
        int difficulty_input = inputDifficulty(board);
        // Get user choice of piece (X goes first so picking X means user goes first, picking O means computer goes first
        int[] pieces = inputPlayerPiece();
        int human = pieces[0];
        int computer = pieces[1];
        // Ask what type of hints to give player
        String h = inputHintMode();
        // Now we can start the game?
        //Check if we want mouse input
        String gui_string;
        do {
            System.out.println("Enter G to enter input with mouse, otherwise use shell to play: ");
            gui_string = scanner.nextLine().trim();
        } while (!gui_string.equals("G") && !gui_string.equals(""));
        boolean gui_flag = gui_string.equals("G");

        //PLAY GAME
        System.out.println("Play a game!");
        int player = X;
        int plays = 0;
        //While game continues
        while (!isGameOver(board)) {
            //Determine complexity guess about game options remaining (no rotational considerations)
            int value = (board.length * board[0].length) - plays;
            if (value > 0) {
                BigInteger complexity = factorial(value);
                System.out.printf("Rough estimated complexity of current game: %d%n", complexity);
            }
            //If human then we have to collect input
            if (human == player) {
                System.out.println("Human player's turn.");
                // Get and draw hint
                inputHint(board, human, computer, h);
                //We ask for repaint as hint will have changed drawing variables
                canvas.repaint();
                // Depending on flag get input for user playing via GUI or via input() prompts in shell
                if (gui_flag) {
                    inputMouseNextPlay(board, human);
                } else {
                    inputScannerNextPlay(board, human);
                }
                //Turn off hint and redraw with human move made
                draw_hint = false;
                canvas.repaint();
                // Switch to other player
                player = computer;
            } else {
                //If AI then get the play and complete it
                int[] play = AI(board, computer, human, difficulty_input);
                int row = play[0];
                int col = play[1];
                play(board, row, col, computer);
                System.out.printf("AI plays at (%d,%d)%n", row, col);
                //Switch to other player
                player = human;
            }
            //Track each play for complexity update purposes
            plays += 1;
        }

        //HANDLE GAME IS COMPLETE
        //This last chunk determines drawing game state of end-game
        if (won(board, X)) {
            draw_winner_piece = X;
            if (human == X) {
                draw_end_game_type = 1;
            } else {
                draw_end_game_type = -1;
            }
        } else if (won(board, O)) {
            draw_winner_piece = O;
            if (human == O) {
                draw_end_game_type = 1;
            } else {
                draw_end_game_type = -1;
            }
        } else {
            draw_end_game_type = 0;
        }
        draw_game_over = true;
        //Repaint with end-game drawing setup to get colour/message of end game
        canvas.repaint();
        //Game is done
    }


    /**
     * Factorial value for n!
     * BigInteger used to let complexity size explode exponentially
     *
     * @param n The integer to calculate factorial for
     * @return BigInteger n! for input integer n
     */
    public static BigInteger factorial(int n) {
        if (n == 0) {
            return BigInteger.ONE;
        }
        BigInteger N = BigInteger.valueOf(n);
        BigInteger i = BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        while (i.compareTo(N) != 1) {
            // Accumulate n!
            result = result.multiply(i);
            i = i.add(BigInteger.ONE);
        }
        return result;
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO GET USER INPUT FROM THE TERMINAL
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Is this string a valid user input between start, and end integer inclusive
     *
     * @param value The value to check
     * @param start The start integer
     * @param end   The end integer inclusive
     * @return True if the value is in that range (inclusive start, end step size 1)
     */
    private static boolean isInputValid(String value, int start, int end) {
        // Lazy way to check if the given string is in an integer range without attempting to parse
        // This is a bad way to do this (only done to leave exception for students until later)
        // Very slow for large ranges (but unnoticeable for this program usage)
        for (int i = start; i <= end; i++) {
            if (value.equals(i + "")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Re-prompt user until a valid row count is given
     *
     * @return A valid int for row number
     */
    private static int inputRows() {
        String input_rows;
        do {
            System.out.printf("Pick a board row count in [%d,%d]: ", MIN_BOARD_SIZE, MAX_BOARD_SIZE);
            input_rows = scanner.nextLine().trim();
        } while (!isInputValid(input_rows, MIN_BOARD_SIZE, MAX_BOARD_SIZE));
        return Integer.parseInt(input_rows);
    }

    /**
     * Re-prompt user until a valid column count is given
     *
     * @return A valid int for column number
     */
    private static int inputColumns() {
        String input_columns;
        do {
            System.out.printf("Pick a board column count in [%d,%d]: ", MIN_BOARD_SIZE, MAX_BOARD_SIZE);
            input_columns = scanner.nextLine().trim();
        } while (!isInputValid(input_columns, MIN_BOARD_SIZE, MAX_BOARD_SIZE));
        return Integer.parseInt(input_columns);
    }

    /**
     * Get difficulty of AI for game from user
     *
     * @param board 2D array that is the game board
     * @return Integer difficulty from user
     */
    private static int inputDifficulty(int[][] board) {
        String difficulty_string;
        //We limit difficulty for boards that are not 3x3 (different message and upper limit)
        if (board.length == 3 && board[0].length == 3) {
            do {
                System.out.println(DIFF_PROMPT_3X3);
                System.out.print("Select a difficulty: ");
                difficulty_string = scanner.nextLine().trim();
            } while (!isInputValid(difficulty_string, MIN_AI, MAX_AI_3X3));
        }
        //Larger boards don't get best AI option
        else {
            do {
                System.out.println(DIFF_PROMPT_DEF);
                System.out.print("Select a difficulty: ");
                difficulty_string = scanner.nextLine().trim();
            } while (!isInputValid(difficulty_string, MIN_AI, MAX_AI));
        }
        return Integer.parseInt(difficulty_string);
    }

    /**
     * Get what piece the human and computer are, one is X, other is O
     *
     * @return Either (X, O) or (O, X) for (human, computer) based on user choice
     */
    private static int[] inputPlayerPiece() {
        while (true) {
            System.out.print("Enter choice of X or O: ");
            String piece_string = scanner.nextLine().trim();
            if (piece_string.equalsIgnoreCase("X")) {
                System.out.println("Human is X.");
                System.out.println("Computer is O.");
                return new int[]{X, O};
            } else if (piece_string.equalsIgnoreCase("O")) {
                System.out.println("Human is O.");
                System.out.println("Computer is X.");
                return new int[]{O, X};
            }
            System.out.printf("Previous entry \"%s\" was invalid!%n", piece_string);
        }
    }

    /**
     * Get what type of hint to give user
     *
     * @return "h" for winning hint, "" for no hint, "a" for hidden advanced AI hint
     */
    private static String inputHintMode() {
        String message = "Enter 'h' for game winning hints or <Enter> for no hints: ";
        String hint_mode_string;
        do {
            System.out.print(message);
            hint_mode_string = scanner.nextLine().trim();
        } while (!hint_mode_string.equals("h") && !hint_mode_string.equals("a") && !hint_mode_string.equals(""));
        return hint_mode_string;
    }

    /**
     * Plays the game via Scanner from user
     *
     * @param board The board of game
     * @param human The human's piece
     */
    private static void inputScannerNextPlay(int[][] board, int human) {
        while (true) {
            //Get a row and column input that fit in board
            String input_row_string = null;
            while (input_row_string == null || !isInputValid(input_row_string, 0, board.length - 1)) {
                System.out.printf("Enter row: %n");
                input_row_string = scanner.nextLine().trim();
            }
            String input_column_string = null;
            while (input_column_string == null || !isInputValid(input_column_string, 0, board[0].length - 1)) {
                System.out.printf("Enter column: %n");
                input_column_string = scanner.nextLine().trim();
            }
            int row = Integer.parseInt(input_row_string);
            int col = Integer.parseInt(input_column_string);
            System.out.printf("User entered (%d,%d)%n", row, col);
            //Check if play is validly open before accepting
            if (canPlay(board, row, col)) {
                play(board, row, col, human);
                return;
            } else {
                System.err.printf("Chosen location (%d,%d) is full!%n", row, col);
            }
        }
    }

    /**
     * Based on hinting mode we will get and show hint on board for the game
     *
     * @param board    The 2D array board in which hint should be found
     * @param human    The human piece
     * @param computer The computer piece
     * @param h        The hint mode 'h','a' or ''
     */
    private static void inputHint(int[][] board, int human, int computer, String h) {
        //Basic look-head to block opponent win or win
        if (h.equals("h")) {
            System.out.println("Wait for hint");
            //Get human win, CPU win hints if they exist
            int[] immediate_win_hint = hint(board, human);
            int[] opponent_win_block = hint(board, computer);
            //Tell player to win if that is there
            if (immediate_win_hint[0] != -1) {
                System.out.printf("Hint to win in (%d,%d)%n", immediate_win_hint[0], immediate_win_hint[1]);
                draw_hint = true;
                draw_hint_location = immediate_win_hint;
                draw_hint_piece = human;
            }
            //Tell player to block CPU if that exists second
            else if (opponent_win_block[0] != -1) {
                System.out.printf("Hint to stop opponent win in (%d,%d)%n", opponent_win_block[0], opponent_win_block[1]);
                draw_hint = true;
                draw_hint_location = opponent_win_block;
                draw_hint_piece = human;
            }
            //Otherwise, no hint
            else {
                System.out.println("No regular hint");
            }
        }
        //AI hint, which will be limited by board size
        else if (h.equals("a")) {
            //Get a hint for human that is a win
            int[] immediate_win_hint = hint(board, human);
            int[] hint;
            //Use immediate win hint if it exists
            if (immediate_win_hint[0] != -1) {
                System.out.println("Wait for hint (quick)");
                hint = immediate_win_hint;
            }
            //Otherwise, if 3x3 board do an AI search for whole board
            else if (board.length == 3 && board[0].length == 3) {
                System.out.println("Wait for hint (really slow!)");
                hint = AI(board, human, computer, 4);
            }
            //If not 3x3 then only do AI search for 3 plays ahead
            else {
                System.out.println("Wait for hint (slow)");
                hint = AI(board, human, computer, 3);
            }
            //If hint found then show this hint
            if (hint[0] != -1) {
                System.out.printf("Hint is (%d,%d)%n", hint[0], hint[1]);
                draw_hint = true;
                draw_hint_location = hint;
                draw_hint_piece = human;
            } else {
                System.out.println("No advanced hint");
            }
        }
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO GET USER INPUT FROM THE GUI
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Plays the game via GUI mouse click from user
     *
     * @param board The board of game
     * @param human The human's piece
     */
    private static void inputMouseNextPlay(int[][] board, int human) {
        //We'll need to use these variables to determine which square was clicked on
        int square_height = HEIGHT / board.length;
        int square_width = WIDTH / board[0].length;
        //Loop until a click on the board
        while (true) {
            //We wait on a mouse event on board
            try {
                synchronized (mouse) {
                    mouse.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // From the stored mouse value determine a row and column
            int row = (mouse[1] / square_height);
            int col = (mouse[0] / square_width);
            //If math says the row,col calculation isn't in board then loop for another click
            if (row < 0 || row > board.length - 1) {
                continue;
            }
            if (col < 0 || col > board[0].length - 1) {
                continue;
            }
            //If we can play in this location we make this play and return to game
            if (canPlay(board, row, col)) {
                play(board, row, col, human);
                System.out.printf("User entered (%d,%d)%n", row, col);
                return;
            }
        }
    }

    /**
     * This is a hidden internal class to enable a Mouse listener
     * We only need the mouse clicked event in this program but are required to implement all the others
     */
    private static class MyMouse extends JComponent implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //When a mouse is clicked we stored that value and notify anyone that might be waiting for it
            mouse[0] = e.getX();
            mouse[1] = e.getY();
            synchronized (mouse) {
                mouse.notifyAll();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED TO DRAW THE TIC TAC TOE GAME
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * This allows us to re-paint the GUI window regularly (we have to update game state variables to draw different things
     *
     * @param graphics The graphics given when painting is requested
     */
    @Override
    public void paint(Graphics graphics) {
        drawBoard((Graphics2D) graphics);
    }

    /**
     * Set up the Java Swing window (really simple window a canvas and a mouse listener)
     */
    private static void setupWindow() {
        JFrame frame = new JFrame("Modified Tic Tac Toe Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        canvas = new TicTacToe();
        canvas.setSize(WIDTH, HEIGHT);
        canvas.setBackground(BGD_COLOUR);
        canvas.setBackground(BGD_COLOUR);
        canvas.setSize(WIDTH, HEIGHT);
        frame.add(canvas);
        Component mouseClick = new MyMouse();
        canvas.addMouseListener((MouseListener) mouseClick);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This handles the bulk of load drawing the GUI game
     *
     * @param graphics The graphics we are drawing to
     */
    private static void drawBoard(Graphics2D graphics) {
        // Clear board to white before redraw
        graphics.clearRect(0, 0, WIDTH, HEIGHT);
        // Get size of a box
        int row_pixel_size = (HEIGHT / board.length);
        int col_pixel_size = (WIDTH / board[0].length);
        // Now draw board in given colour (have to determine if game is over or not)
        //These were all setup elsewhere
        if (draw_game_over) {
            if (draw_end_game_type == 1) {
                draw_board_colour = WIN_COLOUR;
            } else if (draw_end_game_type == 0) {
                draw_board_colour = TIE_COLOUR;
            } else {
                draw_board_colour = LOSE_COLOUR;
            }
        } else {
            draw_board_colour = BOARD_COLOUR;
        }
        graphics.setColor(draw_board_colour);
        //Draw the squares
        graphics.setStroke(STROKE);
        graphics.drawRect(0, 0, WIDTH, HEIGHT);
        // Draw horizontal lines
        for (int y = row_pixel_size; y < HEIGHT - 1; y += row_pixel_size) {
            graphics.drawLine(0, y, WIDTH, y);
        }
        // Draw vertical lines
        for (int x = col_pixel_size; x < WIDTH - 1; x += col_pixel_size) {
            graphics.drawLine(x, 0, x, HEIGHT);
        }
        // Draw any pieces played in board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == X) {
                    drawX(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, draw_board_colour);
                } else if (board[row][col] == O) {
                    drawO(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, draw_board_colour);
                }
            }
        }
        //If there is a hint triggered then draw it
        if (draw_hint) {
            drawHint(graphics);
        }
        //Draw the text for game over (most of this code is trying to center it)
        graphics.setFont(FONT);
        if (draw_game_over) {
            String message = "Board full. Tie Game.";
            if (draw_end_game_type != 0) {
                String piece = draw_winner_piece == X ? "X" : "O";
                message = piece + " won!";
            }
            //Use font metrics to try and center the text over a white box for readability (text and fonts are messy, sigh)
            Rectangle2D rect = graphics.getFontMetrics().getStringBounds(message, graphics);
            int stringLen = (int) rect.getWidth();
            int font_ascent = graphics.getFontMetrics().getMaxAscent();
            int font_descent = graphics.getFontMetrics().getMaxDescent();
            int start_x = WIDTH / 2 - stringLen / 2;
            int start_y_rect = HEIGHT / 2 - font_ascent / 2;
            int start_y_text = HEIGHT / 2 + font_ascent / 2;
            graphics.setStroke(new BasicStroke(0));
            graphics.setColor(BGD_COLOUR);
            graphics.fillRect(start_x - TEXT_BORDER, start_y_rect - TEXT_BORDER, stringLen + TEXT_BORDER * 2, font_ascent + font_descent + TEXT_BORDER * 2);
            graphics.setColor(BOARD_COLOUR);
            graphics.drawString(message, start_x, start_y_text);
        }
    }

    /**
     * Draw X with lines in box beginning at (x,y) with given square size and color
     * Uses X_O_PIXELS_BORDER to create border to X
     *
     * @param graphics Where we are drawing to
     * @param x        The x pixel location of top left of box to draw X in
     * @param y        The y pixel location of top left of box to draw X in
     * @param size_x   The pixel width of the box to draw X in
     * @param size_y   The pixel height of the box ot draw X in
     * @param colour   The color to draw the lines of the X in
     */
    private static void drawX(Graphics2D graphics, int x, int y, int size_x, int size_y, Color colour) {
        graphics.setColor(colour);
        graphics.setStroke(STROKE);
        graphics.drawLine(x + X_O_PIXELS_BORDER, y + X_O_PIXELS_BORDER, x + size_x - X_O_PIXELS_BORDER, y + size_y - X_O_PIXELS_BORDER);
        graphics.drawLine(x + size_x - X_O_PIXELS_BORDER, y + X_O_PIXELS_BORDER, x + X_O_PIXELS_BORDER, y + size_y - X_O_PIXELS_BORDER);


    }

    /**
     * Draw O with lines in box beginning at (x,y) with given square size and color
     * Uses X_O_PIXELS_BORDER to create border to O
     *
     * @param graphics Where we are drawing to
     * @param x        The x pixel location of top left of box to draw O in
     * @param y        The y pixel location of top left of box to draw O in
     * @param size_x   The pixel width of the box to draw O in
     * @param size_y   The pixel height of the box ot draw O in
     * @param colour   The color to draw the lines of the O in
     */
    private static void drawO(Graphics2D graphics, int x, int y, int size_x, int size_y, Color colour) {
        graphics.setColor(colour);
        graphics.setStroke(STROKE);
        graphics.drawOval(x + X_O_PIXELS_BORDER, y + X_O_PIXELS_BORDER, size_x - X_O_PIXELS_BORDER * 2, size_y - X_O_PIXELS_BORDER * 2);
    }

    /**
     * Draw hint information and X or O based on piece in given row, col of board
     * Quite similar to pieces from drawBoard/drawX/drawO as we are drawing a sub-part of board in different colour for hint
     *
     * @param graphics Where we are drawing to
     */
    private static void drawHint(Graphics2D graphics) {
        int row = draw_hint_location[0];
        int col = draw_hint_location[1];
        int piece = draw_hint_piece;
        graphics.setColor(HINT_COLOUR);
        graphics.setStroke(STROKE);
        // Get size of a box
        int row_pixel_size = (HEIGHT / board.length);
        int col_pixel_size = (WIDTH / board[0].length);
        graphics.drawRect(col * col_pixel_size, row * row_pixel_size, col_pixel_size + 1, row_pixel_size + 1);
        if (piece == X) {
            drawX(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, HINT_COLOUR);
        } else if (piece == O) {
            drawO(graphics, col * col_pixel_size, row * row_pixel_size, col_pixel_size, row_pixel_size, HINT_COLOUR);
        }
    }


    /*----------------------------------------------------------------------------------------------------------
     * THIS CODE IS USED FOR THE AI
     * ---------------------------------------------------------------------------------------------------------- */

    /**
     * Calling AI, if level 4 we do full recursive minimax, if not we recurse only to certain depth
     * If level=0 AI we just pick random open spot
     *
     * @param original_board The 2D array board in which game is being played
     * @param player1        The piece of player1, X/O
     * @param player2        The piece of player2, the other of X/O
     * @param level          The difficultly level of AI
     * @return A (row, col) spot to play at
     */
    private static int[] AI(int[][] original_board, int player1, int player2, int level) {
        if (player1 != X && player1 != O) {
            throw new RuntimeException("AI player1 should be X/O not {player1}");
        }
        if (player2 != X && player2 != O) {
            throw new RuntimeException("AI player2 should be X/O not {player2}");
        }
        if (player1 == X && player2 != O) {
            throw new RuntimeException("AI player1/player2 can't be X/X");
        }
        if (player1 == O && player2 != X) {
            throw new RuntimeException("AI player1/player2 can't be O/O");
        }
        if (level < 0 || level > 4) {
            throw new RuntimeException("AI level has to be 0 <= level <= 4");
        }
        //Create copy of board for safety of AI not modifying input board
        int[][] board = new int[original_board.length][];
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(original_board[i], original_board[i].length);
        }
        //Do unlimited lookahead if level >= 4
        if (level == 4) {
            Integer[] result = minimax(board, player1, player2, player1, 1, Integer.MAX_VALUE);
            return new int[]{result[0], result[1]};
        }
        //Otherwise, multiply level by two(to get plays by each side for each level)
        else if (level > 0) {
            Integer[] result = minimax(board, player1, player2, player1, 1, level * 2 + 1);
            return new int[]{result[0], result[1]};
        } else {
            //Otherwise random
            java.util.List<Integer[]> moves = openMoves(board);
            Collections.shuffle(moves);
            for (Integer[] move : moves) {
                if (canPlay(board, move[0], move[1])) {
                    return new int[]{move[0], move[1]};
                }
            }
        }
        //There is no move to be made
        return new int[]{-1, -1};
    }

    /**
     * Get all open moves in the board (i.e. BLANK spots)
     *
     * @param board The 2D array board to get open moves from (playable spots)
     * @return A list of (row, col) move locations that are open to be played in
     */
    private static java.util.List<Integer[]> openMoves(int[][] board) {
        java.util.List<Integer[]> moves = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (canPlay(board, row, col)) {
                    moves.add(new Integer[]{row, col});
                }
            }
        }
        return moves;
    }

    /**
     * Minimax suggest of what row, col to play in for player1 as initial call, and player as current tree call
     *
     * @param board     2D array which is board game is being played in
     * @param player1   Player 1 piece, X/O
     * @param player2   Player 2 piece, the other of X/O
     * @param player    The player current playing
     * @param depth     The depth of the minimax
     * @param max_depth The max depth of the minimax
     * @return The score of this path
     */
    private static Integer[] minimax(int[][] board, int player1, int player2, int player, int depth, int max_depth) {
        Integer[] best;
        //We will be either maximizing value if player1 called AI
        if (player == player1) {
            best = new Integer[]{null, null, Integer.MIN_VALUE, 1};
        }
        //Or minimizing if player2 did
        else {
            best = new Integer[]{null, null, Integer.MAX_VALUE, 1};
        }
        //If we run out of depth or game ends then get board state
        if (depth == max_depth || isGameOver(board)) {
            int score = evaluate(board, depth, player1, player2);
            return new Integer[]{null, null, score, 1};
        }
        //Get all open moves
        List<Integer[]> moves = openMoves(board);
        for (Integer[] move : moves) {
            int row = move[0];
            int col = move[1];
            //Make play
            play(board, row, col, player);
            //Set next player to be other guy
            int next_player;
            if (player == player1) {
                next_player = player2;
            } else {
                next_player = player1;
            }
            //Get score by exploring down tree
            Integer[] score = minimax(board, player1, player2, next_player, depth + 1, max_depth);
            score[0] = row;
            score[1] = col;
            //Undo the play
            play(board, row, col, EMPTY);
            //Depending on if we are currently on player1 or player 2 we update the best upwards, or downwards
            if (player == player1) {
                if (score[2] > best[2]) {
                    best = score;
                }
            } else {
                if (score[2] < best[2]) {
                    best = score;
                }
            }
        }
        return best;
    }

    /**
     * Evaluate the board, 10 for player 1 win, -10 for player 1 loses, 0 for neutral
     * We adjust this starting score about how far away this win is, so that the result is a struggle
     *
     * @param board   The 2D array board to evaluate
     * @param player1 The piece of player 1, X/O
     * @param player2 The piece of player 2, the other of X/O
     * @return The value 0 -> tied, 1 -> player 1 win, -1 -> player 1 loses
     */
    private static int evaluate(int[][] board, int depth, int player1, int player2) {
        int score = 0;
        if (won(board, player1)) {
            score = 10 - depth;
        } else if (won(board, player2)) {
            score = depth - 10;
        }
        return score;
    }
}


