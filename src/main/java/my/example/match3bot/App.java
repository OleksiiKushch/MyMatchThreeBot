package my.example.match3bot;

import my.example.match3bot.algorithm.impl.BruteForce1Match3Alg;
import my.example.match3bot.processor.Match3Processor;
import my.example.match3bot.processor.impl.SC2Match3Processor;
import my.example.match3bot.utils.Match3Utils;

import javax.swing.*;
import java.awt.*;

public class App {

    private static final String WINDOW_NAME = "Match Three Solver";
    private static final int WINDOW_WIDTH = 360;
    private static final int WINDOW_HEIGHT = 100;
    private static final String FIND_BUTTON_NAME = "Find";
    private static final String START_AUTO_SEARCH_BUTTON_NAME = "Start Auto Search";
    private static final String STOP_AUTO_SEARCH_BUTTON_NAME = "Stop Auto Search";

    private static final int MAIN_DELAY = 500;

    private static boolean isAutoSearching = false;

    public static void main(String[] args) throws AWTException {
        JFrame frame = new JFrame(WINDOW_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new FlowLayout());

        Match3Processor match3Processor = new SC2Match3Processor(new Robot(), new BruteForce1Match3Alg());

        JButton findButton = prepareFindButton(match3Processor);
        frame.getContentPane().add(findButton);

        JButton autoSearchButton = prepareAutoSearchButton(match3Processor);
        frame.getContentPane().add(autoSearchButton);

        frame.setVisible(true);
    }

    private static JButton prepareFindButton(Match3Processor match3Processor) {
        JButton findButton = new JButton(FIND_BUTTON_NAME);
        findButton.addActionListener(e -> match3Processor.startProcess());
        return findButton;
    }

    private static JButton prepareAutoSearchButton(Match3Processor match3Processor) {
        JButton autoSearchButton = new JButton(START_AUTO_SEARCH_BUTTON_NAME);

        autoSearchButton.addActionListener(e -> {
            if (!isAutoSearching) {
                isAutoSearching = true;
                autoSearchButton.setText(STOP_AUTO_SEARCH_BUTTON_NAME);
                new Thread(() -> {
                    while(isAutoSearching) {
                        match3Processor.startProcess();
                        Match3Utils.waitMe(MAIN_DELAY);
                    }
                }).start();
            } else {
                isAutoSearching = false;
                autoSearchButton.setText(START_AUTO_SEARCH_BUTTON_NAME);
            }
        });
        return autoSearchButton;
    }
}
