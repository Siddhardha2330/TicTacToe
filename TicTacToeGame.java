
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class TicTacToeGame {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;

    // Circular Queues to track the last 3 moves
    private Queue<Integer> playerXMoves; // Store user inputs (1-9)
    private Queue<Integer> playerOMoves;

    // HashMap to map user input (1-9) to board positions
    private Map<Integer, int[]> positionMap;

    public TicTacToeGame() {
        board = new Board();
        playerX = new Player('X');
        playerO = new Player('O');
        currentPlayer = playerX;

        playerXMoves = new LinkedList<>();
        playerOMoves = new LinkedList<>();

        initializePositionMap();
    }

    private void initializePositionMap() {
        positionMap = new HashMap<>();
        positionMap.put(1, new int[]{0, 0});
        positionMap.put(2, new int[]{0, 1});
        positionMap.put(3, new int[]{0, 2});
        positionMap.put(4, new int[]{1, 0});
        positionMap.put(5, new int[]{1, 1});
        positionMap.put(6, new int[]{1, 2});
        positionMap.put(7, new int[]{2, 0});
        positionMap.put(8, new int[]{2, 1});
        positionMap.put(9, new int[]{2, 2});
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        while (playing) {
            board.printBoard();

            // Notify the user if they already have 3 moves in the queue
            notifyNextRemoval();

            System.out.println("Player " + currentPlayer.getSymbol() + ", enter your move (1-9): ");
            int input = scanner.nextInt();

            // Validate input
            if (!positionMap.containsKey(input)) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                continue;
            }

            int[] position = positionMap.get(input);
            int row = position[0];
            int col = position[1];

            // Place the move
            if (!board.placeMove(row, col, currentPlayer.getSymbol())) {
                System.out.println("This move is not valid. Try again.");
                continue;
            }

            // Update moves
            updatePlayerMoves(input, row, col);

            // Check for win or draw
            if (board.checkWin(currentPlayer.getSymbol())) {
                board.printBoard();
                System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                playing = false;
            } else if (board.isFull()) {
                board.printBoard();
                System.out.println("The game is a draw!");
                playing = false;
            } else {
                // Switch players
                currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
            }
        }
    }

    private void notifyNextRemoval() {
        Queue<Integer> currentPlayerMoves = (currentPlayer == playerX) ? playerXMoves : playerOMoves;

        // Notify the user if their move queue already has 3 elements
        if (currentPlayerMoves.size() == 3) {
            int oldestInput = currentPlayerMoves.peek(); // Get the oldest move
            System.out.println("Note: Your next move will remove your oldest move (" + oldestInput + ").");
        }
    }

    private void updatePlayerMoves(int input, int row, int col) {
        Queue<Integer> currentPlayerMoves = (currentPlayer == playerX) ? playerXMoves : playerOMoves;

        currentPlayerMoves.offer(input); // Add the new move

        // If the queue exceeds 3 moves, remove the oldest move
        if (currentPlayerMoves.size() > 3) {
            int oldestInput = currentPlayerMoves.poll(); // Remove the oldest move
            int[] oldestPosition = positionMap.get(oldestInput); // Get the position
            board.clearMove(oldestPosition[0], oldestPosition[1]);
        }
    }
}