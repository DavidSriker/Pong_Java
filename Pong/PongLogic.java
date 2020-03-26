package Pong;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class PongLogic implements ActionListener, KeyListener{

    public static final int WIDTH = 1000, HEIGHT = 600, UP = 0, DOWN = 1, LEFT = 0, RIGHT = 1, SCALE = 10, PONG_WIDTH = 50;
    public static Point ball;
    public static int score_left = 0, score_right = 0;
    public static ArrayList<Point> pongRight = new ArrayList<Point>();
    public static ArrayList<Point> pongLeft = new ArrayList<Point>();
    public static boolean gameOver, paused=true;
    public static int vertical, angle, direction = DOWN;
    public GameRenderer renderer;
    public JFrame jframe;
    public static Random randGen = new Random();
    public Timer timer = new Timer(30, this);

    public PongLogic(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Pong Game");
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(renderer = new GameRenderer());
        renderer.repaint();
        jframe.setVisible(true);
        jframe.addKeyListener(this);
        startGame();
        timer.start();
    }

    private void startGame(){
        ball = new Point(WIDTH / 2 - SCALE / 2, HEIGHT / 2 - SCALE / 2);
        fillPong(pongRight, RIGHT);
        fillPong(pongLeft, LEFT);
        score_left = 0;
        score_right = 0;
        paused = true;
        gameOver = false;
        angle = randGen.nextInt(4);
        direction = RIGHT;
        vertical = UP;
    }

    private static void fillPong(ArrayList<Point> pong, int side) {
        pong.clear();
        for (int i=0 ; i < PONG_WIDTH ; i++){
            if (side == RIGHT) {
                pong.add(new Point(WIDTH - 30, HEIGHT / 2 - PONG_WIDTH / 2 + i));
            } else if (side == LEFT) {
                pong.add(new Point(5, HEIGHT / 2 - PONG_WIDTH / 2 + i));
            }
        }
    }

    private static void newRound(){
        ball = new Point(WIDTH / 2 - SCALE / 2, HEIGHT / 2 - SCALE / 2);
        fillPong(pongRight, RIGHT);
        fillPong(pongLeft, LEFT);
        paused = true;
        direction = 1 - direction;
        vertical = 1 - vertical;
        angle = randGen.nextInt(4);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) { // left up
            movePong(pongLeft, UP);
        } else if (key == KeyEvent.VK_S) { // left down
            movePong(pongLeft, DOWN);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) { // right up
            movePong(pongRight, UP);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) { // right down
            movePong(pongRight, DOWN);
        } else if (key == KeyEvent.VK_SPACE) {
            paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private static void movePong(ArrayList<Point> pong, int direction) {
        for (int i=0 ; i<15 ; i++){
            if (direction == DOWN){
                if (pong.get(pong.size()-1).y+1<HEIGHT-50){
                    pong.add(new Point(pong.get(0).x, pong.get(PONG_WIDTH-1).y+1));
                    pong.remove(0);}
            }
            if (direction == UP){
                if (pong.get(0).y-1>0){
                    pong.add(0, new Point(pong.get(0).x, pong.get(0).y-1));
                    pong.remove(pong.size()-1);}
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderer.repaint();
        if (!gameOver && !paused) {
            if (direction == RIGHT) {
                ball.x += 5;
                if (vertical == UP)
                    ball.y -= 5 * angle;
                if (vertical == DOWN)
                    ball.y += 5 * angle;
            } else if (direction == LEFT) {
                ball.x -= 5;
                if (vertical == UP)
                    ball.y -= 5 * angle;
                if (vertical == DOWN)
                    ball.y += 5 * angle;
            }
            if (ball.x == WIDTH - 30) {
                score_left++;
                newRound();
            } else if (ball.x == 10) {
                score_right++;
                newRound();
            }

            if (ball.y - SCALE <= 0 || ball.y + SCALE >= HEIGHT - 40) {
                vertical = 1 - vertical;
            }

            Point ballRight = new Point(ball.x + SCALE, ball.y);
            Point ballLeft = new Point(ball.x - SCALE, ball.y);
            if (doesContains(pongRight, ballRight) || doesContains(pongLeft, ballLeft)) {
                if (direction == RIGHT) {
                    changeSpeed(pongRight, ballRight);
                } else if (direction == LEFT) {
                    changeSpeed(pongLeft, ballLeft);
                }
                direction = 1 - direction;
            }
        }
    }

    private boolean doesContains(ArrayList<Point> pong, Point ball) {
        return pong.contains(ball) || pong.contains(new Point(ball.x, ball.y - SCALE))
                || pong.contains(new Point(ball.x, ball.y + SCALE));
    }

    public static void changeSpeed(ArrayList<Point> pong, Point ball){
        if (pong.indexOf(ball) <= 11){
            vertical = UP;
            angle = 3;
        } else if (pong.indexOf(ball) <= 22){
            vertical = UP;
            angle = 2;
        } else if (pong.indexOf(ball) <= 33){
            vertical = UP;
            angle = 1;
        } else if (pong.indexOf(ball) >= 68){
            vertical = DOWN;
            angle = 3;
        } else if (pong.indexOf(ball) >= 57){
            vertical = DOWN;
            angle = 2;
        } else if (pong.indexOf(ball) >= 46){
            vertical = DOWN;
            angle = 1;
        }
    }


    public static void main(String[] args) {
        PongLogic pong = new PongLogic();
        pong.startGame();
    }
}
