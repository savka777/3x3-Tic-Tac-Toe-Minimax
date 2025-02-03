package src;

import java.util.ArrayList;
import java.util.List;

class Point{
    int x;
    int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}

public class GameInstance {

    int[][] board;
    List<Point> availablePositions;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    

    public GameInstance() {
        this.board = new int[3][3];
    }

    public boolean isGameOver() {
        if (this.hasXWon() || this.hasOWon() || avalPoints().isEmpty()){
            return true;
        }
        return false;
    }

    public List<Point> avalPoints(){
        this.availablePositions = new ArrayList<Point>();
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board.length; j++){
                if(this.board[i][j] == 0){
                    availablePositions.add(new Point(i, j));
                }
            }
        }
        return availablePositions;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2)
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1)
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                return true;
            }
        }
        return false;
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public void displayBoard(){
        clearScreen();
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board.length; j++){
                if(board[i][j] == 1){
                    System.out.print(ANSI_RED + "X" + " " + ANSI_RESET );
                }else if(board[i][j] == 2){
                    System.out.print(ANSI_GREEN + "O" + " " + ANSI_RESET);
                }else{
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}