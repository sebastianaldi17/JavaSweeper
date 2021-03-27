import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel {
    final int BOARD_LENGTH = 10;
    final int MINES = 10;
    private int[][] boardValues;
    private JButton[][] boardButtons;
    private boolean firstClick = true;

    public Board() {
        initializeBoard();
    }

    // Generates board based on first click location
    // (x, y) contains the coordinate of the first click
    public void generateBoard(int x, int y) {
        Random rand = new Random();
        int minesLeft = MINES;
        while (minesLeft > 0) {
            int temp_x, temp_y;
            temp_x = rand.nextInt(BOARD_LENGTH);
            temp_y = rand.nextInt(BOARD_LENGTH);

            // Don't put mines on existing mine tile OR the first click location
            if (boardValues[temp_y][temp_x] != -1 && temp_x != x && temp_y != y) {
                // System.out.println("Mine at " + temp_x + " " + temp_y);
                boardValues[temp_y][temp_x] = -1;
                minesLeft--;
            }
        }

        // Fills the rest of the boards with numbers (0-8, based on how many neighbouring mines)
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (boardValues[i][j] != -1)
                    boardValues[i][j] = minesAt(j, i);
            }
        }
    }

    public void initializeBoard() {
        setLayout(new GridLayout(BOARD_LENGTH, BOARD_LENGTH));
        setSize(500, 500);

        boardValues = new int[BOARD_LENGTH][BOARD_LENGTH];
        boardButtons = new JButton[BOARD_LENGTH][BOARD_LENGTH];
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                boardValues[i][j] = 0;
                boardButtons[i][j] = new JButton();

                final int x = j;
                final int y = i;
                boardButtons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (firstClick) {
                            generateBoard(x, y);
                            firstClick = false;
                        }
                        reveal(x, y, true);
                    }

                });

                add(boardButtons[i][j]);
            }
        }

        setVisible(true);
    }

    public boolean isInGrid(int x, int y) {
        if (x < 0 || y < 0)
            return false;
        if (x >= BOARD_LENGTH || y >= BOARD_LENGTH)
            return false;
        return true;
    }

    public int minesAt(int x, int y) {
        int mineCount = 0;
        if (isInGrid(x - 1, y - 1) && boardValues[y - 1][x - 1] == -1)
            mineCount++;
        if (isInGrid(x, y - 1) && boardValues[y - 1][x] == -1)
            mineCount++;
        if (isInGrid(x + 1, y - 1) && boardValues[y - 1][x + 1] == -1)
            mineCount++;
        if (isInGrid(x - 1, y) && boardValues[y][x - 1] == -1)
            mineCount++;
        if (isInGrid(x + 1, y) && boardValues[y][x + 1] == -1)
            mineCount++;
        if (isInGrid(x - 1, y + 1) && boardValues[y + 1][x - 1] == -1)
            mineCount++;
        if (isInGrid(x, y + 1) && boardValues[y + 1][x] == -1)
            mineCount++;
        if (isInGrid(x + 1, y + 1) && boardValues[y + 1][x + 1] == -1)
            mineCount++;
        return mineCount;
    }

    public void reveal(int x, int y, boolean initialClick) {
        if (!isInGrid(x, y))
            return;
        if (!boardButtons[y][x].isEnabled())
            return;
        boardButtons[y][x].setText(Integer.toString(boardValues[y][x]));
        boardButtons[y][x].setEnabled(false);
        if (initialClick) {
            if (boardValues[y][x] == -1) {
                boardButtons[y][x].setBackground(Color.RED);
                // System.out.println("BOOM");
                return;
            }
        }
        // If it's a mine (and not the initial click reveal), don't open via floodfill
        if (boardValues[y][x] == -1) {
            return;
        }
        boardButtons[y][x].setText(Integer.toString(boardValues[y][x]));
        boardButtons[y][x].setEnabled(false);

        // If it's a number, reveal then don't contnue floodfill
        if (boardValues[y][x] != 0)
            return;

        // Implement floodfill
        reveal(x - 1, y - 1, false);
        reveal(x, y - 1, false);
        reveal(x + 1, y - 1, false);
        reveal(x - 1, y, false);
        reveal(x + 1, y, false);
        reveal(x - 1, y + 1, false);
        reveal(x, y + 1, false);
        reveal(x + 1, y + 1, false);
    }
}
