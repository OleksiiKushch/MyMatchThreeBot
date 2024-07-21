package my.example.match3bot.algorithm;

import my.example.match3bot.utils.FromTo;

public interface Match3Alg {

    /**
     * Find the match three in the board.
     *
     * @param board the board to search for match three (matrix of numbers where each number represent an element)
     * @return coordinates of actions to perform the found match three ({@link FromTo}) or null if no match three found
     */
    FromTo find(int[][] board);
}
