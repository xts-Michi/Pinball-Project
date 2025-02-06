import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel {
    private final Color BALL_COLOR = new Color(253, 255, 222);
    private final Color WALL_COLOR = new Color(227, 146, 70);
    private static final float SCALE = 100.0f;

    private Ball ball;
    private Wall[] walls;
    private Flipper[] flippers;
    private ScoringCircle[] scoringCircles;
    private Windmill[] windmills;
    private Tube[] tubes;
    private int points;
    private int lives;
    private Image backgroundImage;

    public GamePanel(World world, Ball ball, Wall[] walls, Flipper[] flippers, ScoringCircle[] scoringCircles, int lives, int points, Windmill[] windmills, Tube[] tubes) {
        this.ball = ball;
        this.walls = walls;
        this.flippers = flippers;
        this.windmills = windmills;
        this.scoringCircles = scoringCircles;
        this.lives = lives;
        this.points = points;
        this.tubes = tubes;
        this.backgroundImage = new ImageIcon("images/game_background_v.3.jpg").getImage(); // Load the background image
        setPreferredSize(new Dimension(700, 700)); // Panel size
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setWalls(Wall[] walls) {
        this.walls = walls;
    }

    public void setFlippers(Flipper[] flippers) {
        this.flippers = flippers;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background image
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Custom font settings
        Font font = new Font("Tahoma", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.setColor(new Color(251, 240, 194));

        // Calculate the width and height of the points-string to keep it centered
        FontMetrics fm = g2d.getFontMetrics();
        String pointsString = Integer.toString(points);
        int pointsWidth = fm.stringWidth(pointsString);
        int pointsHeight = fm.getAscent();

        // Position for points
        int pointsX = 610 - pointsWidth / 2;
        int pointsY = 53 + pointsHeight / 2;

//        // Draw lives
//        g2d.drawString("LIVES: " + lives, 40, pointsY);

        // Draw points
        g2d.drawString(pointsString, pointsX, pointsY);

        // Draw hearts (centered)
        int HEART_SPACING = 10;
        int heartSize = pointsHeight;
        int totalHeartWidth = heartSize * lives + HEART_SPACING * (lives - 1);
        int heartsX = 80 - totalHeartWidth / 2;
        int heartsY = 58 - pointsHeight / 2;

        for (int i = 0; i < lives; i++) {
            g2d.drawImage(new ImageIcon("images/heart.png").getImage(), heartsX + i * (heartSize + HEART_SPACING), heartsY, heartSize, heartSize, null);
        }

        // Draw edges
        g2d.setColor(WALL_COLOR);
        for (Wall wall : walls) {
            g2d.drawLine(
                    (int) (wall.start.x * SCALE),
                    (int) (wall.start.y * SCALE),
                    (int) (wall.end.x * SCALE),
                    (int) (wall.end.y * SCALE)
            );
        }

        // Draw windmills
        for (Windmill windmill : windmills) {
            Vec2 windmillPos = windmill.getJoint().getBodyA().getPosition();
            float angle = windmill.getJoint().getBodyB().getAngle();
            float armWidth = windmill.getArmWidth();
            float armLength = windmill.getArmLength();

            // Save current transform
            AffineTransform old = g2d.getTransform();

            // Set origin to (rotation) center of the flipper
            g2d.translate(windmillPos.x * SCALE, windmillPos.y * SCALE);
            g2d.rotate(angle);
            g2d.drawImage(
                    new ImageIcon("images/metal_bar.png").getImage(),
                    (int) (-armLength / 2 * SCALE),
                    (int) (-armWidth / 2 * SCALE),
                    (int) (armLength * SCALE),
                    (int) (armWidth * SCALE),
                    this
                    );

            g2d.rotate(Math.toRadians(90));

            g2d.drawImage(
                    new ImageIcon("images/metal_bar.png").getImage(),
                    (int) (-armLength / 2 * SCALE),
                    (int) (-armWidth / 2 * SCALE),
                    (int) (armLength * SCALE),
                    (int) (armWidth * SCALE),
                    this
            );

            g2d.setTransform(old);
        }

        // Draw scoring circles
        for (ScoringCircle sc : scoringCircles) {
            g2d.drawImage(sc.circleGraphic,
                    (int) ((sc.body.getPosition().x - sc.radius) * SCALE),
                    (int) ((sc.body.getPosition().y - sc.radius) * SCALE),
                    (int) ((sc.radius * 2) * SCALE),
                    (int) ((sc.radius * 2) * SCALE),
                    this
            );
        }

        // Draw tubes
        for (Tube tube : tubes) {
            g2d.setColor(Color.BLACK);
            Vec2[] outerVertices = tube.getOuterVertices();
            Vec2[] innerVertices = tube.getInnerVertices();
            int[] xPoints = new int[outerVertices.length];
            int[] yPoints = new int[outerVertices.length];

            for (int i = 0; i < outerVertices.length; i++) {
                xPoints[i] = (int) (SCALE * outerVertices[i].x);
                yPoints[i] = (int) (SCALE * outerVertices[i].y);
            }

            g2d.drawPolyline(xPoints, yPoints, outerVertices.length);

            for (int i = 0; i < innerVertices.length; i++) {
                xPoints[i] = (int) (SCALE * innerVertices[i].x);
                yPoints[i] = (int) (SCALE * innerVertices[i].y);
            }

            g2d.drawPolyline(xPoints, yPoints, innerVertices.length);
        }

        // Draw ball
        g2d.setColor(BALL_COLOR);
        Vec2 pos = ball.getPosition();
        float ballRadius = ball.getRadius();
        g2d.drawImage(ball.ballGraphic,
                (int) ((pos.x - ballRadius) * SCALE),
                (int) ((pos.y - ballRadius) * SCALE),
                (int) (ballRadius * 2 * SCALE),
                (int) (ballRadius * 2 * SCALE),
                this
        );

        // Draw flippers
        for (Flipper flipper : flippers) {
            Vec2 flipperPos = flipper.getPos();
            float circleRadius = flipper.getCircleRadius();
            float angle = flipper.getRectangleBat().getAngle();

            // Save current transform
            AffineTransform old = g2d.getTransform();

            // Set origin to (rotation) center of the flipper
            g2d.translate(flipperPos.x * SCALE, flipperPos.y * SCALE);
            g2d.rotate(angle);
            g2d.fillRect(
                    0,
                    (int) (-circleRadius * SCALE),
                    (int) (flipper.getBatLength() * SCALE),
                    (int) (flipper.getCircleRadius() * 2 * SCALE)
            );

            g2d.drawImage(new ImageIcon("images/bat.png").getImage(),
                    0,
                    (int) (-circleRadius * SCALE),
                    (int) (flipper.getBatLength() * SCALE),
                    (int) (flipper.getCircleRadius() * 2 * SCALE),
                    this
            );

            // Restore original transform
            g2d.setTransform(old);

            g2d.drawImage(
                    new ImageIcon("images/FlipperJoint.png").getImage(),
                    (int) ((flipperPos.x - circleRadius) * SCALE),
                    (int) ((flipperPos.y - circleRadius) * SCALE),
                    (int) (circleRadius * 2 * SCALE),
                    (int) (circleRadius * 2 * SCALE),
                    this
            );
        }
    }
}
