import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;

import javax.swing.*;
import java.awt.*;

public class FlipperPanel extends JPanel {

    private final Color BACKGROUND_COLOR = new Color(246, 220,172);
    private final Color BALL_COLOR = new Color(2, 131,145);
    private final Color WALL_COLOR = new Color(1, 32,78);

    private World world;
    private Ball ball;
    private Wall[] walls;
    private Flipper[] flippers;
    private int lives;
    private static final float SCALE = 200.0f; // scale to screen pixels

    public FlipperPanel(World world, Ball ball, Wall[] walls, Flipper[] flippers, int lives) {
        this.world = world;
        this.ball = ball;
        this.walls = walls;
        this.flippers = flippers;
        this.lives = lives;
        setPreferredSize(new Dimension(500, 700));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BACKGROUND_COLOR); // set background color
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the life-circle
        int radius = 10; // Radius of the life-circle
        int margin = 20; // Distance between the circles
        g.setColor(Color.RED); // Color of the life-circles
        for (int i=0; i < lives; i++) {
            g.fillOval(margin + i * (radius * 2 + margin),20, (int) (radius * 2), (int) (radius * 2));
        }

        // draw edges
        g.setColor(WALL_COLOR);
        for (Wall wall : walls){
            g.drawLine(
                    (int) (wall.start.x * SCALE),
                    (int) (wall.start.y * SCALE),
                    (int) (wall.end.x * SCALE),
                    (int) (wall.end.y * SCALE));
        }

        // draw ball
        g.setColor(BALL_COLOR);
        Vec2 pos = ball.getPosition();
        float ballRadiusradius = ball.getRadius();
        g.fillOval(
                (int) ((pos.x - ballRadiusradius) * SCALE),
                (int) ((pos.y - ballRadiusradius) * SCALE),
                (int) (ballRadiusradius * 2 * SCALE),
                (int) (ballRadiusradius * 2 * SCALE)
        );

        // draw flippers
        for (Flipper flipper : flippers) {
            Vec2 flipperPos = flipper.getPos();
            RevoluteJoint joint = flipper.getJoint();
            float circleRadius = flipper.getCircleRadius();
            float batLength = flipper.getBatLength();
            float angle = flipper.getRectangleBat().getAngle();
            Vec2 batPos = joint.getBodyB().getPosition();

            g.drawOval(
                    (int) ((flipperPos.x - circleRadius) * SCALE),
                    (int) ((flipperPos.y - circleRadius) * SCALE),
                    (int) (circleRadius * 2 * SCALE),
                    (int) (circleRadius * 2 * SCALE)
            );

            Graphics2D g2d = (Graphics2D) g;
            // set origin to (rotation) center of the flipper
            g2d.translate(flipperPos.x * SCALE, flipperPos.y * SCALE);
            g2d.rotate(angle);
            g2d.drawRect(
                    0,
                    (int) (-circleRadius * SCALE),
                    (int) (flipper.getBatLength() * SCALE),
                    (int) (flipper.getCircleRadius() * 2 * SCALE)
            );
            System.out.println(Math.toDegrees(angle));

            g2d.rotate(-angle);
            g2d.translate(-flipperPos.x * SCALE, -flipperPos.y * SCALE);
        }
    }
    public void decreaseLife() {
        if (lives > 0 ) {
            lives--;
            repaint();//Draw the panel again to show the changes
        }
    }
}
