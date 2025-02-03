package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AI {

    List<Point> availablePoints;
    Map<Point, Integer> scoresToMoves;

    // Minimax
    public int returnMin(List<Integer> values) {
        int MIN = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) < MIN) {
                MIN = values.get(i);
                index = i;
            }
        }
        return values.get(index);
    }

    public int returnMax(List<Integer> values) {
        int MAX = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) > MAX) {
                MAX = values.get(i);
                index = i;
            }
        }
        return values.get(index);
    }

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

    public void doMiniMax(int depth, int player, GameInstance g) {
        scoresToMoves = new HashMap<Point, Integer>();
        miniMax(depth, player, g);
    }

    public int miniMax(int depth, int player, GameInstance g){
        // Utility function, Terminal State
        if (g.hasXWon()) return 1; // Maximizer
        if (g.hasOWon()) return -1; // Minimize

        List<Point> availablePoints = new ArrayList<Point>();
        availablePoints = g.avalPoints();
        if (availablePoints.isEmpty()) return 0; // Draw

        List<Integer> depthScore = new ArrayList<Integer>();

        for(int i = 0; i < availablePoints.size(); i++){
            Point currentMove = availablePoints.get(i);
            if(player == 1){ // AI
                g.board[currentMove.x][currentMove.y] = 1;
                int ScoreForThisMove = miniMax(depth+1,2, g);

                depthScore.add(ScoreForThisMove); // Will add when propogates back up

                if(depth == 0){
                 scoresToMoves.putIfAbsent(currentMove,ScoreForThisMove); // add final score for move
                }
            }
            else if(player == 2){ // Player
                g.board[currentMove.x][currentMove.y] = 2;
                depthScore.add(miniMax(depth+1, 1, g));
            }

            // reset board
            g.board[currentMove.x][currentMove.y] = 0;
        }

        // when we propogate back up, we need to know which score to pick
        return player == 1 ? returnMax(depthScore) : returnMin(depthScore);
    }
}
