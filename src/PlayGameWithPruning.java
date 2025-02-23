package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PlayGameWithPruning {

    public static void writeToFile(File f, int input) {
        try {
            FileWriter w = new FileWriter(f, true);
            w.write("Total Minimax Explorations (With Alpha Beta Pruning): " + input + "\n");
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AI_AlphaBetaPruning AI = new AI_AlphaBetaPruning();
        GameState gameInstance = new GameState();
        Scanner scanner = new Scanner(System.in);
        File f = new File("src\\results.txt");

        gameInstance.clearScreen();
        System.out.println("Who goes first? (1) AI (2) Player");
        int choose = scanner.nextInt();

        if (choose == 1) {
            AI.doMiniMax(0, 1, gameInstance, Integer.MIN_VALUE, Integer.MIN_VALUE);
            gameInstance.board[AI.returnBestMove().x][AI.returnBestMove().y] = 1;
            gameInstance.displayBoard();

        } else if (choose == 2) {
            System.out.println("Press For ROW (1,2 or 3)");
            int x = scanner.nextInt() - 1;
            System.out.println("Press For COL (1,2 or 3)");
            int y = scanner.nextInt() - 1;

            gameInstance.board[x][y] = 2;
            AI.doMiniMax(0, 1, gameInstance, Integer.MIN_VALUE, Integer.MAX_VALUE);
            Point BestMove = AI.returnBestMove();
            gameInstance.board[BestMove.x][BestMove.y] = 1;
            gameInstance.displayBoard();

        }

        while (!gameInstance.isGameOver()) {
            System.out.println("Press For ROW (1,2 or 3)");
            int x = scanner.nextInt() - 1;
            System.out.println("Press For COL (1,2 or 3)");
            int y = scanner.nextInt() - 1;

            // Can place on points that are not empty // FIX INDEX OUT BOUND X = 10, etc.
            while (gameInstance.board[x][y] != 0) {
                System.out.println("Wrong Move, Please Try Again ");
                System.out.println("Press For ROW (1,2 or 3)");
                x = scanner.nextInt() - 1;
                System.out.println("Press For COL (1,2 or 3)");
                y = scanner.nextInt() - 1;
            }

            gameInstance.board[x][y] = 2; // place move on board
            // gameInstance.displayBoard();

            if (gameInstance.isGameOver()) {
                break;
            }

            AI.doMiniMax(0, 1, gameInstance, Integer.MIN_VALUE, Integer.MAX_VALUE);
            Point BestMove = AI.returnBestMove();
            gameInstance.board[BestMove.x][BestMove.y] = 1;
            gameInstance.displayBoard();
        }

        writeToFile(f, AI.TotalOperationsWithAlphaBeta);

        if (gameInstance.isGameOver()) {
            if (gameInstance.hasXWon()) {
                gameInstance.displayBoard();
                System.out.println("AI Has Won, Sorry!");
            } else if (gameInstance.hasOWon()) {
                gameInstance.displayBoard();
                System.out.println("You beat the AI! Nice Job");
            } else {
                gameInstance.displayBoard();
                System.out.println("DRAW!");

            }
        }
    }
}

// when player places a move over the AI it should be