package my.example.match3bot.processor.impl;

import my.example.match3bot.algorithm.Match3Alg;
import my.example.match3bot.utils.FromTo;
import my.example.match3bot.utils.Match3Utils;
import my.example.match3bot.processor.Match3Processor;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Match3 processor for StarCraft 2 Arcade game StarJeweled.
 * Configured for Display Intel(R) HD Graphics 630 (Mode: Windowed (Not Fullscreen); default placement top left corner).
 * Note: Recognition of elements by color, as the color difference is clearly pronounced.
 */
public class SC2Match3Processor implements Match3Processor {

    private static final int AFTER_MOVE_DELAY = 10;
    private static final int AFTER_CLICK_DELAY = 10;

    private static final int[][] colorValues = new int[][]{
            {115, 45, 22},  // Red
            {76, 132, 42},  // Green
            {67, 139, 171}, // Blue
            {108, 108, 50}, // Yellow
            {77, 43, 83},   // Purple
            {72, 69, 67}    // Gray
    };
    private static final int X_COORD = 646;
    private static final int Y_COORD = 104;
    private static final int SIZE = 414;
    private static final int N_SQUARES = 8;
    private static final int SQUARE_SIZE = SIZE / N_SQUARES;

    private final Robot robot;
    private final Match3Alg match3Alg;

    public SC2Match3Processor(Robot robot, Match3Alg match3Alg) {
        this.robot = robot;
        this.match3Alg = match3Alg;
    }

    @Override
    public void startProcess() {
        BufferedImage boardScreen = createBoardScreen();
//        Utils.saveBoardScreen(screenCapture);

        BufferedImage[][] imgMatrix = createIngMatrix(boardScreen);
//        Utils.saveParsedBoardScreen(imgMatrix);

        int[][] board = calculateMatrix(imgMatrix);
//        Utils.printBoard(board);

        FromTo fromTo = match3Alg.find(board);
//        System.out.println(fromTo);

        if (Objects.nonNull(fromTo)) {
            performOperation(fromTo);
        } else {
            publicNoMovesFoundErrMsg();
        }
    }

    private BufferedImage createBoardScreen() {
        return robot.createScreenCapture(new Rectangle(X_COORD, Y_COORD, SIZE, SIZE));
    }

    private BufferedImage[][] createIngMatrix(BufferedImage boardScreen) {
        BufferedImage[][] imgMatrix = new BufferedImage[N_SQUARES][N_SQUARES];
        double imageWidth = boardScreen.getWidth();
        double imageHeight = boardScreen.getHeight();
        int squareWidth = (int) Math.round(imageWidth / N_SQUARES);
        int squareHeight = (int) Math.round(imageHeight / N_SQUARES);
        for (int i = 0; i < N_SQUARES; i++) {
            for (int j = 0; j < N_SQUARES; j++) {
                int x = j * squareWidth;
                int y = i * squareHeight;
                int width = Math.min(squareWidth, boardScreen.getWidth() - x);
                int height = Math.min(squareHeight, boardScreen.getHeight() - y);
                BufferedImage chessSquare = boardScreen.getSubimage(x, y, width, height);
                imgMatrix[i][j] = chessSquare;
            }
        }
        return imgMatrix;
    }

    private int[][] calculateMatrix(BufferedImage[][] imgMatrix) {
        int[][] board = new int[N_SQUARES][N_SQUARES];
        for (int i = 0; i < N_SQUARES; i++) {
            for (int j = 0; j < N_SQUARES; j++) {
                BufferedImage image = imgMatrix[i][j];
                long redSum = 0;
                long greenSum = 0;
                long blueSum = 0;
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        Color pixelColor = new Color(image.getRGB(x, y));
                        redSum += pixelColor.getRed();
                        greenSum += pixelColor.getGreen();
                        blueSum += pixelColor.getBlue();
                    }
                }
                int numPixels = image.getWidth() * image.getHeight();
                int avgRed = (int) (redSum / numPixels);
                int avgGreen = (int) (greenSum / numPixels);
                int avgBlue = (int) (blueSum / numPixels);

                board[i][j] = getColor(avgRed, avgGreen, avgBlue);
            }
        }
        return board;
    }

    private int getColor(int avgRed, int avgGreen, int avgBlue) {
        int closestColor = 0;
        double smallestDistance = Double.MAX_VALUE;
        for (int i = 0; i < colorValues.length; i++) {
            double distance = Math.sqrt(Math.pow(avgRed - colorValues[i][0], 2) +
                    Math.pow(avgGreen - colorValues[i][1], 2) +
                    Math.pow(avgBlue - colorValues[i][2], 2));
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestColor = i + 1;
            }
        }
        return closestColor;
    }

    private void performOperation(FromTo fromTo) {
        Point originalPos = MouseInfo.getPointerInfo().getLocation();

        moveAndClick(fromTo.fromX(), fromTo.fromY());
        moveAndClick(fromTo.toX(), fromTo.toY());

        robot.mouseMove((int) originalPos.getX(), (int) originalPos.getY());
    }

    private void moveAndClick(int x, int y) {
        robot.mouseMove(
                X_COORD + SQUARE_SIZE * x + SQUARE_SIZE / 2,
                Y_COORD + SQUARE_SIZE * y + SQUARE_SIZE / 2
        );
        Match3Utils.waitMe(AFTER_MOVE_DELAY);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Match3Utils.waitMe(AFTER_CLICK_DELAY);
    }
}
