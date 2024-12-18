
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Character>> board;

    public Board() {
        board = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(' '); 
            }
            board.add(row);
        }
    }

    public void printBoard() {
        System.out.println("Current board:");
        for (List<Character> row : board) {
            System.out.print(" | ");
            for (char cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public boolean placeMove(int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board.get(row).get(col) == ' ') {
            board.get(row).set(col, player);
            return true;
        }
        return false;
    }

    public void clearMove(int row, int col) {
        board.get(row).set(col, ' ');
    }

    public boolean checkWin(char player) {
    
        for (int i = 0; i < 3; i++) {
            if ((board.get(i).get(0) == player && board.get(i).get(1) == player && board.get(i).get(2) == player) ||
                (board.get(0).get(i) == player && board.get(1).get(i) == player && board.get(2).get(i) == player)) {
                return true;
            }
        }
       
        return (board.get(0).get(0) == player && board.get(1).get(1) == player && board.get(2).get(2) == player) ||
               (board.get(0).get(2) == player && board.get(1).get(1) == player && board.get(2).get(0) == player);
    }

    public boolean isFull() {
        for (List<Character> row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false; 
                }
            }
        }
        return true; 
    }
}
