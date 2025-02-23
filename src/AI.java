package src;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements Minimax algorithm.
 */
public class AI {

    Map<Point, Integer> scoresToMoves;
    static int TotalOperationsMinimax = 0;

    /**
     * Determines the best move based on the scores stored in the scoresToMoves map.
     *
     * @return The Point representing the best move.
     */
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

    /**
     * Initializes the scoresToMoves map and starts the Minimax algorithm.
     *
     * @param depth  The current depth in the game tree.
     * @param player The current player (1 for maximizer, 2 for minimizer).
     * @param g      The current game state.
     */
    public void doMiniMax(int depth, int player, GameState g) {
        scoresToMoves = new HashMap<Point, Integer>();
        miniMax(depth, player, g);
    }

    /**
     * Compute the Minimax algorithm.
     *
     * @param depth  The current depth in the game tree.
     * @param player The current player (1 for maximizer, 2 for minimizer).
     * @param g      The current game state.
     * @return The score of the best move for the current player.
     */
    public int miniMax(int depth, int player, GameState g) {
        // Utility function, Terminal State
        if (g.hasXWon())
            return 1; // Maximizer WIN
        if (g.hasOWon())
            return -1; // Minimize WIN
        if (g.avalPoints().isEmpty())
            return 0; // Draw

        if (player == 1) {

            int bestMaxScore = Integer.MIN_VALUE;

            for (int i = 0; i < g.avalPoints().size(); i++) { // iterate through available game positions
                Point currentMove = g.avalPoints().get(i); // get the current game move
                g.board[currentMove.x][currentMove.y] = 1; // place move on board

                TotalOperationsMinimax++;

                int currentScore = miniMax(depth + 1, 2, g); // get Score
                g.board[currentMove.x][currentMove.y] = 0; // reset board from next Move
                bestMaxScore = Math.max(currentScore, bestMaxScore); // get maximizing value // utility

                if (depth == 0) {
                    scoresToMoves.putIfAbsent(currentMove, currentScore);
                }
            }
            return bestMaxScore;

        } else {

            int bestMinScore = Integer.MAX_VALUE;

            for (int i = 0; i < g.avalPoints().size(); i++) {

                Point currentMove = g.avalPoints().get(i);
                g.board[currentMove.x][currentMove.y] = 2;

                TotalOperationsMinimax++;

                int currentScore = miniMax(depth + 1, 1, g); // get Score
                g.board[currentMove.x][currentMove.y] = 0;
                bestMinScore = Math.min(currentScore, bestMinScore); // get minimizing value // utility

            }
            return bestMinScore;
        }
    }
}
