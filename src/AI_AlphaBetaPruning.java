package src;

import java.util.HashMap;
import java.util.Map;

public class AI_AlphaBetaPruning {

    Map<Point, Integer> scoresToMoves;
    static int TotalOperationsWithAlphaBeta = 0;

    public Point returnBestMove() {

        int maxPointsPerMove = Integer.MIN_VALUE;
        Point locationOfMaxPointPerMove = null;

        for (Map.Entry<Point, Integer> pair : scoresToMoves.entrySet()) {
            if (pair.getValue() > maxPointsPerMove) {
                maxPointsPerMove = pair.getValue();
                locationOfMaxPointPerMove = pair.getKey();
            }
        }
        
        return locationOfMaxPointPerMove;
    }

    public void doMiniMax(int depth, int player, GameInstance g , int alpha, int beta) {
        scoresToMoves = new HashMap<Point, Integer>();
        miniMax(depth, player, g, alpha, beta);
    }

    public int miniMax(int depth, int player, GameInstance g , int alpha, int beta) {
        // Utility function, Terminal State
        if (g.hasXWon())
            return 1; // Maximizer WIN
        if (g.hasOWon())
            return -1; // Minimize WIN
        if (g.avalPoints().isEmpty())
            return 0; // Draw

        if (player == 1) {

            int bestMaxScore = Integer.MIN_VALUE;
            
            for (int i = 0; i < g.avalPoints().size(); i++) { // Iterate through available game positions
                Point currentMove = g.avalPoints().get(i); // Get the current game move
                g.board[currentMove.x][currentMove.y] = 1; // Place move on board

                TotalOperationsWithAlphaBeta++;
                
                int currentScore = miniMax(depth + 1, 2, g, alpha, beta); // Get Score
                g.board[currentMove.x][currentMove.y] = 0; // Reset Board from next Move
                bestMaxScore = Math.max(currentScore, bestMaxScore); // Get Maximizing Value // Utility

                alpha = Math.max(alpha, bestMaxScore);

                if(depth == 0){
                    scoresToMoves.putIfAbsent(currentMove, currentScore);
                }

                if(alpha >= beta){
                    break;
                }
                
            }
            return bestMaxScore;

        } else {

            int bestMinScore = Integer.MAX_VALUE;
            
            for (int i = 0; i < g.avalPoints().size(); i++) {

                Point currentMove = g.avalPoints().get(i);
                g.board[currentMove.x][currentMove.y] = 2;
                
                TotalOperationsWithAlphaBeta ++;
                
                int currentScore = miniMax(depth + 1, 1, g, alpha, beta); // Get Score
                g.board[currentMove.x][currentMove.y] = 0;
                bestMinScore = Math.min(currentScore, bestMinScore); // Get Minimizing Value // Utility

                beta = Math.min(beta, bestMinScore); 

                if(alpha >= beta){
                    break;
                }

            }
            return bestMinScore;
        }
    }
}
