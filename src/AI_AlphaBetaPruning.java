package src;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements Minimax Alpha Beta Pruning algorithm.
 * This class prunes branches that do not need to be explored.
 */
public class AI_AlphaBetaPruning {

    Map<Point, Integer> scoresToMoves; // store the scores associated with each possible move
    static int TotalOperationsWithAlphaBeta = 0; // keep track amount of operations

    /**
     * Determines the best move based on the scores stored in the scoresToMoves map.
     *
     * @return The Point representing the best move.
     */
    public Point returnBestMove() {

        int maxPointsPerMove = Integer.MIN_VALUE;
        Point locationOfMaxPointPerMove = null;

        for (Map.Entry<Point, Integer> pair : scoresToMoves.entrySet()) { // get the best move
            if (pair.getValue() > maxPointsPerMove) {
                maxPointsPerMove = pair.getValue();
                locationOfMaxPointPerMove = pair.getKey();
            }
        }

        return locationOfMaxPointPerMove;
    }

    /**
     * Initializes the scoresToMoves map and starts the Minimax algorithm with
     * AlphaBeta Pruning.
     *
     * @param depth  The current depth in the game tree.
     * @param player The current player (1 for maximizer, 2 for minimizer).
     * @param g      The current game state.
     * @param alpha  The alpha value for pruning (best value for the maximizer).
     * @param beta   The beta value for pruning (best value for the minimizer).
     */
    public void doMiniMax(int depth, int player, GameState g, int alpha, int beta) {
        scoresToMoves = new HashMap<Point, Integer>();
        miniMax(depth, player, g, alpha, beta);
    }

    /**
     * Compute the Minimax algorithm with Alpha Beta Pruning.
     *
     * @param depth  The current depth in the game tree.
     * @param player The current player (1 for maximizer, 2 for minimizer).
     * @param g      The current game state.
     * @param alpha  The alpha value for pruning (best value for the maximizer).
     * @param beta   The beta value for pruning (best value for the minimizer).
     * @return The score of the best move for the current player.
     */
    public int miniMax(int depth, int player, GameState g, int alpha, int beta) {
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

                TotalOperationsWithAlphaBeta++;

                int currentScore = miniMax(depth + 1, 2, g, alpha, beta); // get Score
                g.board[currentMove.x][currentMove.y] = 0; // reset Board from next Move
                bestMaxScore = Math.max(currentScore, bestMaxScore); // get Maximizing Value // utility

                alpha = Math.max(alpha, bestMaxScore); // update alpha

                if (depth == 0) {
                    scoresToMoves.putIfAbsent(currentMove, currentScore);
                }

                if (alpha >= beta) {
                    break;
                }

            }
            return bestMaxScore;

        } else {

            int bestMinScore = Integer.MAX_VALUE;

            for (int i = 0; i < g.avalPoints().size(); i++) {

                Point currentMove = g.avalPoints().get(i);
                g.board[currentMove.x][currentMove.y] = 2;

                TotalOperationsWithAlphaBeta++;

                int currentScore = miniMax(depth + 1, 1, g, alpha, beta); // get Score
                g.board[currentMove.x][currentMove.y] = 0;
                bestMinScore = Math.min(currentScore, bestMinScore); // get Minimizing Value // Utility

                beta = Math.min(beta, bestMinScore); // update beta

                if (alpha >= beta) {
                    break;
                }

            }
            return bestMinScore;
        }
    }
}
