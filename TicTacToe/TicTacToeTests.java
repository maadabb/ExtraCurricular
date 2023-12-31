
import org.junit.jupiter.api.*;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    private static final int X = 1;
    private static final int O = 2;
    @Test
    /**
     * Test a square board to see if board has all 0's
     * Test should pass if all entries on the board are 0
     */
    public void testcreateBoard3x3() {
        int [][] expectedboard;
        int rows = 3;
        int cols = 3;
        expectedboard = new int[rows][cols];
        int[][] result = TicTacToe.createBoard(rows, cols);
        assertArrayEquals(expectedboard,result);

    }


    @Test
        /**
         * Test to see if a board with more rows than columns has all 0's
         * Test should pass if board has all 0's
         */
        public void testcreateBoard5x3() {
            int [][] expectedboard;
            int rows = 5;
            int cols = 3;
            expectedboard = new int[rows][cols];
            int[][] result = TicTacToe.createBoard(rows, cols);
            assertArrayEquals(expectedboard,result);

        }

        @Test
        /**
         * Test to see if a board with more columns than rows has all 0's
         * Test should pass if board has all 0's
         */
        public void testcreateBoard4x5() {
            int [][] emptyboard;
            int rows = 4;
            int cols = 5;
            emptyboard = new int[rows][cols];
            int[][] result = TicTacToe.createBoard(rows, cols);
            assertArrayEquals(emptyboard,result);

        }






        @Test
        /**
         * Test to see if a 4x5 board will correctly give the number of rows on the board
         * Test should pass if correct number of rows is returned
         */
        public void testrowsIn() {
            int rows = 4;
            int cols = 5;
            int [][] board = new int[rows][cols];
            int result = TicTacToe.rowsIn(board);
            assertEquals(rows,result);


        }

        @Test
        /**
         *Test to see if a 4x5 board will correctly give the number of columns in the board
         *Test should pass if correct number of columns is returned
         */
        public void testcolumnsIn() {
            int rows = 4;
            int colsexpected = 5;
            int[][]board = new int[rows][colsexpected];
            int result = TicTacToe.columnsIn(board);
            assertEquals(colsexpected,result);
        }
        @Test
        /**
         *Test to see if a piece can be played at a specific location on  the board when the value of the spot is 0
         *Test should pass if true is returned (the piece can be played)
         */
        public void canPlaytesttrue() {
            boolean expected = true;
            int [][] board = new int[4][4];
            int row = 3;
            int col = 2;
            board[row][col] = 0;
            boolean result = TicTacToe.canPlay(board, row, col);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Test to see if a piece can be played at a specific location on the board when the value is not 0
         * Test should pass if fail is returned (the piece cant be played at that location)
         */
        public void canPlaytestfalse() {
            boolean expected = false;
            int [][] board = new int[4][4];
            int row = 3;
            int col = 2;
            board[row][col] = 1;
            boolean result = TicTacToe.canPlay(board, row, col);
            assertEquals(expected,result);
        }

        @Test
        /**
         *Test to see if a piece can be placed in an empty spot
         * Test should pass if same notfull board equals full board.
         */
        public void playallowed() {
            int[][] notfull = new int[][]{{X,X,O,},
                                          {X,O,X},
                                          {X,0,X}};

            int[][] expected = new int[][] {{X,X,O,},
                    {X,O,X},
                    {X,O,X}};
            TicTacToe.play(notfull,2,1,O);
            assertArrayEquals(expected,notfull);

        }
        @Test
        /**
         *Test to see if a board filled with 0's is full
         *Test should return false for test to pass (the board is not full)
         */
        public void fullfalse() {
            boolean expected = false;
            int [][] board = new int[][] {{0,0,0},
                    {0,0,0},
                    {0,0,0}};
            boolean result = TicTacToe.full(board);
            assertEquals(expected,result);

        }


        @Test
        /**
         *Test to see if a board filled with X's and O's is full
         *Test should return true for test to pass (the board is full(
         */
        public void fulltrue() {
            boolean expected = true;
            int[][] board = new int[][] {{X,O,X},
                    {X,O,O},
                    {O,X,O}};
            boolean result = TicTacToe.full(board);
            assertEquals(expected,result);

        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the bottom row
         *Test should return true for the test to pass (You win in the bottom row)
         */
        public void wininlastRow() {
            boolean expected = true;
            int[][] board = new int[][] {{X,X,O,X},
                    {X,O,X,O},
                    {O,X,X,O},
                    {O,O,O,X}};
            int row = 3;
            boolean result = TicTacToe.wininRow(board, row, O);
            assertEquals(expected,result);
        }



        @Test
        /**
         *Testing if you get a win by getting three in a row in the first row
         *Test should return true for the test to pass (You win in the top row)
         */
        public void wininfirstRow() {
            boolean expected = true;
            int[][] board = new int[][] {{O,O,O,X},
                    {X,O,X,O},
                    {O,X,X,O},
                    {X,O,O,X}};
            int row = 0;
            boolean result = TicTacToe.wininRow(board, row, O);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the middle row
         *Test should return true for the test to pass (You win in the middle row)
         */
        public void wininmiddleRow() {
            boolean expected = true;
            int[][] board = new int[][] {{X,O,O,X},
                    {X,O,X,O},
                    {O,O,O,X},
                    {X,O,O,X}};
            int row = 2;
            boolean result = TicTacToe.wininRow(board, row, O);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the middle column
         * Test should return true for the test to pass (You win in the last column)
         */
        public void wininlastColumn() {
            boolean expected = true;
            int[][] board = new int[][] {{X,X,O,O},
                    {X,O,X,O},
                    {O,X,X,O},
                    {X,O,O,X}};
            int col = 3;
            boolean result = TicTacToe.wininColumn(board, col, O);
            assertEquals(expected,result);

        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the first column
         * Test should return true for the test to pass (You win the first column)
         */
        public void wininfirstColumn() {
            boolean expected = true;
            int[][] board = new int[][] {{O,X,O,X},
                    {O,O,X,O},
                    {O,X,O,X},
                    {X,O,O,O}};
            int col = 0;
            boolean result = TicTacToe.wininColumn(board, col, O);
            assertEquals(expected,result);

        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the middle column
         * Test should return true for the test to pass (You win in the middle column)
         */
        public void wininmiddleColumn() {
            boolean expected = true;
            int [][] board = new int[][] {{X,X,O,X},
                    {O,O,O,O},
                    {O,X,O,X},
                    {X,O,X,O}};
            int col = 2;
            boolean result = TicTacToe.wininColumn(board, col, O);
            assertEquals(expected,result);


        }
        @Test
        /**
         *Testing if you get a win by getting three in a row in the last column
         * Test should return true for the test to pass (You win in the last column)
         */
        public void winlastColumn() {
            boolean expected = true;
            int[][]board = new int[][] {{X,X,O,O},
                    {O,O,O,O},
                    {O,X,O,O},
                    {X,O,X,X}};
            int col = 3;
            boolean result = TicTacToe.wininColumn(board,col,O);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Testing to see if you get a win in the top left diagonal for black slash wins
         * Test should return true for the test to pass (You win in the top left diagonal)
         */
        public void winintopleftDiagonalBS(){
            boolean expected = true;
            int[][]board = new int[][] {{O,O,X,O},
                                        {X,O,X,O},
                                        {O,X,O,X},
                                        {X,X,O,X}};
            boolean result = TicTacToe.winInDiagonalBS(board, O);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Testing to see if you get a win in the bot right diagonal for black slash wins
         * Test should return true for the test to pass (You win in the bot right diagonal
         */
        public void wininbotrightDiagonalBS() {
            boolean expected = true;
            int[][] board = new int[][] {{X,X,O,X},
                    {O,O,X,O},
                    {X,X,O,X},
                    {O,X,O,O}};
            boolean result = TicTacToe.winInDiagonalBS(board, O);
            assertEquals(expected,result);

        }

        @Test
        /**
         *Testing to see if you get a win in the top right diagonal for forward slash wins
         * Test shuuld return true for the test to pass (You win in the top right diagonal)
         */
        public void winintoprightDiagonalFS() {
            boolean expected = true;
            int[][] board = new int[][] {{O,X,X,O},
                    {X,O,O,X},
                    {O,O,X,O},
                    {X,X,O,X}};
            boolean result = TicTacToe.winInDiagonalFS(board,O);
            assertEquals(expected,result);
        }
        @Test
        /**
         *Testing to see if you get a win in the bot left diagonal for forward slash wins
         * Test should return true for the test to pass (You win in the boy left diagonal)
         */
        public void wininbotleftDiagonalFS() {
            boolean expected = true;
            int[][] board = new int[][]{{X,O,X,X},
                    {X,X,O,O},
                    {O,O,X,X},
                    {O,O,X,O}};
            boolean result = TicTacToe.winInDiagonalFS(board, O);
            assertEquals(expected,result);
        }

    @Test
    /**
     *Testing to see if you get a win in the bot left diagonal for forward slash wins
     * Test should return true for the test to pass (You win in the boy left diagonal)
     */

    public void testHintinRow(){
        int[][] board = new int[][] {{O,O,0},
                                     {X,X,O,},
                                     {O,X,X}};
        int [] expected = {0,2};

        int[] result = TicTacToe.hint(board,O);
        assertArrayEquals(expected,result);

    }
    @Test
    /**
     *Testing to see if you get a win in the bot left diagonal for forward slash wins
     * Test should return true for the test to pass (You win in the boy left diagonal)
     */

    public void testHintinColumn(){
        int[][] board = new int[][] {{O,X,X},
                                     {O,X,O,},
                                     {0,O,X}};
        int [] expected = {2,0};

        int[] result = TicTacToe.hint(board,O);
        assertArrayEquals(expected,result);

    }
    @Test
    /**
     *Testing to see if you get a win in the bot left diagonal for forward slash wins
     * Test should return true for the test to pass (You win in the boy left diagonal)
     */

    public void testHintinDiagonal() {
        int[][] board = new int[][]{{O, X, 0},
                                    {X, O, O,},
                                    {O, O, X}};
        int[] expected = {0, 2};

        int[] result = TicTacToe.hint(board, O);
        assertArrayEquals(expected, result);
    }

        @Test
    /**
     *Testing to see when giving a value if it will return the factorial of that number
     * Test should return same value as expected in order to pass the test.
     */
    public void factorial(){
        int x = 4;
        BigInteger expected = new BigInteger("24");
        BigInteger result = TicTacToe.factorial(x);
        assertEquals(expected,result);

    }




    }





