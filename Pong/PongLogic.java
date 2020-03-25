package Pong;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JFrame;

public class PongLogic{

    public static PongLogic pong;
    public static final int WIDTH = 1000, HEIGHT = 600, UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
    public static Point ball;
    public static int score_left = 0, score_right = 0;
    public static boolean gameOver, paused=true;
    public int vertical, angle;
    public GameRenderer renderer;
    public JFrame jframe;
    public int snakeLength;
    public int direction = DOWN;
    public Point head;
    public Random randGen = new Random();

    public PongLogic(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Pong Game");
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(renderer = new GameRenderer());
        renderer.repaint();
        jframe.setVisible(true);
        startGame();
    }

    private void startGame(){
        ball = new Point(WIDTH / 2 - SCALE / 2, HEIGHT / 2 - SCALE / 2);
        score_left = 0;
        score_right = 0;
        paused = true;
        gameOver = false;
        angle = randGen.nextInt(4);
        direction = RIGHT;
        vertical = UP;
    }

    public static void main(String[] args) {
        PongLogic pong = new PongLogic();
        pong.startGame();
    }
}
