package src;

import java.util.Scanner;

public class PlayGame {

    public static void main(String[] args) {
        AI AI = new AI();
        GameInstance gameInstance = new GameInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Who goes first? (1) AI (2) Player");
        int choose = scanner.nextInt();

        if(choose == 1){
            AI.doMiniMax(0, 1, gameInstance);
            gameInstance.board[AI.returnBestMove().x][AI.returnBestMove().y] = 1;
            gameInstance.displayBoard();

        }else if(choose == 2){
            System.out.println("Press For ROW (1,2 or 3)");
            int x = scanner.nextInt()-1;
            System.out.println("Press For COL (1,2 or 3)");
            int y = scanner.nextInt()-1;

            gameInstance.board[x][y] = 2;
            AI.doMiniMax(0, 1, gameInstance);
            gameInstance.board[AI.returnBestMove().x][AI.returnBestMove().y] = 1;
            gameInstance.displayBoard();

        }

        while (!gameInstance.isGameOver()) {
            System.out.println("Press For ROW (1,2 or 3)");
            int x = scanner.nextInt()-1;
            System.out.println("Press For COL (1,2 or 3)");
            int y = scanner.nextInt()-1;
            
            // Can place on points that are already on the board
            while(gameInstance.board[x][y] != 0){
                System.out.println("Wrong Move, Please Try Again ");
                System.out.println("Press For ROW (1,2 or 3)");
                x = scanner.nextInt()-1;
                System.out.println("Press For COL (1,2 or 3)");
                y = scanner.nextInt()-1;
            }

            gameInstance.board[x][y] = 2; // place move on board
            // gameInstance.displayBoard();

            if(gameInstance.isGameOver()){
                break;
            }

            AI.doMiniMax(0, 1, gameInstance);
            gameInstance.board[AI.returnBestMove().x][AI.returnBestMove().y] = 1;
            gameInstance.displayBoard();
        }

        if(gameInstance.isGameOver()){
            if(gameInstance.hasXWon()){
                System.out.println("AI Has Won, Sorry!");
            }else if(gameInstance.hasOWon()){
                System.out.println("You beat the AI! Nice Job");
            }
        }
    }
}


// when player places a move over the AI it should be