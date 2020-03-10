/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */

import tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameBattleShip extends JFrame {

    protected final String TITLE = "BattleShip";
    protected final int FIELD_SIZE = 10;
    protected final int AI_PANEL_SIZE = 400;
    protected final int AI_CELL_SIZE = AI_PANEL_SIZE / FIELD_SIZE;
    protected final int HUMAN_PANEL_SIZE = AI_PANEL_SIZE / 2;
    protected final int HUMAN_CELL_SIZE = HUMAN_PANEL_SIZE / FIELD_SIZE;
    protected final String BTN_NEW_GAME = "New Game";
    protected final String BTN_EXIT = "Exit";
    protected final String MSG_AI_WIN = "You loss!!!";
    protected final String MSG_HUMAN_WIN = "You win!!!";
    protected final int MOUSE_LEFT_CLICK = 1;
    protected final int MOUSE_RIGHT_CLICK = 3;

    protected JTextArea board;
    protected Canvas leftPanel;
    protected Canvas humanPanel;

    protected Ships aiShips;
    protected Ships humanShips;

    protected Shots aiShots;
    protected Shots humanShots;

    protected Random random;

    protected boolean gameOver;

    public static void main(String[] args) {
        new GameBattleShip();
    }

    public GameBattleShip() {
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        leftPanel = new Canvas();
        leftPanel.setPreferredSize(new Dimension(AI_PANEL_SIZE, AI_PANEL_SIZE));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        leftPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX() / AI_CELL_SIZE;
                int y = e.getY() / AI_CELL_SIZE;
                if (e.getButton() == MOUSE_LEFT_CLICK && !gameOver) {
                    if (!humanShots.hitSamePlace(x, y)) {
                        humanShots.add(x, y, true);
                    }
                    if (aiShips.checkSurvivors()) {
                        board.append("\n" + MSG_HUMAN_WIN);
                        gameOver = true;
                    } else {
                        shootsAI();
                    }
                    leftPanel.repaint();
                    humanPanel.repaint();
                    board.setCaretPosition(board.getText().length());
                }
                if (e.getButton() == MOUSE_RIGHT_CLICK) {
                    Shot label = humanShots.getLabel(x, y);
                    if (label != null) {
                        humanShots.removeLabel(label);
                    } else {
                        humanShots.add(x, y, false);
                    }
                    leftPanel.repaint();
                }
            }
        });

        humanPanel = new Canvas();
        humanPanel.setPreferredSize(new Dimension(HUMAN_PANEL_SIZE, HUMAN_PANEL_SIZE));
        humanPanel.setBackground(Color.WHITE);
        humanPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

        JButton newGame = new JButton(BTN_NEW_GAME);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                leftPanel.repaint();
                humanPanel.repaint();
            }
        });

        JButton exitGame = new JButton(BTN_EXIT);
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        board = new JTextArea();
        board.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(board);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.add(newGame);
        buttonPanel.add(exitGame);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(humanPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(leftPanel);
        add(rightPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        init();
    }

    public void init() {
        aiShips = new Ships(FIELD_SIZE, AI_CELL_SIZE, true);
        humanShips = new Ships(FIELD_SIZE, HUMAN_CELL_SIZE, false);
        aiShots = new Shots(HUMAN_CELL_SIZE);
        humanShots = new Shots(AI_CELL_SIZE);
        board.setText(BTN_NEW_GAME);
        gameOver = false;
        random = new Random();
    }

    private void shootsAI() {
        int x;
        int y;
        do {
            x = random.nextInt(FIELD_SIZE);
            y = random.nextInt(FIELD_SIZE);
        } while (aiShots.hitSamePlace(x, y));
        aiShots.add(x, y, true);
        if (!humanShips.checkHit(x, y)) {
            board.append("\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
            return;
        } else {
            board.append("\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
            board.setCaretPosition(board.getText().length());
            if (!humanShips.checkSurvivors()) {
                board.append("\n" + MSG_AI_WIN);
                gameOver = true;
            } else {
                shootsAI();
            }
        }
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int cellSize = (int) getSize().getWidth() / FIELD_SIZE;
            g.setColor(Color.lightGray);
            for (int i = 1; i < FIELD_SIZE; i++) {
                g.drawLine(0, i * cellSize, FIELD_SIZE * cellSize, i * cellSize);
                g.drawLine(i * cellSize, 0, i * cellSize, FIELD_SIZE * cellSize);
            }
            if (cellSize == AI_CELL_SIZE) {
                humanShots.paint(g);
                aiShips.paint(g);
            } else {
                aiShots.paint(g);
                humanShips.paint(g);
            }
        }
    }
}


