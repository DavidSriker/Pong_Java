package Pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameRenderer extends JPanel {

    protected void paintComponent(Graphics elem) {
        super.paintComponent(elem);
        elem.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 18));

        elem.setColor(new Color(2039572));
        elem.fillRect(0, 0, PongLogic.WIDTH, PongLogic.HEIGHT);

        elem.setColor(new Color(10040166));
        elem.fillRect(PongLogic.ball.x, PongLogic.ball.y, PongLogic.SCALE, PongLogic.SCALE);

        elem.setColor(new Color(16777215));
        elem.drawLine(PongLogic.WIDTH / 2, 0, PongLogic.WIDTH / 2, PongLogic.HEIGHT-1);
        elem.drawArc(PongLogic.WIDTH / 2 - 60, PongLogic.HEIGHT / 2 - 60, 120, 120, 0, 360);


        elem.setColor(new Color(26367));
        elem.drawString("Goals: " + PongLogic.score_left, PongLogic.WIDTH / 4, 30);
        elem.setColor(new Color(16727040));
        elem.drawString("Goals: "+ PongLogic.score_right, 3 * PongLogic.WIDTH / 4, 30);

        elem.setColor(new Color(46080));
        if (PongLogic.paused) {
            elem.drawString("Press Space to Start!", PongLogic.WIDTH / 2 - 100, PongLogic.HEIGHT / 2);
        }
    }
}
