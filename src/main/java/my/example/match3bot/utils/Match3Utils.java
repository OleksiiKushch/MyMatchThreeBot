package my.example.match3bot.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Match3Utils {

    private static final String GENERAL_FORMAT = "png";
    private static final String BOARD_SCREEN_PATHNAME = "output/board-screen." + GENERAL_FORMAT;
    private static final String IMG_MATRIX_FOLDER = "output/img-matrix";

    public static void saveBoardScreen(BufferedImage screenCapture) {
        File outputfile = new File(BOARD_SCREEN_PATHNAME);
        try {
            ImageIO.write(screenCapture, GENERAL_FORMAT, outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveParsedBoardScreen(BufferedImage[][] imgMatrix) {
        File outputDirectory = new File(IMG_MATRIX_FOLDER);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                BufferedImage image = imgMatrix[y][x];
                File outputFile = new File(outputDirectory, formImgMatrixName(y, x));
                try {
                    ImageIO.write(image, GENERAL_FORMAT, outputFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (x == 1 && y == 1) {
                    break;
                }
            }
        }
    }

    private static String formImgMatrixName(int y, int x) {
        return "square_y" + y + "_x" + x + "." + GENERAL_FORMAT;
    }

    public static void printBoard(int[][] board) {
        for (int[] b : board) {
            for (int i : b) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void waitMe(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
