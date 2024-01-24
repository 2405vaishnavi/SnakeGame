package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Board extends JPanel implements ActionListener {

    private Image target;
    private Image source;

    private final int ALL_DOTS = 2500;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POSITION = 45;

    private int target_x;
    private int target_y;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;

    private int dots;
    private Timer timer;
    private int score = 0;

    Board() {
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));
        setFocusable(true);

        loadImages();
        initGame();
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/img2.png"));
        Image originalImage = i1.getImage();

        int desiredWidth = 10;
        int desiredHeight = 10;

        Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

        target = new ImageIcon(resizedImage).getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/img1.png"));
        Image originalImage1 = i2.getImage();

        Image resizedImage1 = originalImage1.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

        source = new ImageIcon(resizedImage1).getImage();
    }

    public void initGame() {
        dots = 2;

        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }

        locateTarget();

        timer = new Timer(140, this);
        timer.start();
    }

    public void locateTarget() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        target_x = r * DOT_SIZE;

        r = (int) (Math.random() * RANDOM_POSITION);
        target_y = r * DOT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        displayScore(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(target, target_x, target_y, this);

            for (int i = 0; i < dots; i++) {
                g.drawImage(source, x[i], y[i], this);
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void displayScore(Graphics g) {
        String scoreText = "Score: " + score;
        Font font = new Font("Arial", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(scoreText, 10, 20);
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("Arial", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (500 - metrics.stringWidth(msg)) / 2, 500 / 2);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }

    public void checkTarget() {
        if ((x[0] == target_x) && (y[0] == target_y)) {
            dots++;
            score++;
            locateTarget();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (y[0] >= 500) {
            inGame = false;
        }
        if (x[0] >= 500) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkTarget();
            checkCollision();
            move();
        }

        repaint();
    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }

            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}