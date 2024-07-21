package my.example.match3bot.algorithm.impl;

import my.example.match3bot.utils.FromTo;
import my.example.match3bot.algorithm.Match3Alg;

public class BruteForce1Match3Alg implements Match3Alg {

    @Override
    public FromTo find(int[][] board) {
        int y, x;

        for (y = 0; y < board.length; y++) {
            for (x = 0; x < board.length - 3; x++) {
                if (board[y][x] == board[y][x + 1] && board[y][x] == board[y][x + 3]) {
                    return new FromTo(y, x + 3, y, x + 2);
                }
            }
        }

        for (y = 0; y < board.length; y++) {
            for (x = 3; x < board.length; x++) {
                if (board[y][x] == board[y][x - 1] && board[y][x] == board[y][x - 3]) {
                    return new FromTo(y, x - 3, y, x - 2);
                }
            }
        }

        for (x = 0; x < board.length; x++) {
            for (y = 0; y < board.length - 3; y++) {
                if (board[y][x] == board[y + 1][x] && board[y][x] == board[y + 3][x]) {
                    return new FromTo(y + 3, x, y + 2, x);
                }
            }
        }

        for (x = 0; x < board.length; x++) {
            for (y = 3; y < board.length; y++) {
                if (board[y][x] == board[y - 1][x] && board[y][x] == board[y - 3][x]) {
                    return new FromTo(y - 3, x, y - 2, x);
                }
            }
        }

        for (y = 0; y < board.length - 1; y++) {
            for (x = 0; x < board.length - 2; x++) {
                if (board[y][x] == board[y][x + 1] && board[y][x] == board[y + 1][x + 2]) {
                    return new FromTo(y + 1, x + 2, y, x + 2);
                }
                if (board[y][x] == board[y][x + 2] && board[y][x] == board[y + 1][x + 1]) {
                    return new FromTo(y + 1, x + 1, y, x + 1);
                }
                if (board[y][x + 1] == board[y][x + 2] && board[y][x + 1] == board[y + 1][x]) {
                    return new FromTo(y + 1, x, y, x);
                }
            }
        }

        for (y = 1; y < board.length; y++) {
            for (x = 0; x < board.length - 2; x++) {
                if (board[y][x] == board[y][x + 1] && board[y][x] == board[y - 1][x + 2]) {
                    return new FromTo(y - 1, x + 2, y, x + 2);
                }
                if (board[y][x] == board[y][x + 2] && board[y][x] == board[y - 1][x + 1]) {
                    return new FromTo(y - 1, x + 1, y, x + 1);
                }
                if (board[y][x + 1] == board[y][x + 2] && board[y][x + 1] == board[y - 1][x]) {
                    return new FromTo(y - 1, x, y, x);
                }
            }
        }

        for (x = 0; x < board.length - 1; x++) {
            for (y = 0; y < board.length - 2; y++) {
                if (board[y][x] == board[y + 1][x] && board[y][x] == board[y + 2][x + 1]) {
                    return new FromTo(y + 2, x + 1, y + 2, x);
                }
                if (board[y][x] == board[y + 2][x] && board[y][x] == board[y + 1][x + 1]) {
                    return new FromTo(y + 1, x + 1, y + 1, x);
                }
                if (board[y + 1][x] == board[y + 2][x] && board[y + 1][x] == board[y][x + 1]) {
                    return new FromTo(y, x + 1, y, x);
                }
            }
        }

        for (x = 1; x < board.length; x++) {
            for (y = 0; y < board.length - 2; y++) {
                if (board[y][x] == board[y + 1][x] && board[y][x] == board[y + 2][x - 1]) {
                    return new FromTo(y + 2, x - 1, y + 2, x);
                }
                if (board[y][x] == board[y + 2][x] && board[y][x] == board[y + 1][x - 1]) {
                    return new FromTo(y + 1, x - 1, y + 1, x);
                }
                if (board[y + 1][x] == board[y + 2][x] && board[y + 1][x] == board[y][x - 1]) {
                    return new FromTo(y, x - 1, y, x);
                }
            }
        }

        return null;
    }
}



