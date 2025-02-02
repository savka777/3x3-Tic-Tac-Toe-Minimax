//You are supposed to add your comments

import java.util.*;

class AIplayer { 
    List<Point> availablePoints;
    List<PointsAndScores> rootsChildrenScores;
    Board b = new Board();
    
    public AIplayer() {
    }


    /* Returns the point that is associated with the best score */
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        // Iterate through the 
        for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    // Utility functions for maximizer's and minimizer's
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }
 
    public void callMinimax(int depth, int turn, Board b){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, b);
    }
    
    /* We call minimax in the TicTacToe class, the purpose of this is to, pick the successor move with the maximizing move for the AI
     * For evey move that the AI can make we keep track of the score it got + plus the move position.
     * We add the score + move position to a list called rootChildren scores. 
     * In order to know what successor score will be after recursively exploring the consequences of that move (predicting than the best move the player can make and back to AI and so forth)
     * We need to track the current score. When we backtrack from the recurise state of exploration, we return the score of the depth and it to the scores list.
     * In the depth, it is eighter the players turn or the AI's turn. If it's the AI, we need to maximize, therfore the score chosen at the depth, will be. 
     * The score that has maximum and vice versa for the player's depth. 
     * When we backtrack to the original depth of 0, we than add the results scores
     * 
     * Each depth returns a score.
        If it's AI's turn, pick the max score.
        If it's Player's turn, pick the min score.
        The scores propagate back to depth 0, where AI picks the best move.
    */

    public int minimax(int depth, int turn, Board b) {
        // Base Case Terminal States -1 = LOSE for AI, 1 = WIN for AI, 0 = TIE
        if (b.hasXWon()) return 1; // Maximizer
        if (b.hasOWon()) return -1; // Minimizer
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0; // draw

        /*Accumaltes all possible scores from each move the current player(AI, human) can make.
         * each call to minmax makes its own scores list.
         * If there a 3 avaliable point on the map, we will get a list of scores with size 3
         * When reaching depth 0, we will have the max of those 3 scores and will add that max score along side the position*/
        List<Integer> scores = new ArrayList<>(); 
        // System.out.println(scores.size());

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);  

            if (turn == 1) { 
                b.placeAMove(point, 1); 
                int currentScore = minimax(depth + 1, 2, b);  

                /* returns the score for this move, considering the exploration of ALL possible future moves */
                scores.add(currentScore); 

                if (depth == 0) // then, the propogated score and it's associated point is added to the points and scores
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                
            } else if (turn == 2) {
                b.placeAMove(point, 2); 
                scores.add(minimax(depth + 1, 1, b)); 
            }
            b.placeAMove(point, 0);  // undo the move for next simulation
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }    
}
